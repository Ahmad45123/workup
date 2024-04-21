package com.workup.payments.repositories;

import com.workup.payments.models.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, String> {

    WalletTransaction findByWalletId(String walletId);
    List<WalletTransaction> findAllByWalletId(String walletId);
}
