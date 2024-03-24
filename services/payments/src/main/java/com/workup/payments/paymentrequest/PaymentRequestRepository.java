package com.workup.payments.paymentrequest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, String> {
    List<PaymentRequest> findAllByFreelancerId(String freelancerId);

    List<PaymentRequest> findAllByClientId(String clientId);

}
