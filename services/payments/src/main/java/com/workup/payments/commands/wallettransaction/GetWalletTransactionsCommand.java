package com.workup.payments.commands.wallettransaction;

import com.workup.payments.commands.PaymentCommand;
import com.workup.payments.mapper.WalletTransactionMapper;
import com.workup.payments.models.WalletTransaction;
import com.workup.shared.commands.payments.dto.WalletTransactionDTO;
import com.workup.shared.commands.payments.wallettransaction.requests.GetWalletTransactionsRequest;
import com.workup.shared.commands.payments.wallettransaction.responses.GetWalletTransactionsResponse;
import com.workup.shared.enums.HttpStatusCode;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetWalletTransactionsCommand
    extends PaymentCommand<GetWalletTransactionsRequest, GetWalletTransactionsResponse> {
  private static final Logger logger = LogManager.getLogger(GetWalletTransactionsCommand.class);

  @Override
  public GetWalletTransactionsResponse Run(GetWalletTransactionsRequest request) {
    if (!getWalletRepository().existsById(request.getFreelancerId())) {
      return GetWalletTransactionsResponse.builder()
          .withStatusCode(HttpStatusCode.BAD_REQUEST)
          .withErrorMessage("Wallet does not exist")
          .build();
    }
    List<WalletTransaction> savedTransactions =
        getWalletTransactionRepository().findAllByWalletId(request.getFreelancerId());
    List<WalletTransactionDTO> walletTransactionDTOS =
        WalletTransactionMapper.mapToWalletTransactionDTOs(savedTransactions);

    logger.info("[x] Wallet transactions fetched : " + walletTransactionDTOS);

    return GetWalletTransactionsResponse.builder()
        .withStatusCode(HttpStatusCode.OK)
        .withTransactions(walletTransactionDTOS)
        .build();
  }
}
