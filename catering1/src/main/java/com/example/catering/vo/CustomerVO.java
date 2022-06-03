package com.example.catering.vo;

import com.example.catering.models.Account;
import com.example.catering.models.Customer;

import lombok.Data;

@Data
public class CustomerVO {
    private String userName;
    private String password;
    private String phoneNum;
    private String email;
    private String address;

    public Customer toCustomer() {
        Customer customer = new Customer();
        customer.setPhoneNum(this.phoneNum);
        customer.setEmail(this.email);
        customer.setAddress(this.address);
        customer.setAccount(new Account(this.userName, this.password));
        return customer;
    }
}
