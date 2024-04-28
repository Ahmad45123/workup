package com.workup.payments.commands.wallet;

import com.workup.payments.commands.PaymentCommand;
import com.workup.payments.models.Wallet;
import com.workup.shared.commands.payments.wallet.requests.GetWalletRequest;
import com.workup.shared.commands.payments.wallet.responses.GetWalletResponse;
import com.workup.shared.enums.HttpStatusCode;
import java.util.Optional;

public class GetWalletCommand extends PaymentCommand<GetWalletRequest, GetWalletResponse> {

  @Override
  public GetWalletResponse Run(GetWalletRequest request) {
    Optional<Wallet> wallet = getWalletRepository().findById(request.getFreelancerId());
    if (wallet.isPresent()) {
      System.out.println("[x] Wallet fetched : " + wallet.get());

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
