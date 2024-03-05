package com.workup.shared.commands.payments;

import java.util.Date;

public class WalletTransaction {
    private String id;
    private String wallet_id;
    private double amount;
    private String payment_transaction_id;
    private String description;
    private Date created_at;
    private String transaction_type;
}