package com.workup.payments.repositories;

import com.workup.payments.models.PaymentRequest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, String> {
  List<PaymentRequest> findAllByFreelancerId(String freelancerId);

  List<PaymentRequest> findAllByClientId(String clientId);
}
