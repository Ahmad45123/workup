package com.workup.payments.paymenttransaction;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@AllArgsConstructor
public class PaymentTransactionService {

    private final PaymentTransactionRepository paymentTransactionRepository;

    public PaymentTransaction get(String paymentTransactionId) {
        return paymentTransactionRepository.findById(paymentTransactionId).orElse(null);
    }

    public PaymentTransaction create(PaymentTransaction paymentTransaction) {
        return paymentTransactionRepository.save(paymentTransaction);
    }

    public List<PaymentTransaction> findByClientId(String clientId) {
        return paymentTransactionRepository.findAllByClientId(clientId);
    }

    public List<PaymentTransaction> findByFreelancerId(String freelancerId) {
        return paymentTransactionRepository.findAllByFreelancerId(freelancerId);
    }

    public List<PaymentTransaction> findAll() {
        return paymentTransactionRepository.findAll();
    }



    @Transactional
    public boolean delete(String id) {
        return paymentTransactionRepository.findById(id)
                .map(transaction -> {
                    paymentTransactionRepository.delete(transaction);
                    return true;
                }).orElse(false);
    }
}
