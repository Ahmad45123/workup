package com.workup.payments.commands.wallettransaction;

import com.workup.payments.commands.PaymentCommand;
import com.workup.payments.models.Wallet;
import com.workup.payments.models.WalletTransaction;
import com.workup.shared.commands.payments.wallettransaction.requests.CreateWalletTransactionRequest;
import com.workup.shared.commands.payments.wallettransaction.responses.CreateWalletTransactionResponse;
import com.workup.shared.enums.HttpStatusCode;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateWalletTransactionCommand
    extends PaymentCommand<CreateWalletTransactionRequest, CreateWalletTransactionResponse> {
  private static final Logger logger = LogManager.getLogger(CreateWalletTransactionCommand.class);

  @Override
  public CreateWalletTransactionResponse Run(CreateWalletTransactionRequest request) {

    Optional<Wallet> wallet = getWalletRepository().findById(request.getFreelancerId());
    if (wallet.isEmpty()) {
      return CreateWalletTransactionResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .withErrorMessage("Wallet not found")
          .build();
    }
    if (request.getAmount() <= 0) {
      return CreateWalletTransactionResponse.builder()
          .withStatusCode(HttpStatusCode.BAD_REQUEST)
          .withErrorMessage("Amount must be greater than 0")
          .build();
    }

    WalletTransaction walletTransaction =
        WalletTransaction.builder()
            .withWalletId(request.getFreelancerId())
            .withAmount(request.getAmount())
            .withPaymentTransactionId(request.getPaymentTransactionId())
            .withDescription(request.getDescription())
            .withTransactionType(request.getTransactionType())
            .build();
    try {
      WalletTransaction savedWalletTransaction =
          getWalletTransactionRepository().save(walletTransaction);

      logger.info("[x] Wallet transaction created : " + savedWalletTransaction);

      return CreateWalletTransactionResponse.builder()
          .withStatusCode(HttpStatusCode.CREATED)
          .withWalletTransactionId(savedWalletTransaction.getId())
          .build();
    } catch (Exception e) {
      logger.error("[x] Wallet transaction creation failed : " + e.getMessage());

      return CreateWalletTransactionResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .withErrorMessage("An error occurred while creating wallet transaction")
          .withWalletTransactionId(null)
          .build();
    }
  }
}
