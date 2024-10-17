package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account registerAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().trim().isEmpty()) {
            return null;
        }

        if (account.getPassword() == null || account.getPassword().length() < 4) {
            return null;
        }

        Account existingAccount = accountRepository.findByUsername(account.getUsername());
        if (existingAccount != null) {
            Account duplicateAccount = new Account();
            duplicateAccount.setAccountId(-1);
            return duplicateAccount;
        }

        return accountRepository.save(account);
    }

    public Account login(Account account) {
        Account existingAccount = accountRepository.findByUsername(account.getUsername());
        if (existingAccount != null && existingAccount.getPassword().equals(account.getPassword())) {
            return existingAccount;
        } else {
            return null;
        }
    }
}
