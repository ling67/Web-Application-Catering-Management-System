package com.example.catering.controllers;

import com.example.catering.vo.CustomerVO;
import com.example.catering.dto.MessageDetails;
import com.example.catering.models.Account;
import com.example.catering.models.Customer;
import com.example.catering.repositories.AccountRepository;
import com.example.catering.repositories.CustomerRepository;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public LoginController(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customerByName")
    public Customer login(@RequestParam String username) {
        Set<Account> accounts = accountRepository.findByName(username);

        if (accounts.size() == 0) {
            return null;
        }
        int accountId = accounts.iterator().next().getId();
        Set<Customer> customers = customerRepository.findByAccountId(accountId);
        return customers.iterator().next();
    }

    @PostMapping("/login")
    public ResponseEntity<MessageDetails> login(@RequestParam String username, @RequestParam String password) {
        Set<Account> oldAccount = accountRepository.findByNameAndPassword(username, password);

        if(oldAccount.size() == 0) {
            MessageDetails failMsg = new MessageDetails(
                    "Account could not be found. username = " + username, false);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(failMsg);
        } 
        MessageDetails msg = new MessageDetails("Login successfully.", true);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }

    @PostMapping("/register")
    public ResponseEntity<MessageDetails> register(@RequestBody CustomerVO customerVO) {
        Set<Account> account = accountRepository.findByName(customerVO.getUserName());
        if(account.size() == 0) {
            customerRepository.save(customerVO.toCustomer());
            MessageDetails msg = new MessageDetails("Register successfully.", true);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
        }
        MessageDetails failMsg = new MessageDetails(
                "Account could not be create, username has existed. username = " + customerVO.getUserName(), false);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(failMsg);
    }

}
