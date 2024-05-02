package com.db.test.repository;

import com.db.test.model.BankAccount;
import com.db.repository.CRUDNoSQLRepository;
//import org.springframework.stereotype.Repository;

//@Repository
public class BankAccountRepository extends CRUDNoSQLRepository<BankAccount, String> {
}