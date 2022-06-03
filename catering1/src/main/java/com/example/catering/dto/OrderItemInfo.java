package com.example.catering.dto;

import lombok.Data;

@Data
public class OrderItemInfo {
    private Integer orderItemId;
    private Integer menuId;
    private Integer itemQuantity;
}
