package com.example.demo.controller;

import com.example.demo.requestmodels.AuthRequest;
import com.example.demo.service.JwtService;
import com.example.demo.service.AccountInfoService;
import com.example.demo.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AccountController {

    @Autowired
    private AccountInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody Account userInfo) {
        return service.addUser(userInfo);
    }

    @GetMapping("/Employer/Profile")
    @PreAuthorize("hasAuthority('employer')")
    public String userProfile() {
        return "Welcome to User Profile";
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('admin')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(authRequest.getUsername(),authRequest.getState());
                System.out.println("User '" + authRequest.getUsername() + "' successfully authenticated and received JWT token: " + token);
                return token;
            } else {
                throw new UsernameNotFoundException("invalid user request !");
            }
        } catch (AuthenticationException e) {
            System.err.println("Authentication error: " + e.getMessage());
            throw e;
        }
    }


}
