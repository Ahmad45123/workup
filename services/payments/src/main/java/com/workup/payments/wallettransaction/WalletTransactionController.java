package com.workup.payments.wallettransaction;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/payments/wallettransaction")
@AllArgsConstructor
public class WalletTransactionController {
    private final WalletTransactionService walletTransactionService;

    @GetMapping("/{wallet_transaction_id}")
    public WalletTransaction getWalletTransaction(@PathVariable("wallet_transaction_id") String walletTransactionId) {
        return walletTransactionService.getWalletTransaction(walletTransactionId);
    }

    @PostMapping
    public void createWalletTransaction(@RequestBody WalletTransaction walletTransaction) {
        walletTransactionService.createWalletTransaction(walletTransaction);
    }

    @GetMapping("/{wallet_id}")
    public List<WalletTransaction> getWalletTransactions(@PathVariable("wallet_id") String walletId) {
        return walletTransactionService.getWalletTransactions(walletId);
    }
    

}
