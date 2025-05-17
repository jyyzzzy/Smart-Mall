package com.smartmall.mapper;

import com.smartmall.domain.Customer;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 顾客表 (Customer Table)Mapper接口
 *
 * @author ccut_zzy
 * @date 2025-05-14
 */
@Mapper
public interface CustomerMapper {

    /**
     * 查询所有顾客 (Customer Table)
     *
     * @return 顾客 (Customer Table)集合
     */
    @Select("select customer_id, user_id, customer_name, contact_phone, contact_email from customers")
    List<Customer> findAll();

    /**
     * 根据ID查询顾客 (Customer Table)
     *
     * @param customerId 顾客ID (Customer ID)
     * @return 顾客 (Customer Table)
     */
    @Select("select customer_id, user_id, customer_name, contact_phone, contact_email from customers where customer_id = #{customerId}")
    Customer selectCustomerById(String customerId);

    /**
     * 根据用户ID查询顾客 (Customer Table)列表
     *
     * @param userId 顾客用户ID (User ID)
     * @return 顾客 (Customer Table)集合 (could be 0 or 1)
     */
    @Select("select customer_id, user_id, customer_name, contact_phone, contact_email from customers where user_id = #{userId}")
    List<Customer> selectCustomerByUserId(String userId);

    /**
     * 查询所有顾客 (Customer Table)列表 (前端负责过滤)
     *
     * @return 顾客 (Customer Table)集合
     */
    @Select("select customer_id, user_id, customer_name, contact_phone, contact_email from customers")
    List<Customer> selectCustomerList();

    /**
     * 新增顾客 (Customer Table)
     *
     * @param customer 顾客 (Customer Table)实体
     * @return 结果 (影响的行数)
     */
    @Insert("""
            INSERT INTO customers (customer_id, user_id, customer_name, contact_phone, contact_email)
            VALUES (UUID(), #{userId}, #{customerName}, #{contactPhone}, #{contactEmail});
            """)
    int insertCustomer(Customer customer);

    /**
     * 修改顾客 (Customer Table)
     *
     * @param customer 顾客 (Customer Table)实体
     * @return 结果 (影响的行数)
     */
    @Update("""
            UPDATE customers
            SET user_id = #{userId}, customer_name = #{customerName}, contact_phone = #{contactPhone}, contact_email = #{contactEmail}
            WHERE customer_id = #{customerId};
            """)
    int updateCustomer(Customer customer);

    /**
     * 删除顾客 (Customer Table)
     *
     * @param customerId 顾客ID (Customer ID)
     * @return 结果 (影响的行数)
     */
    @Delete("DELETE FROM customers where customer_id = #{customerId}")
    int deleteCustomerById(String customerId);

    // Batch delete method removed as requested to remove <script>
    // public int deleteCustomerByIds(String[] customerIds);
}