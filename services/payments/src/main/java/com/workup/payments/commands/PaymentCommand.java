package com.workup.payments.commands;

import com.workup.payments.repositories.PaymentRequestRepository;
import com.workup.payments.repositories.PaymentTransactionRepository;
import com.workup.payments.repositories.WalletRepository;
import com.workup.payments.repositories.WalletTransactionRepository;
import com.workup.shared.commands.Command;
import com.workup.shared.commands.CommandRequest;
import com.workup.shared.commands.CommandResponse;
import com.workup.shared.redis.RedisService;
import lombok.Data;
import org.springframework.amqp.core.AmqpTemplate;

@Data
public abstract class PaymentCommand<T extends CommandRequest, Q extends CommandResponse>
    implements Command<T, Q> {

  public PaymentRequestRepository paymentRequestRepository;
  public PaymentTransactionRepository paymentTransactionRepository;
  public WalletRepository walletRepository;
  public WalletTransactionRepository walletTransactionRepository;
  public RedisService redisService;
  public AmqpTemplate amqpTemplate;
}
