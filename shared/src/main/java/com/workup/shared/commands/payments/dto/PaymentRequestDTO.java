package com.workup.shared.commands.payments.dto;

import com.workup.shared.enums.payments.PaymentRequestStatus;
import java.util.Date;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder(setterPrefix = "with")
@Jacksonized
@Data
public class PaymentRequestDTO {

  private String id;
  private String freelancerId;
  private String clientId;
  private double amount;
  private String referenceId;
  private Date createdAt;
  private Date updatedAt;
  private PaymentRequestStatus status;
}
