package com.db.test;


import com.db.test.model.BankAccount;
import com.db.test.model.BankAccountType;
import com.db.test.service.BankAccountService;

public class main {
    public static void main(String[] args) {
        BankAccount example = new BankAccount("ezz", 3000, true, BankAccountType.SALARY);
        BankAccountService service = new BankAccountService();
        service.addAccount(example);

        while (true) {
            // Perform some operation or wait for user input
            // For example, you could add a sleep to avoid high CPU usage
            try {
                Thread.sleep(1000); // Sleep for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}