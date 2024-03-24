package com.workup.payments.wallettransaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, String> {
}
