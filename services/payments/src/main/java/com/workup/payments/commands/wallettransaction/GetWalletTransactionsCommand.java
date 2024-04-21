package com.workup.payments.commands.wallettransaction;

import com.workup.payments.commands.PaymentCommand;
import com.workup.payments.mapper.WalletTransactionMapper;
import com.workup.payments.models.WalletTransaction;
import com.workup.shared.commands.payments.dto.WalletTransactionDTO;
import com.workup.shared.commands.payments.wallettransaction.requests.GetWalletTransactionsRequest;
import com.workup.shared.commands.payments.wallettransaction.responses.GetWalletTransactionsResponse;
import com.workup.shared.enums.HttpStatusCode;

import java.util.List;

public class GetWalletTransactionsCommand extends PaymentCommand<GetWalletTransactionsRequest, GetWalletTransactionsResponse> {

    @Override
    public GetWalletTransactionsResponse Run(GetWalletTransactionsRequest request) {
        List<WalletTransaction> savedTransactions = getWalletTransactionRepository()
                .findAllByWalletId(request.getFreelancerId());
        List<WalletTransactionDTO> walletTransactionDTOS = WalletTransactionMapper.mapToWalletTransactionDTOs(savedTransactions);

        System.out.println("[x] Wallet transactions fetched : " + walletTransactionDTOS);

        return GetWalletTransactionsResponse.builder()
                .withStatusCode(HttpStatusCode.OK)
                .withTransactions(walletTransactionDTOS)
                .build();
    }
}
