package com.db.test.service;

import com.db.test.model.BankAccount;
import com.db.test.repository.BankAccountRepository;
import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
public class BankAccountService {
    private final BankAccountRepository BankRepository;

    public BankAccountService() {
        BankRepository = new BankAccountRepository();
    }
    public List<BankAccount> getAllAccounts() {
        return BankRepository.getAllDocuments();
    }
    public boolean addAccount(BankAccount account){
        return BankRepository.createDocument(account);
    }
}
