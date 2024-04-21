package com.workup.payments.commands.wallet;

import com.workup.payments.commands.PaymentCommand;
import com.workup.payments.models.Wallet;
import com.workup.shared.commands.payments.wallet.requests.CreateWalletRequest;
import com.workup.shared.commands.payments.wallet.responses.CreateWalletResponse;
import com.workup.shared.enums.HttpStatusCode;

public class CreateWalletCommand extends PaymentCommand<CreateWalletRequest, CreateWalletResponse> {

    @Override
    public CreateWalletResponse Run(CreateWalletRequest request) {
        Wallet wallet = Wallet.builder()
                .withFreelancerId(request.getFreelancerId())
                .build();
        try {
            Wallet savedWallet = getWalletRepository().save(wallet);

            System.out.println("[x] Wallet created : " + savedWallet);

            return CreateWalletResponse.builder()
                    .withStatusCode(HttpStatusCode.CREATED)
                    .build();
        } catch (Exception e) {
            System.out.println("[x] Wallet creation failed : " + e.getMessage());

            return CreateWalletResponse.builder()
                    .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
                    .withErrorMessage("An error occurred while creating payment request")
                    .build();
        }
    }
}
