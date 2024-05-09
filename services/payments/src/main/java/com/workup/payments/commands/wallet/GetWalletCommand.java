package com.workup.payments.commands.wallet;

import com.workup.payments.commands.PaymentCommand;
import com.workup.payments.models.Wallet;
import com.workup.shared.commands.payments.wallet.requests.GetWalletRequest;
import com.workup.shared.commands.payments.wallet.responses.GetWalletResponse;
import com.workup.shared.enums.HttpStatusCode;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetWalletCommand extends PaymentCommand<GetWalletRequest, GetWalletResponse> {
  private static final Logger logger = LogManager.getLogger(GetWalletCommand.class);

  @Override
  public GetWalletResponse Run(GetWalletRequest request) {
    Optional<Wallet> wallet = getWalletRepository().findById(request.getFreelancerId());
    if (wallet.isPresent()) {
      logger.info("[x] Wallet fetched : " + wallet.get());

      return GetWalletResponse.builder()
          .withStatusCode(HttpStatusCode.OK)
          .withBalance(wallet.get().getBalance())
          .build();
    }
    return GetWalletResponse.builder()
        .withStatusCode(HttpStatusCode.NOT_FOUND)
        .withErrorMessage("Wallet not found")
        .build();
  }
}
