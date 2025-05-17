package com.smartmall.service;

import com.smartmall.domain.Order;
import com.smartmall.domain.OrderItem;

import java.util.List;

/**
 * 订单业务Service接口
 *
 * @author ccut_zzy
 * @date 2025-05-14
 */
public interface OrderService {

    /**
     * 创建新订单 (包含订单主信息及订单明细)
     *
     * @param order 订单实体，需包含订单明细列表
     * @return 结果 (影响的行数，通常是订单主表行数)
     */
    int createOrder(Order order);

    /**
     * 根据ID查询订单 (包含订单主信息及订单明细)
     *
     * @param orderId 订单ID
     * @return 订单实体 (包含订单明细列表)，或null
     */
    Order getOrderById(String orderId);

    /**
     * 根据顾客ID查询订单列表 (通常不包含详细明细，按需实现)
     *
     * @param customerId 顾客ID
     * @return 订单列表
     */
    List<Order> getOrdersByCustomerId(String customerId);

    /**
     * 根据商家ID查询订单列表 (通常不包含详细明细，按需实现)
     *
     * @param merchantId 商家ID
     * @return 订单列表
     */
    List<Order> getOrdersByMerchantId(String merchantId);

    /**
     * 查询所有订单列表 (通常不包含详细明细，按需实现)
     *
     * @return 订单列表
     */
    List<Order> findAllOrders();


    /**
     * 更新订单状态
     *
     * @param orderId 订单ID
     * @param status  新的订单状态
     * @return 结果 (影响的行数)
     */
    int updateOrderStatus(String orderId, String status);

    /**
     * 删除订单 (同时删除关联的订单明细)
     *
     * @param orderId 订单ID
     * @return 结果 (影响的行数，通常是订单主表行数)
     */
    int deleteOrder(String orderId);

    // Methods for updating/deleting individual order items could be added here if needed
    // e.g., int deleteOrderItem(String orderItemId);
    // e.g., int updateOrderItemQuantity(String orderItemId, int quantity);
}