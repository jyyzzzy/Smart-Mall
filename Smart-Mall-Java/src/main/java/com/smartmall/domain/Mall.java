package com.smartmall.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
//商场信息
public class Mall {
    private String mallId;
    private String mallName;
    private String userId;
    private String location;
    private String contactPhone;
    private String contactEmail;
    private Date createdAt;
    private Date updatedAt;
}
