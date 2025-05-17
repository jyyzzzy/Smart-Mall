package com.smartmall.mapper;

import com.smartmall.domain.OrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 订单详情表 (Order Item Table)Mapper接口
 *
 * @author ccut_zzy
 * @date 2025-05-14
 */
@Mapper
public interface OrderItemMapper {

    /**
     * 根据订单ID查询订单明细 (Order Item Table)列表
     *
     * @param orderId 订单ID (Order ID)
     * @return 订单明细 (Order Item Table)集合
     */
    @Select("select order_item_id, order_id, product_id, quantity, unit_price, created_at, updated_at from order_items where order_id = #{orderId}")
    List<OrderItem> selectOrderItemsByOrderId(String orderId);

    /**
     * 根据ID查询订单明细 (Order Item Table)
     *
     * @param orderItemId 订单明细ID (Order Item ID)
     * @return 订单明细 (Order Item Table)
     */
    @Select("select order_item_id, order_id, product_id, quantity, unit_price, created_at, updated_at from order_items where order_item_id = #{orderItemId}")
    OrderItem selectOrderItemById(String orderItemId);


    /**
     * 新增订单明细 (Order Item Table)
     *
     * @param orderItem 订单明细 (Order Item Table)实体
     * @return 结果 (影响的行数)
     */
    @Insert("""
            INSERT INTO order_items (order_item_id, order_id, product_id, quantity, unit_price, created_at, updated_at)
            VALUES (#{orderItemId}, #{orderId}, #{productId}, #{quantity}, #{unitPrice}, #{createdAt}, #{updatedAt});
            """)
    int insertOrderItem(OrderItem orderItem);

    // Batch insert method for OrderItems would typically use <script><foreach>

    /**
     * 删除订单明细 (Order Item Table)
     *
     * @param orderItemId 订单明细ID (Order Item ID)
     * @return 结果 (影响的行数)
     */
    @Delete("DELETE FROM order_items where order_item_id = #{orderItemId}")
    int deleteOrderItemById(String orderItemId);


    /**
     * 根据订单ID删除所有相关订单明细 (Order Item Table)
     *
     * @param orderId 订单ID (Order ID)
     * @return 结果 (影响的行数)
     */
    @Delete("DELETE FROM order_items where order_id = #{orderId}")
    int deleteOrderItemsByOrderId(String orderId);

    // Batch delete by item IDs would typically use <script><foreach>
    // public int deleteOrderItemByIds(String[] orderItemIds);
}