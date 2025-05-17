package com.smartmall.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单详情表 (Order Item Table)实体类
 *
 * @author ccut_zzy
 * @date 2025-05-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem implements Serializable {

    /**
     * 订单明细ID (Order Item ID, UUID)
     */
    private String orderItemId;

    /**
     * 订单ID (Order ID)
     */
    private String orderId;

    /**
     * 商品ID (Product ID)
     */
    private String productId;

    /**
     * 购买数量 (Quantity)
     */
    private Integer quantity;

    /**
     * 商品单价 (Unit Price)
     */
    private BigDecimal unitPrice;

    /**
     * 创建时间 (Created Time)
     */
    private Date createdAt;

    /**
     * 更新时间 (Updated Time)
     */
    private Date updatedAt;
}