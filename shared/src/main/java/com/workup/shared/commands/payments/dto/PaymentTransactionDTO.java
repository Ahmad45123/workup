package com.workup.shared.commands.payments.dto;

import com.workup.shared.enums.payments.PaymentTransactionStatus;
import java.util.Date;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder(setterPrefix = "with")
@Jacksonized
@Data
public class PaymentTransactionDTO {

  private String id;
  private String paymentRequestId;
  private double amount;
  private Date createdAt;
  private Date updatedAt;
  private PaymentTransactionStatus status;
}
