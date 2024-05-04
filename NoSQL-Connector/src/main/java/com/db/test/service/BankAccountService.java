package com.db.test.service;

import com.db.json.JsonBuilder;
import com.db.test.model.BankAccount;
import com.db.test.repository.BankAccountRepository;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
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

    public boolean withdrawal(String ID, long amount) {
        BankAccount account = getBankAccount(ID);
        System.out.println(account.getBalance());
        return updateBankAccountBalance(ID, account.getBalance() - amount);
    }

    public boolean deposit(String ID, long amount) {
        BankAccount account = getBankAccount(ID);
        return updateBankAccountBalance(ID, account.getBalance() - amount);
    }
    public boolean updateBankAccountBalance(String ID, double balance) {
        JSONObject updateJson = JsonBuilder.getBuilder()
                .add("balance", balance)
                .build();
        return BankRepository.updateDocument(ID, updateJson);
    }

    public BankAccount getBankAccount(String ID) {
        return BankRepository.getDocumentByID(ID);
    }
}
