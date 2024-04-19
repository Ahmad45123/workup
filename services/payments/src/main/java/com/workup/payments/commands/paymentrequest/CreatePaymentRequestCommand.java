package com.workup.payments.commands.paymentrequest;

import com.workup.payments.commands.PaymentCommand;
import com.workup.payments.models.PaymentRequest;
import com.workup.shared.commands.payments.paymentrequest.requests.CreatePaymentRequestRequest;
import com.workup.shared.commands.payments.paymentrequest.responses.CreatePaymentRequestResponse;

public class CreatePaymentRequestCommand extends PaymentCommand<CreatePaymentRequestRequest, CreatePaymentRequestResponse> {

    @Override
    public CreatePaymentRequestResponse Run(CreatePaymentRequestRequest request) {
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .withClientId(request.getClientId())
                .withFreelancerId(request.getFreelancerId())
                .withAmount(request.getAmount())
                .withDescription(request.getDescription())
                .build();
        try {
            PaymentRequest savedPaymentRequest = getPaymentRequestRepository().save(paymentRequest);

            System.out.println("[x] Payment request created : " + savedPaymentRequest);

            return CreatePaymentRequestResponse.builder()
                    .withSuccess(true)
                    .withPaymentRequestId(savedPaymentRequest.getId())
                    .build();
        } catch (Exception e) {
            System.out.println("[x] Payment request creation failed : " + e.getMessage());

            return CreatePaymentRequestResponse.builder()
                    .withSuccess(false)
                    .withPaymentRequestId(null)
                    .build();
        }
    }

}
