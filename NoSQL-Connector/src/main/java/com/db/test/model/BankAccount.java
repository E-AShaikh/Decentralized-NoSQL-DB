package com.db.test.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BankAccount {
    private long id;
    private String clientName;
    private double balance;
    private boolean hasInsurance;
    private String accountType;

    public BankAccount(long id, String clientName, double balance, boolean hasInsurance, String accountType) {
        this.id = id;
        this.clientName = clientName;
        this.balance = balance;
        this.hasInsurance = hasInsurance;
        this.accountType = accountType;
    }
}
