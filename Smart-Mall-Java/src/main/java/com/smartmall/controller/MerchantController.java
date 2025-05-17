package com.smartmall.controller;

import com.smartmall.domain.Result; // Assuming Result class exists
import com.smartmall.domain.Merchant;
import com.smartmall.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/merchant")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    /**
     * 获取所有商家列表
     */
    @GetMapping("/all")
    public Result getAllMerchants() {
        List<Merchant> merchantList = merchantService.findAll();
        return Result.success(merchantList);
    }

    /**
     * 根据ID获取商家信息
     */
    @GetMapping("/{merchantId}")
    public Result getMerchantById(@PathVariable String merchantId) {
        Merchant merchant = merchantService.selectMerchantById(merchantId);
        if (merchant != null) {
            return Result.success(merchant);
        } else {
            // Assuming Result class has an error method with message
            return Result.error("Merchant not found with ID: " + merchantId);
        }
    }

    /**
     * 根据用户ID获取商家列表
     */
    @GetMapping("/user/{userId}")
    public Result getMerchantsByUserId(@PathVariable String userId) {
        List<Merchant> merchantList = merchantService.selectMerchantsByUserId(userId);
        return Result.success(merchantList);
    }

    /**
     * 根据商场ID获取商家列表
     */
    @GetMapping("/mall/{mallId}")
    public Result getMerchantsByMallId(@PathVariable String mallId) {
        List<Merchant> merchantList = merchantService.selectMerchantsByMallId(mallId);
        return Result.success(merchantList);
    }


    /**
     * 查询所有商家列表 (前端负责过滤)
     */
    @GetMapping("/list") // Changed to GET as no filtering body is expected here
    public Result getMerchantList() {
        List<Merchant> merchantList = merchantService.selectMerchantList();
        return Result.success(merchantList);
    }


    /**
     * 创建新商家
     */
    @PostMapping
    public Result createMerchant(@RequestBody Merchant merchant) {
        // Basic validation: Ensure mallId and userId are provided if required business logic
        if (merchant.getMallId() == null || merchant.getMallId().isEmpty()) {
            return Result.error("Mall ID is required for creating a merchant.");
        }
        if (merchant.getUserId() == null || merchant.getUserId().isEmpty()) {
            return Result.error("User ID is required for creating a merchant.");
        }

        int result = merchantService.insertMerchant(merchant);
        if (result > 0) {
            return Result.success();
        } else {
            // Assuming Result class has an error method
            return Result.error("Failed to create merchant.");
        }
    }

    /**
     * 更新商家信息
     */
    @PutMapping
    public Result updateMerchant(@RequestBody Merchant merchant) {
        if (merchant.getMerchantId() == null || merchant.getMerchantId().isEmpty()) {
            // Assuming Result class has an error method
            return Result.error("Merchant ID is required for update.");
        }
        int result = merchantService.updateMerchant(merchant);
        if (result > 0) {
            return Result.success();
        } else {
            // Assuming Result class has an error method
            return Result.error("Failed to update merchant or merchant not found.");
        }
    }

    /**
     * 根据ID删除商家
     */
    @DeleteMapping("/{merchantId}")
    public Result deleteMerchantById(@PathVariable String merchantId) {
        int result = merchantService.deleteMerchantById(merchantId);
        if (result > 0) {
            return Result.success();
        } else {
            // Assuming Result class has an error method
            return Result.error("Failed to delete merchant or merchant not found.");
        }
    }

    // Batch delete endpoint removed
     /*
     @DeleteMapping("/batch")
    public Result deleteMerchantByIds(@RequestBody String[] merchantIds) {
         // Logic would be in Service or Controller to loop and call deleteById
         return Result.error("Batch delete not implemented in Mapper."); // Example response
    }
    */
}