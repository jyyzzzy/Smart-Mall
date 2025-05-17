package com.smartmall.controller;

import com.smartmall.domain.Result; // Assuming Result class exists
import com.smartmall.domain.Customer;
import com.smartmall.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 获取所有顾客列表
     */
    @GetMapping("/all")
    public Result getAllCustomers() {
        List<Customer> customerList = customerService.findAll();
        return Result.success(customerList);
    }

    /**
     * 根据ID获取顾客信息
     */
    @GetMapping("/{customerId}")
    public Result getCustomerById(@PathVariable String customerId) {
        Customer customer = customerService.selectCustomerById(customerId);
        if (customer != null) {
            return Result.success(customer);
        } else {
            // Assuming Result class has an error method with message
            return Result.error("Customer not found with ID: " + customerId);
        }
    }

    /**
     * 根据用户ID获取顾客列表 (expecting 0 or 1)
     */
    @GetMapping("/user/{userId}")
    public Result getCustomerByUserId(@PathVariable String userId) {
        List<Customer> customerList = customerService.selectCustomerByUserId(userId);
        if (customerList != null && !customerList.isEmpty()) {
            // Returning the first one if multiple exist, or adjust business logic
            return Result.success(customerList.getFirst());
        } else {
            return Result.error("Customer profile not found for User ID: " + userId);
        }
    }


    /**
     * 查询所有顾客列表 (前端负责过滤)
     */
    @GetMapping("/list") // Changed to GET as no filtering body is expected here
    public Result getCustomerList() {
        List<Customer> customerList = customerService.selectCustomerList();
        return Result.success(customerList);
    }


    /**
     * 创建新顾客
     */
    @PostMapping
    public Result createCustomer(@RequestBody Customer customer) {
        // Basic validation: Ensure userId is provided if required business logic
        if (customer.getUserId() == null || customer.getUserId().isEmpty()) {
            return Result.error("User ID is required for creating a customer.");
        }
        // Optional: Add check if a customer profile already exists for this userId in Service
        // If service returns 0 or throws exception on duplicate user id...
        int result = customerService.insertCustomer(customer);
        if (result > 0) {
            return Result.success();
        } else {
            // Assuming Result class has an error method
            // This might also catch the case where insertCustomer returns 0 due to validation in Service
            return Result.error("Failed to create customer. User may already have a customer profile.");
        }
    }

    /**
     * 更新顾客信息
     */
    @PutMapping
    public Result updateCustomer(@RequestBody Customer customer) {
        if (customer.getCustomerId() == null || customer.getCustomerId().isEmpty()) {
            // Assuming Result class has an error method
            return Result.error("Customer ID is required for update.");
        }
        int result = customerService.updateCustomer(customer);
        if (result > 0) {
            return Result.success();
        } else {
            // Assuming Result class has an error method
            return Result.error("Failed to update customer or customer not found.");
        }
    }

    /**
     * 根据ID删除顾客
     */
    @DeleteMapping("/{customerId}")
    public Result deleteCustomerById(@PathVariable String customerId) {
        int result = customerService.deleteCustomerById(customerId);
        if (result > 0) {
            return Result.success();
        } else {
            // Assuming Result class has an error method
            return Result.error("Failed to delete customer or customer not found.");
        }
    }

    // Batch delete endpoint removed
    /*
     @DeleteMapping("/batch")
    public Result deleteCustomerByIds(@RequestBody String[] customerIds) {
         // Logic would be in Service or Controller to loop and call deleteById
         return Result.error("Batch delete not implemented in Mapper."); // Example response
    }
    */
}