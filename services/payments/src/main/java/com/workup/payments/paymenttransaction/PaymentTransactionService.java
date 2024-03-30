package com.workup.payments.paymenttransaction;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentTransactionService {

    private final PaymentTransactionRepository paymentTransactionRepository;

    public Optional<PaymentTransaction> get(String paymentTransactionId) {
        return paymentTransactionRepository.findById(paymentTransactionId);
    }

    public PaymentTransaction create(PaymentTransaction paymentTransaction) {
        return paymentTransactionRepository.save(paymentTransaction);
    }

    public List<PaymentTransaction> findAllByClientId(String clientId) {
        return paymentTransactionRepository.findAllByClientId(clientId);
    }

    public List<PaymentTransaction> findAllByFreelancerId(String freelancerId) {
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
