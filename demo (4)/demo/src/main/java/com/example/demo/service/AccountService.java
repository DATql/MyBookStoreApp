package com.example.demo.service;

import com.example.demo.dao.AccountRepository;
import com.example.demo.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public Account save(Account user) {
        return accountRepository.save(user);
    }
}
