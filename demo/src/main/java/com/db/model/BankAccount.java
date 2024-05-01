package com.db.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BankAccount {
    private String _id;
    private String clientName;
    private double balance;
    private boolean hasInsurance;
    private BankAccountType accountType;

    public BankAccount(String clientName, double balance, boolean hasInsurance, BankAccountType accountType) {
        this.clientName = clientName;
        this.balance = balance;
        this.hasInsurance = hasInsurance;
        this.accountType = accountType;
    }
}
