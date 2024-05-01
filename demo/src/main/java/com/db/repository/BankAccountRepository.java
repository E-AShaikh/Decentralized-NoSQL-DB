package com.db.repository;

import com.db.repository.CRUDNoSQLRepository;
import com.db.model.BankAccount;
import org.springframework.stereotype.Repository;

@Repository
public class BankAccountRepository extends CRUDNoSQLRepository<BankAccount, String> {
}