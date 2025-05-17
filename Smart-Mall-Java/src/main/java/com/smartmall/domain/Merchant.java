package com.smartmall.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 商家表 (Merchant Table)实体类
 *
 * @author ccut_zzy
 * @date 2025-05-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Merchant {
    /**
     * 商家ID (Merchant ID, UUID)
     */
    private String merchantId;

    /**
     * 商家用户ID (User ID)
     */
    private String userId;

    /**
     * 所属商场ID (Mall ID)
     */
    private String mallId;

    /**
     * 商家名称 (Merchant Name)
     */
    private String merchantName;

    /**
     * 商家联系电话 (Contact Phone)
     */
    private String contactPhone;

    /**
     * 商家联系邮箱 (Contact Email)
     */
    private String contactEmail;

    /**
     * 商家地址 (Merchant Address)
     */
    private String address;

}
