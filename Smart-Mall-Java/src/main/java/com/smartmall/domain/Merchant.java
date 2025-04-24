package com.smartmall.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Merchant {
    private String merchantId;
    private String merchantName;
    private String userId;
    private String mallId;
    private String contactPhone;
    private String contactEmail;
    private String address;
    private Date createdAt;
    private Date updatedAt;
}
