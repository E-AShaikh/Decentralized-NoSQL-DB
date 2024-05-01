package com.db.service;

import com.db.model.BankAccount;
import com.db.repository.BankAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class BankAccountService {
    private final BankAccountRepository BankRepository;

    public List<BankAccount> getAllAccounts() {
        return BankRepository.getAllDocuments();
    }
    public BankAccount addAccount(BankAccount account){
        return BankRepository.createDocument(account);
    }
}
