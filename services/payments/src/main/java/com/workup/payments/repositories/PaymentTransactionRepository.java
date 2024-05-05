package com.workup.payments.repositories;

import com.workup.payments.models.PaymentTransaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, String> {
  @Query(
      value =
          "SELECT * FROM payment_transaction pt WHERE pt.payment_request_id IN (SELECT pr.id FROM payment_request pr WHERE pr.freelancer_id = ?1)",
      nativeQuery = true)
  List<PaymentTransaction> findAllByFreelancerId(String freelancerId);

  @Query(
      value =
          "SELECT * FROM payment_transaction pt WHERE pt.payment_request_id IN (SELECT pr.id FROM payment_request pr WHERE pr.client_id = ?1)",
      nativeQuery = true)
  List<PaymentTransaction> findAllByClientId(String clientId);
}
