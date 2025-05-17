package com.smartmall.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smartmall.mapper.CustomerMapper;
import com.smartmall.domain.Customer;
import com.smartmall.service.CustomerService;

/**
 * 顾客表 (Customer Table)Service业务层处理
 *
 * @author ccut_zzy
 * @date 2025-05-14
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 查询所有顾客 (Customer Table)
     */
    @Override
    public List<Customer> findAll() {
        return customerMapper.findAll();
    }

    /**
     * 根据ID查询顾客 (Customer Table)
     */
    @Override
    public Customer selectCustomerById(String customerId) {
        return customerMapper.selectCustomerById(customerId);
    }

    /**
     * 根据用户ID查询顾客 (Customer Table)列表
     */
    @Override
    public List<Customer> selectCustomerByUserId(String userId) {
        return customerMapper.selectCustomerByUserId(userId);
    }

    /**
     * 查询所有顾客 (Customer Table)列表 (前端负责过滤)
     */
    @Override
    public List<Customer> selectCustomerList() {
        return customerMapper.selectCustomerList();
    }

    /**
     * 新增顾客 (Customer Table)
     */
    @Override
    public int insertCustomer(Customer customer) {
        // Generate UUID for customerId if not provided
        if (customer.getCustomerId() == null || customer.getCustomerId().isEmpty()) {
            customer.setCustomerId(UUID.randomUUID().toString());
        }
        // Optional: Add check if a customer profile already exists for this userId
        // List<Customer> existingCustomers = customerMapper.selectCustomerByUserId(customer.getUserId());
        // if (!existingCustomers.isEmpty()) {
        //     // Handle error: User already has a customer profile
        //     return 0; // Or throw a custom exception
        // }
        return customerMapper.insertCustomer(customer);
    }

    /**
     * 修改顾客 (Customer Table)
     */
    @Override
    public int updateCustomer(Customer customer) {
        return customerMapper.updateCustomer(customer);
    }

    /**
     * 删除顾客 (Customer Table)
     */
    @Override
    public int deleteCustomerById(String customerId) {
        return customerMapper.deleteCustomerById(customerId);
    }

    // Batch delete method removed
    /*@Override
    public int deleteCustomerByIds(String[] customerIds)
    {
        // Implementation would involve looping through customerIds and calling deleteCustomerById
        // or using a different batching strategy outside the simple mapper annotation
        return 0; // Placeholder
    }*/
}