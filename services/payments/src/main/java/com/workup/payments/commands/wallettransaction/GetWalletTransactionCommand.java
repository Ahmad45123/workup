package com.workup.payments.commands.wallettransaction;

import com.workup.payments.commands.PaymentCommand;
import com.workup.payments.mapper.WalletTransactionMapper;
import com.workup.payments.models.WalletTransaction;
import com.workup.shared.commands.payments.dto.WalletTransactionDTO;
import com.workup.shared.commands.payments.wallettransaction.requests.GetWalletTransactionRequest;
import com.workup.shared.commands.payments.wallettransaction.responses.GetWalletTransactionResponse;
import com.workup.shared.enums.HttpStatusCode;

public class GetWalletTransactionCommand
  extends PaymentCommand<GetWalletTransactionRequest, GetWalletTransactionResponse> {

  @Override
  public GetWalletTransactionResponse Run(GetWalletTransactionRequest request) {
    WalletTransaction savedTransaction = getWalletTransactionRepository()
      .findByWalletId(request.getWalletTransactionId());

    WalletTransactionDTO walletTransactionDTO = WalletTransactionMapper.mapToWalletTransactionDTO(
      savedTransaction
    );

    System.out.println("[x] Wallet transaction fetched : " + walletTransactionDTO);

    return GetWalletTransactionResponse
      .builder()
      .withStatusCode(HttpStatusCode.OK)
      .withTransaction(walletTransactionDTO)
      .build();
  }
}
