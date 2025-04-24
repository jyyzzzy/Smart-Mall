package com.smartmall.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String productId;
    private String productName;
    private String description;
    private String merchantId;
    private Double price;
    private Integer stock;
    private String category;
    private Date createdAt;
    private Date updatedAt;
}
