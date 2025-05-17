package com.smartmall.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 顾客表 (Customer Table)实体类
 *
 * @author ccut_zzy
 * @date 2025-05-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    /**
     * 顾客ID (Customer ID, UUID)
     */
    private String customerId;

    /**
     * 顾客用户ID (User ID)
     */
    private String userId;

    /**
     * 顾客名称
     */
    private String customerName;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 联系邮箱
     */
}