package com.workup.payments.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.validation.constraints.DecimalMin;
import java.util.Date;

@Entity
@Data
@Builder(setterPrefix = "with")
@AllArgsConstructor
public class Wallet {
    @Id
    private String freelancerId;

    @Column(name = "balance", nullable = false, columnDefinition = "decimal default 0.00")
    @DecimalMin(value = "0.00", message = "Balance must be greater than 0.00")
    private double balance;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    public Wallet(String freelancerId) {
        this.freelancerId = freelancerId;
        this.balance = 0;
    }
}
