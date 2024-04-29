package com.workup.payments.models;

import com.workup.shared.enums.payments.WalletTransactionType;
import jakarta.persistence.*;
import java.util.Date;
import javax.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
public class WalletTransaction {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false)
  private String id;

  @Column(name = "wallet_id", nullable = false)
  private String walletId;

  @Column(name = "amount", nullable = false, columnDefinition = "decimal default 0.00")
  @DecimalMin(value = "0.00", message = "Amount must be greater than 0.00")
  private double amount;

  @Column(name = "payment_transaction_id")
  private String paymentTransactionId;

  @Column(name = "description")
  private String description;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @Column(name = "transaction_type")
  private WalletTransactionType transactionType;

  public WalletTransaction(
      String walletId,
      double amount,
      String paymentTransactionId,
      String description,
      WalletTransactionType transactionType) {
    this.walletId = walletId;
    this.amount = amount;
    this.paymentTransactionId = paymentTransactionId;
    this.description = description;
    this.transactionType = transactionType;
  }
}
