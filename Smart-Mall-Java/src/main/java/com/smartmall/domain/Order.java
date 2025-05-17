package com.smartmall.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单表 (Order Table)实体类
 *
 * @author ccut_zzy
 * @date 2025-05-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    /**
     * 订单ID (Order ID, UUID)
     */
    private String orderId;

    /**
     * 顾客ID (Customer ID)
     */
    private String customerId;

    /**
     * 商家ID (Merchant ID)
     */
    private String merchantId;

    /**
     * 订单总金额 (Total Amount)
     */
    private BigDecimal totalAmount;

    /**
     * 支付方式 (Payment Method)
     */
    private String paymentMethod;

    /**
     * 订单状态 (Order Status)
     */
    private String status;

    /**
     * 创建时间 (Created Time)
     */
    private Date createdAt;

    /**
     * 更新时间 (Updated Time)
     */
    private Date updatedAt;

    /**
     * 订单明细列表 (Order Item List) - One-to-Many Relationship
     */
    private List<OrderItem> orderItems;
}
