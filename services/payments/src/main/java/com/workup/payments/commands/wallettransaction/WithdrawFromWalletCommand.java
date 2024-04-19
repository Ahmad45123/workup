package com.workup.payments.commands.wallettransaction;

import com.workup.payments.commands.PaymentCommand;
import com.workup.payments.models.Wallet;
import com.workup.payments.models.WalletTransaction;
import com.workup.shared.commands.payments.wallettransaction.requests.WithdrawFromWalletRequest;
import com.workup.shared.commands.payments.wallettransaction.responses.WithdrawFromWalletResponse;
import com.workup.shared.enums.payments.WalletTransactionType;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

public class WithdrawFromWalletCommand extends PaymentCommand<WithdrawFromWalletRequest, WithdrawFromWalletResponse> {

    @Override
    @Transactional
    public WithdrawFromWalletResponse Run(WithdrawFromWalletRequest request) {
        String freelancerId = request.getFreelancerId();
        double amount = request.getAmount();
        String description = request.getDescription();

        Optional<Wallet> wallet = getWalletRepository().findById(request.getFreelancerId());
        if (wallet.isEmpty() || amount <= 0 || wallet.get().getBalance() < amount) {
            return WithdrawFromWalletResponse.builder()
                    .withSuccess(false)
                    .build();
        }
        wallet.get().setBalance(wallet.get().getBalance() - amount);
        try {
            getWalletRepository().save(wallet.get());

            System.out.println("[x] Wallet balance updated : " + wallet.get().getBalance());

            WalletTransaction walletTransaction = WalletTransaction.builder()
                    .withWalletId(freelancerId)
                    .withAmount(amount)
                    .withDescription(description)
                    .withTransactionType(WalletTransactionType.DEBIT)
                    .build();
            getWalletTransactionRepository().save(walletTransaction);

            System.out.println("[x] Wallet transaction created : " + walletTransaction);

            return WithdrawFromWalletResponse.builder()
                    .withSuccess(true)
                    .withBalance(wallet.get().getBalance())
                    .build();
        } catch (Exception e) {
            return WithdrawFromWalletResponse.builder()
                    .withSuccess(false)
                    .build();
        }
    }
}
