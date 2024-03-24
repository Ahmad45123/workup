package com.workup.payments.wallet;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;

    public Wallet getWallet(String freelancerId) {
        return walletRepository.findById(freelancerId).orElse(null);
    }

    public void createWallet(Wallet wallet) {
        if (walletRepository.existsById(wallet.getFreelancerId())) {
            throw new IllegalStateException("Wallet already exists");
        }
        walletRepository.save(wallet);
    }

    @Transactional
    public void withdraw(String freelancerId, double amount) {
        if (amount <= 0) {
            throw new IllegalStateException("Amount must be greater than 0");
        }
        Wallet wallet = walletRepository.findById(freelancerId)
                .orElseThrow(() -> new IllegalStateException("Wallet not found"));
        if (wallet.getBalance() < amount) {
            throw new IllegalStateException("Insufficient balance");
        }
        wallet.setBalance(wallet.getBalance() - amount);
        walletRepository.save(wallet);
        // TODO: add transaction record to WalletTransaction table
    }

}
