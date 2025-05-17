package com.smartmall.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // For transaction management

import com.smartmall.mapper.OrderMapper;
import com.smartmall.mapper.OrderItemMapper;
import com.smartmall.domain.Order;
import com.smartmall.domain.OrderItem;
import com.smartmall.service.OrderService;

/**
 * 订单业务Service业务层处理
 *
 * @author ccut_zzy
 * @date 2025-05-14
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    /**
     * 创建新订单 (包含订单主信息及订单明细)
     */
    @Override
    @Transactional // Ensure atomicity: either order and all items are created, or none are.
    public int createOrder(Order order) {
        // 1. Validate input and set defaults/UUIDs
        if (order == null || order.getOrderItems() == null || order.getOrderItems().isEmpty()) {
            // Handle error: Order and items must be provided
            return 0; // Or throw exception
        }
        if (order.getOrderId() == null || order.getOrderId().isEmpty()) {
            order.setOrderId(UUID.randomUUID().toString());
        }
        if (order.getCreatedAt() == null) {
            order.setCreatedAt(new Date());
        }
        if (order.getUpdatedAt() == null) {
            order.setUpdatedAt(new Date());
        }
        if (order.getStatus() == null || order.getStatus().isEmpty()) {
            order.setStatus("PENDING"); // Default status
        }

        // Calculate total amount from items (business logic)
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderItem item : order.getOrderItems()) {
            if (item.getUnitPrice() != null && item.getQuantity() != null && item.getQuantity() > 0) {
                BigDecimal itemTotal = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                totalAmount = totalAmount.add(itemTotal);
            } else {
                // Handle error: Invalid item data
                throw new IllegalArgumentException("Invalid order item data.");
            }
            // Set UUID and orderId for each item
            if (item.getOrderItemId() == null || item.getOrderItemId().isEmpty()) {
                item.setOrderItemId(UUID.randomUUID().toString());
            }
            item.setOrderId(order.getOrderId());
            if (item.getCreatedAt() == null) {
                item.setCreatedAt(new Date());
            }
            if (item.getUpdatedAt() == null) {
                item.setUpdatedAt(new Date());
            }
        }
        order.setTotalAmount(totalAmount); // Set calculated total amount

        // 2. Insert Order
        int orderResult = orderMapper.insertOrder(order);

        if (orderResult <= 0) {
            // Failed to insert order header
            throw new RuntimeException("Failed to insert order."); // Rollback transaction
        }

        // 3. Insert Order Items
        int itemResults = 0;
        for (OrderItem item : order.getOrderItems()) {
            int itemResult = orderItemMapper.insertOrderItem(item);
            if (itemResult <= 0) {
                // Failed to insert an item - rollback the whole transaction
                throw new RuntimeException("Failed to insert order item.");
            }
            itemResults += itemResult;
        }

        // Return total inserted count (order + items) or just order count
        return orderResult; // Indicate success of order header insert
    }

    /**
     * 根据ID查询订单 (包含订单主信息及订单明细)
     */
    @Override
    public Order getOrderById(String orderId) {
        // 1. Fetch the main order record
        Order order = orderMapper.selectOrderById(orderId);

        if (order != null) {
            // 2. Fetch associated order items
            List<OrderItem> orderItems = orderItemMapper.selectOrderItemsByOrderId(orderId);
            order.setOrderItems(orderItems); // Attach items to the order object
        }

        return order;
    }

    /**
     * 根据顾客ID查询订单列表 (通常不包含详细明细，按需实现)
     */
    @Override
    public List<Order> getOrdersByCustomerId(String customerId) {
        // Note: This fetches orders, but not their items. Modify if items are needed here.
        return orderMapper.selectOrdersByCustomerId(customerId);
    }

    /**
     * 根据商家ID查询订单列表 (通常不包含详细明细，按需实现)
     */
    @Override
    public List<Order> getOrdersByMerchantId(String merchantId) {
        // Note: This fetches orders, but not their items. Modify if items are needed here.
        return orderMapper.selectOrdersByMerchantId(merchantId);
    }

    /**
     * 查询所有订单列表 (通常不包含详细明细，按需实现)
     */
    @Override
    public List<Order> findAllOrders() {
        // Note: This fetches orders, but not their items. Modify if items are needed here.
        return orderMapper.findAll();
    }

    /**
     * 更新订单状态
     */
    @Override
    public int updateOrderStatus(String orderId, String status) {
        Order order = new Order();
        order.setOrderId(orderId);
        order.setStatus(status);
        order.setUpdatedAt(new Date()); // Update timestamp

        return orderMapper.updateOrderStatus(order);
    }

    /**
     * 删除订单 (同时删除关联的订单明细)
     */
    @Override
    @Transactional // Ensure atomicity: either order and all items are deleted, or none are.
    public int deleteOrder(String orderId) {
        // 1. Delete associated order items first (due to foreign key constraints)
        int itemDeleteCount = orderItemMapper.deleteOrderItemsByOrderId(orderId);
        // Consider checking itemDeleteCount if necessary, but FK cascade delete might handle this
        // depending on database schema definition. Explicit deletion here is safer.

        // 2. Delete the main order record
        int orderDeleteCount = orderMapper.deleteOrderById(orderId);

        // Return count of deleted orders (should be 1 if successful)
        return orderDeleteCount;
    }

    // Methods for updating/deleting individual order items could be implemented here
    // e.g., @Override public int deleteOrderItem(String orderItemId) { return orderItemMapper.deleteOrderItemById(orderItemId); }
    // e.g., @Override public int updateOrderItemQuantity(String orderItemId, int quantity) { ... logic ... }
}