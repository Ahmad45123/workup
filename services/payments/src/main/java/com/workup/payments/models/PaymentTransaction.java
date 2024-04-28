package com.workup.payments.models;

import com.workup.shared.enums.payments.PaymentTransactionStatus;
import jakarta.persistence.*;
import java.util.Date;
import javax.validation.constraints.DecimalMin;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
@Builder(setterPrefix = "with")
public class PaymentTransaction {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false)
  private String id;

  @Column(name = "payment_request_id", nullable = false)
  private String paymentRequestId;

  @Column(name = "amount", nullable = false, columnDefinition = "decimal default 0.00")
  @DecimalMin(value = "0.00", message = "Amount must be greater than 0.00")
  private double amount;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private PaymentTransactionStatus status = PaymentTransactionStatus.PENDING;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_at", nullable = false, updatable = false)
  private Date createdAt;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated_at", nullable = false)
  private Date updatedAt;
}
