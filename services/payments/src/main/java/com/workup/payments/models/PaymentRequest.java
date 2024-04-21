package com.workup.payments.models;

import com.workup.shared.enums.payments.PaymentRequestStatus;
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
public class PaymentRequest {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false)
  private String id;

  @Column(name = "freelancer_id", nullable = false)
  private String freelancerId;

  @Column(name = "client_id", nullable = false)
  private String clientId;

  @Column(name = "amount", nullable = false, columnDefinition = "decimal default 0.00")
  @DecimalMin(value = "0.00", message = "Amount must be greater than 0.00")
  private double amount;

  @Column(name = "description")
  private String description;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private PaymentRequestStatus status;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_at", nullable = false, updatable = false)
  private Date createdAt;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated_at", nullable = false)
  private Date updatedAt;

  public PaymentRequest(
    String id,
    String freelancerId,
    String clientId,
    double amount,
    String description,
    PaymentRequestStatus status,
    Date createdAt,
    Date updatedAt
  ) {
    this.id = id;
    this.freelancerId = freelancerId;
    this.clientId = clientId;
    this.amount = amount;
    this.description = description;
    this.status = PaymentRequestStatus.PENDING;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }
}
