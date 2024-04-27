package com.workup.payments.commands.wallettransaction;

import com.workup.payments.commands.PaymentCommand;
import com.workup.payments.models.Wallet;
import com.workup.payments.models.WalletTransaction;
import com.workup.shared.commands.payments.wallettransaction.requests.WithdrawFromWalletRequest;
import com.workup.shared.commands.payments.wallettransaction.responses.WithdrawFromWalletResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.payments.WalletTransactionType;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

public class WithdrawFromWalletCommand
    extends PaymentCommand<WithdrawFromWalletRequest, WithdrawFromWalletResponse> {

  @Override
  @Transactional
  public WithdrawFromWalletResponse Run(WithdrawFromWalletRequest request) {
    String freelancerId = request.getFreelancerId();
    double amount = request.getAmount();
    String paymentTransactionId = request.getPaymentTransactionId();
    String description = request.getDescription();

    Optional<Wallet> wallet = getWalletRepository().findById(freelancerId);
    if (wallet.isEmpty()) {
      return WithdrawFromWalletResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Wallet not found")
          .build();
    }
    if (amount <= 0) {
      return WithdrawFromWalletResponse.builder()
          .withStatusCode(HttpStatusCode.BAD_REQUEST)
          .withErrorMessage("Amount must be greater than 0")
          .build();
    }
    if (wallet.get().getBalance() < amount) {
      return WithdrawFromWalletResponse.builder()
          .withStatusCode(HttpStatusCode.BAD_REQUEST)
          .withErrorMessage("Insufficient balance")
          .build();
    }
    wallet.get().setBalance(wallet.get().getBalance() - amount);
    try {
      getWalletRepository().save(wallet.get());

      System.out.println("[x] Wallet balance updated : " + wallet.get().getBalance());

      WalletTransaction walletTransaction =
          WalletTransaction.builder()
              .withWalletId(freelancerId)
              .withAmount(amount)
              .withPaymentTransactionId(paymentTransactionId)
              .withDescription(description)
              .withTransactionType(WalletTransactionType.DEBIT)
              .build();
      getWalletTransactionRepository().save(walletTransaction);

      System.out.println("[x] Wallet transaction created : " + walletTransaction);

      return WithdrawFromWalletResponse.builder()
          .withStatusCode(HttpStatusCode.OK)
          .withBalance(wallet.get().getBalance())
          .build();
    } catch (Exception e) {
      return WithdrawFromWalletResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .withErrorMessage("An error occurred while withdrawing from wallet")
          .build();
    }
  }
}
