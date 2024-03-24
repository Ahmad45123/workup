package com.workup.payments.wallet;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/payments/wallet")
@AllArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @GetMapping("/{freelancer_id}")
    public Wallet getWallet(@PathVariable("freelancer_id") String freelancerId) {
        return walletService.getWallet(freelancerId);
    }

    @PostMapping
    public void createWallet(@RequestBody Wallet wallet) {
        walletService.createWallet(wallet);
    }

}
