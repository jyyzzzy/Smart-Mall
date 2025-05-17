package com.smartmall.controller;

import com.smartmall.domain.Result; // Assuming Result class exists
import com.smartmall.domain.Order;
import com.smartmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 创建新订单 (包含订单主信息及订单明细)
     */
    @PostMapping
    public Result createOrder(@RequestBody Order order) {
        // Basic validation
        if (order == null || order.getCustomerId() == null || order.getCustomerId().isEmpty() ||
                order.getMerchantId() == null || order.getMerchantId().isEmpty() ||
                order.getPaymentMethod() == null || order.getPaymentMethod().isEmpty() ||
                order.getOrderItems() == null || order.getOrderItems().isEmpty()) {
            return Result.error("Invalid order data provided.");
        }

        try {
            int result = orderService.createOrder(order);
            if (result > 0) {
                return Result.success(order.getOrderId()); // Return created order ID
            } else {
                // Service might return 0 or throw specific exceptions
                return Result.error("Failed to create order.");
            }
        } catch (IllegalArgumentException e) {
            return Result.error("Invalid order item data: " + e.getMessage());
        } catch (RuntimeException e) {
            // Catch exceptions from service layer transactions/failures
            return Result.error("Order creation failed: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取订单信息 (包含订单明细)
     */
    @GetMapping("/{orderId}")
    public Result getOrderById(@PathVariable String orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order != null) {
            return Result.success(order);
        } else {
            return Result.error("Order not found with ID: " + orderId);
        }
    }

    /**
     * 根据顾客ID获取订单列表 (不包含详细明细)
     */
    @GetMapping("/customer/{customerId}")
    public Result getOrdersByCustomerId(@PathVariable String customerId) {
        List<Order> orderList = orderService.getOrdersByCustomerId(customerId);
        return Result.success(orderList);
    }

    /**
     * 根据商家ID获取订单列表 (不包含详细明细)
     */
    @GetMapping("/merchant/{merchantId}")
    public Result getOrdersByMerchantId(@PathVariable String merchantId) {
        List<Order> orderList = orderService.getOrdersByMerchantId(merchantId);
        return Result.success(orderList);
    }

    /**
     * 获取所有订单列表 (不包含详细明细)
     */
    @GetMapping("/all") // Or /list
    public Result getAllOrders() {
        List<Order> orderList = orderService.findAllOrders();
        return Result.success(orderList);
    }


    /**
     * 更新订单状态
     */
    @PutMapping("/{orderId}/status")
    public Result updateOrderStatus(@PathVariable String orderId, @RequestBody String status) {
        if (status == null || status.trim().isEmpty()) {
            return Result.error("Status cannot be empty.");
        }
        int result = orderService.updateOrderStatus(orderId, status.trim());
        if (result > 0) {
            return Result.success();
        } else {
            return Result.error("Failed to update order status or order not found.");
        }
    }


    /**
     * 根据ID删除订单 (同时删除关联明细)
     */
    @DeleteMapping("/{orderId}")
    public Result deleteOrderById(@PathVariable String orderId) {
        try {
            int result = orderService.deleteOrder(orderId);
            if (result > 0) {
                return Result.success();
            } else {
                // This might return 0 if the orderId didn't exist
                return Result.error("Failed to delete order or order not found.");
            }
        } catch (RuntimeException e) {
            // Catch exceptions from service layer transactions/failures
            return Result.error("Order deletion failed: " + e.getMessage());
        }
    }

    // Endpoints for managing individual order items could be added here if needed
}