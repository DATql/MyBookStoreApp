package com.example.demo.service;

import com.example.demo.security.UserInfoDetails;
import com.example.demo.dao.AccountInfoRepository;
import com.example.demo.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountInfoService implements UserDetailsService {

    @Autowired
    private AccountInfoRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Account> userDetail = repository.findByEmail(email);

        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + email));
    }

    public String addUser(Account userInfo) {
        userInfo.setId(35);
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User Added Successfully";
    }


}
