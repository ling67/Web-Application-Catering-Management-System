package com.example.catering.repositories;

import java.util.Set;

import com.example.catering.models.Customer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{
    @Query(value = "SELECT * FROM customer WHERE account_id = :accountId", nativeQuery = true)
    Set<Customer> findByAccountId(int accountId);
}
