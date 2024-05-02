package com.db.controller;

import com.db.model.BankAccount;
import com.db.service.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class BankAccountController {
    private final BankAccountService bankAccountService;

    @GetMapping
    public List<BankAccount> fetchAllEmails() {
        return bankAccountService.getAllAccounts();
    }
    @PostMapping("/addAccount")
    public boolean addEmail(@RequestBody BankAccount account){
        return bankAccountService.addAccount(account);
    }
}