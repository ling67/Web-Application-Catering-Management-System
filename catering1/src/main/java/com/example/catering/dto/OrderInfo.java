package com.example.catering.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderInfo {
    private Integer orderId;   //create not need 
    private Integer customerId;   
    private Integer orderStatus;  
    private List<OrderItemInfo> orderItemInfos;
}
