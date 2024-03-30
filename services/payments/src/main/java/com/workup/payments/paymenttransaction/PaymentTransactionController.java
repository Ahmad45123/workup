package com.workup.payments.paymenttransaction;


import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(path = "api/v1/payments/transactions")
@AllArgsConstructor
public class PaymentTransactionController {

    private final PaymentTransactionService paymentTransactionService;


    // Get a payment transaction by ID
    @GetMapping("/{payment_transaction_id}")
    public ResponseEntity<PaymentTransaction> getPaymentTransactionById(@PathVariable String id) {
        return paymentTransactionService.get(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a payment transaction
    @PostMapping
    public ResponseEntity<PaymentTransaction> createPaymentTransaction(@RequestBody PaymentTransaction paymentTransaction) {
        PaymentTransaction createdTransaction = paymentTransactionService.create(paymentTransaction);
        return ResponseEntity.ok(createdTransaction);
    }

    // List all payment transactions
    @GetMapping
    public ResponseEntity<Iterable<PaymentTransaction>> getAllPaymentTransactions() {
        return ResponseEntity.ok(paymentTransactionService.findAll());
    }

    // List all payment transactions for a client
    @GetMapping("/client/{client_id}")
    public List<PaymentTransaction> findAllByClientId(@PathVariable("client_id") String clientId) {
        return paymentTransactionService.findAllByClientId(clientId);
    }

    // List all payment transactions for a freelancer
    @GetMapping("/freelancer/{freelancer_id}")
    public List<PaymentTransaction> findAllByFreelancerId(@PathVariable("freelancer_id") String freelancerId) {
        return paymentTransactionService.findAllByFreelancerId(freelancerId);
    }


    // Delete a payment transaction
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePaymentTransaction(@PathVariable String id) {
        if (paymentTransactionService.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
