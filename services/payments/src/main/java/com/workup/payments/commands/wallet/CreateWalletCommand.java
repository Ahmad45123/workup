package com.workup.payments.commands.wallet;

import com.workup.payments.commands.PaymentCommand;
import com.workup.payments.models.Wallet;
import com.workup.shared.commands.payments.wallet.requests.CreateWalletRequest;
import com.workup.shared.commands.payments.wallet.responses.CreateWalletResponse;
import com.workup.shared.enums.HttpStatusCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateWalletCommand extends PaymentCommand<CreateWalletRequest, CreateWalletResponse> {
  private static final Logger logger = LogManager.getLogger(CreateWalletCommand.class);

  @Override
  public CreateWalletResponse Run(CreateWalletRequest request) {
    if (getWalletRepository().existsById(request.getFreelancerId())) {
      return CreateWalletResponse.builder()
          .withStatusCode(HttpStatusCode.BAD_REQUEST)
          .withErrorMessage("Wallet already exists")
          .build();
    }
    Wallet wallet = Wallet.builder().withFreelancerId(request.getFreelancerId()).build();
    try {
      Wallet savedWallet = getWalletRepository().save(wallet);

      logger.info("[x] Wallet created : " + savedWallet);

      return CreateWalletResponse.builder().withStatusCode(HttpStatusCode.CREATED).build();
    } catch (Exception e) {
      logger.error("[x] Wallet creation failed : " + e.getMessage());

      return CreateWalletResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .withErrorMessage("An error occurred while creating payment request")
          .build();
    }
  }
}
