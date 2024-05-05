package com.workup.payments.commands;

import com.workup.payments.commands.paymentrequest.*;
import com.workup.payments.commands.paymenttransaction.GetClientPaymentTransactionsCommand;
import com.workup.payments.commands.paymenttransaction.GetFreelancerPaymentTransactionsCommand;
import com.workup.payments.commands.wallet.CreateWalletCommand;
import com.workup.payments.commands.wallet.GetWalletCommand;
import com.workup.payments.commands.wallettransaction.CreateWalletTransactionCommand;
import com.workup.payments.commands.wallettransaction.GetWalletTransactionCommand;
import com.workup.payments.commands.wallettransaction.GetWalletTransactionsCommand;
import com.workup.payments.commands.wallettransaction.WithdrawFromWalletCommand;
import com.workup.payments.repositories.PaymentRequestRepository;
import com.workup.payments.repositories.PaymentTransactionRepository;
import com.workup.payments.repositories.WalletRepository;
import com.workup.payments.repositories.WalletTransactionRepository;
import com.workup.shared.commands.CommandMap;
import com.workup.shared.commands.CommandRequest;
import com.workup.shared.commands.CommandResponse;
import com.workup.shared.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentCommandMap
    extends CommandMap<PaymentCommand<? extends CommandRequest, ? extends CommandResponse>> {

  @Autowired private PaymentRequestRepository paymentRequestRepository;

  @Autowired private PaymentTransactionRepository paymentTransactionRepository;

  @Autowired private WalletRepository walletRepository;

  @Autowired private WalletTransactionRepository walletTransactionRepository;

  @Autowired private RedisService redisService;

  public void registerCommands() {
    /* PaymentRequest commands */
    commands.put("CreatePaymentRequest", CreatePaymentRequestCommand.class);
    commands.put("GetClientPaymentRequests", GetClientPaymentRequestsCommand.class);
    commands.put("GetFreelancerPaymentRequests", GetFreelancerPaymentRequestsCommand.class);
    commands.put("GetPaymentRequest", GetPaymentRequestCommand.class);
    commands.put("PayPaymentRequest", PayPaymentRequestCommand.class);

    /* PaymentTransaction commands */
    commands.put("GetClientPaymentTransactions", GetClientPaymentTransactionsCommand.class);
    commands.put("GetFreelancerPaymentTransactions", GetFreelancerPaymentTransactionsCommand.class);

    /* Wallet commands */
    commands.put("CreateWallet", CreateWalletCommand.class);
    commands.put("GetWallet", GetWalletCommand.class);

    /* WalletTransaction commands */
    commands.put("CreateWalletTransaction", CreateWalletTransactionCommand.class);
    commands.put("GetWalletTransaction", GetWalletTransactionCommand.class);
    commands.put("GetWalletTransactions", GetWalletTransactionsCommand.class);
    commands.put("WithdrawFromWallet", WithdrawFromWalletCommand.class);
  }

  @Override
  public void setupCommand(
      PaymentCommand<? extends CommandRequest, ? extends CommandResponse> command) {
    command.setPaymentRequestRepository(paymentRequestRepository);
    command.setPaymentTransactionRepository(paymentTransactionRepository);
    command.setWalletRepository(walletRepository);
    command.setWalletTransactionRepository(walletTransactionRepository);

    command.setRedisService(redisService);
  }
}
