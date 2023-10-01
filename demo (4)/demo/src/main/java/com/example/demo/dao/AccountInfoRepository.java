package com.example.demo.dao;

import com.example.demo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountInfoRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByEmail(String email);
}
