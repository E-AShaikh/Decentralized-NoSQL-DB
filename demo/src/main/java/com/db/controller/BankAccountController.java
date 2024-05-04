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
    public List<BankAccount> getAllAccounts() {
        return bankAccountService.getAllAccounts();
    }
    @PostMapping("/addAccount")
    public boolean addEmail(@RequestBody BankAccount account){
        return bankAccountService.addAccount(account);
    }

    @GetMapping("/{ID}")
    public BankAccount getBankAccount(@PathVariable String ID) {
        return bankAccountService.getBankAccount(ID);
    }
    @PostMapping("/{ID}/withdrawal")
    public boolean withdrawal(@PathVariable String ID, @RequestBody Long amount) {
        return bankAccountService.withdrawal(ID, amount);
    }
    @PostMapping("/{ID}/deposit")
    public boolean deposit(@PathVariable String ID, @RequestBody Long amount) {
        return bankAccountService.deposit(ID, amount);
    }
}