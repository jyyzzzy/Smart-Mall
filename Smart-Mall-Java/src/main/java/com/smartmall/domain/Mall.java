package com.smartmall.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 商场表 (Mall Table)实体类
 *
 * @author ccut_zzy
 * @date 2025-05-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mall {

    /**
     * 商场ID (Mall ID, UUID)
     */
    private String mallId;

    /**
     * 商场用户ID (User ID)
     */
    private String userId;

    /**
     * 商场名称 (Mall Name)
     */
    private String mallName;

    /**
     * 描述
     */
    private String description;

    /**
     * 地址
     */
    private String address;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 联系邮箱
     */
    private String contactEmail;
}
