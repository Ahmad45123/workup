package com.workup.payments.paymenttransaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, String> {
    List<PaymentTransaction> findAllByFreelancerId(String freelancerId);
    List<PaymentTransaction> findAllByClientId(String clientId);
}
