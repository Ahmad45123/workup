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
//@NoArgsConstructor
//@EnableJpaAuditing
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
  private PaymentRequestStatus status = PaymentRequestStatus.PENDING;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_at", nullable = false, updatable = false)
  private Date createdAt;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated_at", nullable = false)
  private Date updatedAt;
}
