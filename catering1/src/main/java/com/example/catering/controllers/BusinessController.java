package com.example.catering.controllers;

import com.example.catering.dto.MessageDetails;
import com.example.catering.enums.EmployeeStatusType;
import com.example.catering.exceptions.ResourceNotFoundException;
import com.example.catering.models.Business;
import com.example.catering.repositories.BusinessRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/business")
public class BusinessController {

    private final BusinessRepository businessRepository;

    public BusinessController(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }
    
    @GetMapping
    public Iterable<Business> getEmployees() {
        Iterable<Business> businesses = this.businessRepository.findAll();
        return businesses;
    }

    @PostMapping
    public ResponseEntity<MessageDetails> addEmployee(@RequestBody Business business) {
        this.businessRepository.save(business);
        MessageDetails msg = new MessageDetails("The new employee was inserted successfully.", true);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }

    @PutMapping
    public ResponseEntity<MessageDetails> updateEmployee(@RequestBody Business business) {
        Business oldBusiness = this.businessRepository.findById(business.getId()).get();
        if (oldBusiness != null) {
            // 这里要改一下规定哪些字段不能改
            this.businessRepository.save(oldBusiness);
            MessageDetails msg = new MessageDetails("The employee was updated successfully.", true);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
        } else {
            MessageDetails failMsg = new MessageDetails("Could not be found. businessId = " + business.getId());
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(failMsg);
        }
    }

    @PutMapping("/status")
    public ResponseEntity<MessageDetails> updateEmployeeStatus(@RequestParam Integer businessId,
            @RequestParam EmployeeStatusType status) {
        Business business = new Business();
        try {
            business = this.businessRepository.findById(businessId).get();   //为空有问题
        } catch (Exception e) {
            throw new ResourceNotFoundException("The business id not exist. businessId = " + businessId);
        }
        business.setStatus(status);
        businessRepository.save(business);
        MessageDetails msg = new MessageDetails("The business status updated successfully.", true);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }

    @DeleteMapping("/{businessId}")
    public ResponseEntity<MessageDetails> removeEmployee(@PathVariable Integer businessId) {
        Business business = this.businessRepository.findById(businessId).get();
        if (business != null) {
            this.businessRepository.deleteById(businessId);
            MessageDetails sucMsg = new MessageDetails("The employee was removed successfully.", true);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(sucMsg);
        } else {
            MessageDetails failMsg = new MessageDetails("Could not be found. customerId = " + businessId);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(failMsg);
        }
    }

}
