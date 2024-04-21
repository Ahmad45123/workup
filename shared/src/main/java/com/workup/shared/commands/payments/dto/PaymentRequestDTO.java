package com.workup.shared.commands.payments.dto;

import com.workup.shared.enums.payments.PaymentRequestStatus;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;

@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class PaymentRequestDTO {

  private String id;
  private String freelancerId;
  private String clientId;
  private double amount;
  private String description;
  private Date createdAt;
  private Date updatedAt;
  private PaymentRequestStatus status;
}
