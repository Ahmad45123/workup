package com.workup.payments.repositories;

import com.workup.payments.models.WalletTransaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletTransactionRepository
  extends JpaRepository<WalletTransaction, String> {
  WalletTransaction findByWalletId(String walletId);

  List<WalletTransaction> findAllByWalletId(String walletId);
}
