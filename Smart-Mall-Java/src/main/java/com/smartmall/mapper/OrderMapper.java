package com.smartmall.mapper;

import com.smartmall.domain.Order;
import com.smartmall.domain.OrderItem;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

/**
 * 订单表 (Order Table)Mapper接口
 *
 * @author ccut_zzy
 * @date 2025-05-14
 */
@Mapper
public interface OrderMapper {

    /**
     * 查询所有订单 (Order Table)
     *
     * @return 订单 (Order Table)集合
     */
    @Select("select order_id, customer_id, merchant_id, total_amount, payment_method, status, created_at, updated_at from orders")
    List<Order> findAll();

    /**
     * 根据ID查询订单 (Order Table)
     *
     * @param orderId 订单ID (Order ID)
     * @return 订单 (Order Table)
     */
    @Select("select order_id, customer_id, merchant_id, total_amount, payment_method, status, created_at, updated_at from orders where order_id = #{orderId}")
    Order selectOrderById(String orderId);

    /**
     * 根据顾客ID查询订单 (Order Table)列表
     *
     * @param customerId 顾客ID (Customer ID)
     * @return 订单 (Order Table)集合
     */
    @Select("select order_id, customer_id, merchant_id, total_amount, payment_method, status, created_at, updated_at from orders where customer_id = #{customerId}")
    List<Order> selectOrdersByCustomerId(String customerId);

    /**
     * 根据商家ID查询订单 (Order Table)列表
     *
     * @param merchantId 商家ID (Merchant ID)
     * @return 订单 (Order Table)集合
     */
    @Select("select order_id, customer_id, merchant_id, total_amount, payment_method, status, created_at, updated_at from orders where merchant_id = #{merchantId}")
    List<Order> selectOrdersByMerchantId(String merchantId);

    /**
     * 查询所有订单 (Order Table)列表 (前端负责过滤) - Does NOT fetch items
     *
     * @return 订单 (Order Table)集合
     */
    @Select("select order_id, customer_id, merchant_id, total_amount, payment_method, status, created_at, updated_at from orders")
    List<Order> selectOrderList();


    /**
     * 新增订单 (Order Table)
     *
     * @param order 订单 (Order Table)实体
     * @return 结果 (影响的行数)
     */
    @Insert("""
            INSERT INTO orders (order_id, customer_id, merchant_id, total_amount, payment_method, status, created_at, updated_at)
            VALUES (#{orderId}, #{customerId}, #{merchantId}, #{totalAmount}, #{paymentMethod}, #{status}, #{createdAt}, #{updatedAt});
            """)
    int insertOrder(Order order);

    /**
     * 修改订单状态 (Order Table)
     *
     * @param order 订单 (Order Table)实体 (only status and updated_at are expected to be updated)
     * @return 结果 (影响的行数)
     */
    @Update("""
            UPDATE orders
            SET status = #{status}, updated_at = #{updatedAt}
            WHERE order_id = #{orderId};
            """)
    int updateOrderStatus(Order order); // Specific update for status

    /**
     * 更新订单 (Order Table) - General update, use cautiously
     *
     * @param order 订单 (Order Table)实体
     * @return 结果 (影响的行数)
     */
    @Update("""
            UPDATE orders
            SET customer_id = #{customerId}, merchant_id = #{merchantId}, total_amount = #{totalAmount},
                payment_method = #{paymentMethod}, status = #{status}, updated_at = #{updatedAt}
            WHERE order_id = #{orderId};
            """)
    int updateOrder(Order order);


    /**
     * 删除订单 (Order Table)
     *
     * @param orderId 订单ID (Order ID)
     * @return 结果 (影响的行数)
     */
    @Delete("DELETE FROM orders where order_id = #{orderId}")
    int deleteOrderById(String orderId);

    /**
     * 根据商场ID和时间范围统计订单数量
     * <p>
     * Note: This assumes an order belongs to only one merchant, and that merchant belongs
     * to only one mall. We need to join with the 'merchants' table.
     *
     * @param mallId    所属商场ID (Mall ID)
     * @param startTime 开始时间 (inclusive)
     * @param endTime   结束时间 (exclusive)
     * @return 订单数量
     */
    @Select("""
            SELECT count(o.order_id)
            FROM orders o
            JOIN merchants m ON o.merchant_id = m.merchant_id
            WHERE m.mall_id = #{mallId}
            AND o.created_at >= #{startTime} AND o.created_at < #{endTime}
            """)
    int countOrdersByMallIdAndDateRange(@Param("mallId") String mallId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    // Batch delete method removed as requested
    // public int deleteOrderByIds(String[] orderIds);
}