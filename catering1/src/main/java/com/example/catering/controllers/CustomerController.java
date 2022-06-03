package com.example.catering.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.catering.dto.MessageDetails;
import com.example.catering.models.Customer;
import com.example.catering.repositories.CustomerRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public Iterable<Customer> getCustomers() {
        Iterable<Customer> customers = this.customerRepository.findAll();
        return customers;
    }

    @PostMapping
    public ResponseEntity<MessageDetails> addCustomer(@RequestBody Customer customer) {
        customer.setCreated(LocalDateTime.now());
        this.customerRepository.save(customer);
        MessageDetails msg = new MessageDetails("The new customer was inserted successfully.", true);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }

    @DeleteMapping
    public ResponseEntity<MessageDetails> removeCustomer(@RequestParam Integer customerId) {
        Customer customer = this.customerRepository.findById(customerId).get();
        if (customer != null) {
            this.customerRepository.deleteById(customerId);
            MessageDetails sucMsg = new MessageDetails("The customer was removed successfully.", true);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(sucMsg);
        } else {
            MessageDetails failMsg = new MessageDetails("Could not be found. customerId = " + customerId);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(failMsg);
        }
    }

    @PutMapping
    public ResponseEntity<MessageDetails> updateCustomer(@RequestBody Customer customer) {
        Customer oldCustomer = this.customerRepository.findById(customer.getId()).get();
        if (oldCustomer != null) {    
            oldCustomer.setEmail(customer.getEmail());     
            oldCustomer.setPhoneNum(customer.getPhoneNum());
            oldCustomer.setAddress(customer.getAddress());
            this.customerRepository.save(oldCustomer);   
            MessageDetails msg = new MessageDetails("The customer was updated successfully.", true);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
        } else {
            MessageDetails failMsg = new MessageDetails("Could not be found. customerId = " + customer.getId());
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(failMsg);
        }
    }

}    
