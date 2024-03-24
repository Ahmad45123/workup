package com.workup.payments.wallettransaction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class WalletTransactionService {
    private final WalletTransactionRepository walletTransactionRepository;

    public WalletTransaction getWalletTransaction(String walletTransactionId) {
        return walletTransactionRepository.findById(walletTransactionId).orElse(null);
    }
    

    public void createWalletTransaction(WalletTransaction walletTransaction) {
        walletRepository.save(walletTransaction); 
    }

    public void getWalletTransactions(String walletId) {
        return walletTransactionRepository.findAllByWalletId(walletId);
    }

 

    

}
