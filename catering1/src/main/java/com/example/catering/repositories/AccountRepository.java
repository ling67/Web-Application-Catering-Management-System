package com.example.catering.repositories;

import java.util.Set;

import com.example.catering.models.Account;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Integer> {
    
    @Query(value = "SELECT * FROM account WHERE user_name = :username and user_password = :password", nativeQuery = true)
    Set<Account> findByNameAndPassword(String username, String password);

    @Query(value = "SELECT * FROM account WHERE user_name = :username", nativeQuery = true)
    Set<Account> findByName(String username);

}
