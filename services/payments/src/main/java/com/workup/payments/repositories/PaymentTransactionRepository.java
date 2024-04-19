package com.workup.payments.repositories;

import com.workup.payments.models.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, String> {
    @Query("SELECT * FROM PaymentTransaction pt WHERE pt.payment_request_id IN (SELECT pr.id FROM PaymentRequest pr WHERE pr.freelancer_id = ?0)")
    List<PaymentTransaction> findAllByFreelancerId(String freelancerId);

    @Query("SELECT * FROM PaymentTransaction pt WHERE pt.payment_request_id IN (SELECT pr.id FROM PaymentRequest pr WHERE pr.client_id = ?0)")
    List<PaymentTransaction> findAllByClientId(String clientId);

}
