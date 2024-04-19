package com.workup.payments.commands.wallet;

import com.workup.payments.commands.PaymentCommand;
import com.workup.payments.models.Wallet;
import com.workup.shared.commands.payments.wallet.requests.CreateWalletRequest;
import com.workup.shared.commands.payments.wallet.responses.CreateWalletResponse;

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
                    .withSuccess(true)
                    .build();
        } catch (Exception e) {
            System.out.println("[x] Wallet creation failed : " + e.getMessage());

            return CreateWalletResponse.builder()
                    .withSuccess(false)
                    .build();
        }
    }
}
