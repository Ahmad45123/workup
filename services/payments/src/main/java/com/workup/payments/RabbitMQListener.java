package com.workup.payments;

import com.workup.payments.commands.PaymentCommandMap;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = "paymentsqueue")
public class RabbitMQListener {

    @Autowired
    public PaymentCommandMap commandMap;
    // TODO: Implement receive method for each command

}
