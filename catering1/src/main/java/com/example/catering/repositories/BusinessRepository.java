package com.example.catering.repositories;

import com.example.catering.models.Business;

import org.springframework.data.repository.CrudRepository;

public interface BusinessRepository extends CrudRepository<Business, Integer> {
    
}
