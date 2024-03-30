package com.workup.payments.paymenttransaction;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

import javax.validation.constraints.DecimalMin;

@Entity
@Data
@NoArgsConstructor
@Table(name = "PaymentTransaction")
public class PaymentTransaction {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "payment_request_id")
    private String paymentRequestId;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    @DecimalMin(value = "0.00", message = "Amount must be greater than 0.00")
    private double amount;

    @Column(name = "currency", nullable = false, length = 3, columnDefinition = "VARCHAR(3) DEFAULT 'USD'")
    private String currency;

    @Column(name = "external_transaction_id", length = 255)
    private String externalTransactionId;

    @Column(name = "payment_gateway", length = 255)
    private String paymentGateway;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false, updatable = false)
    private Date updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'PENDING'")
    private PaymentTransactionStatus status;


    // Enum for payment transaction status
    public enum PaymentTransactionStatus {
        PENDING,
        COMPLETED,
        FAILED 
    }
}