package com.workup.payments.paymentrequest;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PaymentRequestService {
    private final PaymentRequestRepository paymentRequestRepository;

    public PaymentRequest getPaymentRequest(String paymentRequestId) {
        return paymentRequestRepository.findById(paymentRequestId).orElse(null);
    }

    public void createPaymentRequest(PaymentRequest paymentRequest) {
        paymentRequestRepository.save(paymentRequest);
    }

    public List<PaymentRequest> findAllPaymentRequestsByFreelancerId(String freelancerId) {
        return paymentRequestRepository.findAllByFreelancerId(freelancerId);
    }

    public List<PaymentRequest> findAllPaymentRequestsByClientId(String clientId) {
        return paymentRequestRepository.findAllByClientId(clientId);
    }
}
