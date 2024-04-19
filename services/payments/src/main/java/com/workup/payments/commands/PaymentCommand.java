package com.workup.payments.commands;

import com.workup.payments.repositories.PaymentRequestRepository;
import com.workup.payments.repositories.PaymentTransactionRepository;
import com.workup.payments.repositories.WalletRepository;
import com.workup.payments.repositories.WalletTransactionRepository;
import com.workup.shared.commands.Command;
import com.workup.shared.commands.CommandRequest;
import com.workup.shared.commands.CommandResponse;
import lombok.Data;

@Data
public abstract class PaymentCommand<T extends CommandRequest, Q extends CommandResponse> implements Command<T, Q> {

    private PaymentRequestRepository paymentRequestRepository;
    private PaymentTransactionRepository paymentTransactionRepository;
    private WalletRepository walletRepository;
    private WalletTransactionRepository walletTransactionRepository;
}
