package com.smartmall.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private String categoryId;
    private String categoryName;
    private String merchantId;
}