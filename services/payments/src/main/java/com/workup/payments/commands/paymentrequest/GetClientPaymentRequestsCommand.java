package com.workup.payments.commands.paymentrequest;

import com.workup.payments.commands.PaymentCommand;
import com.workup.payments.mapper.PaymentRequestMapper;
import com.workup.payments.models.PaymentRequest;
import com.workup.shared.commands.payments.dto.PaymentRequestDTO;
import com.workup.shared.commands.payments.paymentrequest.requests.GetClientPaymentRequestsRequest;
import com.workup.shared.commands.payments.paymentrequest.responses.GetClientPaymentRequestsResponse;

import java.util.List;

public class GetClientPaymentRequestsCommand extends PaymentCommand<GetClientPaymentRequestsRequest, GetClientPaymentRequestsResponse> {

    @Override
    public GetClientPaymentRequestsResponse Run(GetClientPaymentRequestsRequest request) {
        List<PaymentRequest> savedRequests = getPaymentRequestRepository()
                .findAllByClientId(request.getClientId());

        List<PaymentRequestDTO> paymentRequestDTOS = PaymentRequestMapper.mapToPaymentRequestDTOs(savedRequests);

        System.out.println("[x] Payment requests fetched : " + paymentRequestDTOS);

        return GetClientPaymentRequestsResponse.builder()
                .withSuccess(true)
                .withRequests(paymentRequestDTOS)
                .build();
    }
}
