package com.workup.payments.mapper;

import com.workup.payments.models.PaymentRequest;
import com.workup.shared.commands.payments.dto.PaymentRequestDTO;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentRequestMapper {

  private PaymentRequestMapper() {}

  public static PaymentRequestDTO mapToPaymentRequestDTO(PaymentRequest paymentRequest) {
    return PaymentRequestDTO
      .builder()
      .withId(paymentRequest.getId())
      .withClientId(paymentRequest.getClientId())
      .withFreelancerId(paymentRequest.getFreelancerId())
      .withAmount(paymentRequest.getAmount())
      .withDescription(paymentRequest.getDescription())
      .withCreatedAt(paymentRequest.getCreatedAt())
      .withUpdatedAt(paymentRequest.getUpdatedAt())
      .withStatus(paymentRequest.getStatus())
      .build();
  }

  public static List<PaymentRequestDTO> mapToPaymentRequestDTOs(
    List<PaymentRequest> paymentRequests
  ) {
    return paymentRequests
      .stream()
      .map(PaymentRequestMapper::mapToPaymentRequestDTO)
      .collect(Collectors.toList());
  }
}
