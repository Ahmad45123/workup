package com.workup.payments.paymentrequest;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/payments/request")
@AllArgsConstructor
public class PaymentRequestController {
    private final PaymentRequestService paymentRequestService;

    @GetMapping("/{payment_request_id}")
    public PaymentRequest getPaymentRequest(@PathVariable("payment_request_id") String paymentRequestId) {
        return paymentRequestService.getPaymentRequest(paymentRequestId);
    }

    @PostMapping
    public void createPaymentRequest(@RequestBody PaymentRequest paymentRequest) {
        paymentRequestService.createPaymentRequest(paymentRequest);
    }

    @GetMapping("/freelancer/{freelancer_id}")
    public List<PaymentRequest> findAllPaymentRequestsByFreelancerId(@PathVariable("freelancer_id") String freelancerId) {
        return paymentRequestService.findAllPaymentRequestsByFreelancerId(freelancerId);
    }

    @GetMapping("/client/{client_id}")
    public List<PaymentRequest> findAllPaymentRequestsByClientId(@PathVariable("client_id") String clientId) {
        return paymentRequestService.findAllPaymentRequestsByClientId(clientId);
    }

}
