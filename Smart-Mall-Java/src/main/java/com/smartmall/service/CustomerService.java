package com.smartmall.service;

import com.smartmall.domain.Customer;

import java.util.List;

/**
 * 顾客表 (Customer Table)Service接口
 *
 * @author ccut_zzy
 * @date 2025-05-14
 */
public interface CustomerService {

    /**
     * 查询所有顾客 (Customer Table)
     *
     * @return 顾客 (Customer Table)集合
     */
    List<Customer> findAll();

    /**
     * 根据ID查询顾客 (Customer Table)
     *
     * @param customerId 顾客ID (Customer ID)
     * @return 顾客 (Customer Table)
     */
    Customer selectCustomerById(String customerId);

    /**
     * 根据用户ID查询顾客 (Customer Table)列表
     *
     * @param userId 顾客用户ID (User ID)
     * @return 顾客 (Customer Table)集合 (could be 0 or 1)
     */
    List<Customer> selectCustomerByUserId(String userId);

    /**
     * 查询所有顾客 (Customer Table)列表 (前端负责过滤)
     *
     * @return 顾客 (Customer Table)集合
     */
    List<Customer> selectCustomerList();

    /**
     * 新增顾客 (Customer Table)
     *
     * @param customer 顾客 (Customer Table)实体
     * @return 结果 (影响的行数)
     */
    int insertCustomer(Customer customer);

    /**
     * 修改顾客 (Customer Table)
     *
     * @param customer 顾客 (Customer Table)实体
     * @return 结果 (影响的行数)
     */
    int updateCustomer(Customer customer);

    /**
     * 删除顾客 (Customer Table)
     *
     * @param customerId 顾客ID (Customer ID)
     * @return 结果 (影响的行数)
     */
    int deleteCustomerById(String customerId);

    // Batch delete method removed
    // int deleteCustomerByIds(String[] customerIds);
}