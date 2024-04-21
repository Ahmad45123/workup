package com.workup.shared.commands.payments.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.enums.payments.PaymentTransactionStatus;
import java.util.Date;
import lombok.experimental.SuperBuilder;

@SuperBuilder(setterPrefix = "with")
@JsonDeserialize
public class PaymentTransactionDTO {

  private String id;
  private String paymentRequestId;
  private double amount;
  private Date createdAt;
  private Date updatedAt;
  private PaymentTransactionStatus status;
}
