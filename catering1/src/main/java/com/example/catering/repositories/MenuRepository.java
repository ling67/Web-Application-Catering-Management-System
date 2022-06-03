package com.example.catering.repositories;
import com.example.catering.models.Menu;
import org.springframework.data.repository.CrudRepository;

public interface MenuRepository extends CrudRepository<Menu, Integer>{
    
}
