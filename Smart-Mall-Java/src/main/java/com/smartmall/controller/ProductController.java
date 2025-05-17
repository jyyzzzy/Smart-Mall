package com.smartmall.controller;

import com.smartmall.domain.Result; // Assuming Result class exists
import com.smartmall.domain.Product;
import com.smartmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 获取所有商品列表
     */
    @GetMapping("/all")
    public Result getAllProducts() {
        List<Product> productList = productService.findAll();
        return Result.success(productList);
    }

    /**
     * 根据ID获取商品信息
     */
    @GetMapping("/{productId}")
    public Result getProductById(@PathVariable String productId) {
        Product product = productService.selectProductById(productId);
        if (product != null) {
            return Result.success(product);
        } else {
            // Assuming Result class has an error method with message
            return Result.error("Product not found with ID: " + productId);
        }
    }

    /**
     * 根据商家ID获取商品列表
     */
    @GetMapping("/merchant/{merchantId}")
    public Result getProductsByMerchantId(@PathVariable String merchantId) {
        List<Product> productList = productService.selectProductsByMerchantId(merchantId);
        return Result.success(productList);
    }

    /**
     * 根据分类ID获取商品列表
     */
    @GetMapping("/category/{categoryId}")
    public Result getProductsByCategoryId(@PathVariable String categoryId) {
        List<Product> productList = productService.selectProductsByCategoryId(categoryId);
        return Result.success(productList);
    }

    /**
     * 查询所有商品列表 (前端负责过滤)
     */
    @GetMapping("/list") // Changed to GET as no filtering body is expected here
    public Result getProductList() {
        List<Product> productList = productService.selectProductList();
        return Result.success(productList);
    }


    /**
     * 创建新商品
     */
    @PostMapping
    public Result createProduct(@RequestBody Product product) {
        // Basic validation: Ensure merchantId is provided if required business logic
        if (product.getMerchantId() == null || product.getMerchantId().isEmpty()) {
            return Result.error("Merchant ID is required for creating a product.");
        }
        // Optional: Add business logic validation in Service, e.g., check if merchantId exists

        int result = productService.insertProduct(product);
        if (result > 0) {
            return Result.success();
        } else {
            // Assuming Result class has an error method
            return Result.error("Failed to create product.");
        }
    }

    /**
     * 更新商品信息
     */
    @PutMapping
    public Result updateProduct(@RequestBody Product product) {
        if (product.getProductId() == null || product.getProductId().isEmpty()) {
            // Assuming Result class has an error method
            return Result.error("Product ID is required for update.");
        }
        // Optional: Add business logic validation in Service, e.g., check if merchantId or categoryId exists
        int result = productService.updateProduct(product);
        if (result > 0) {
            return Result.success();
        } else {
            // Assuming Result class has an error method
            return Result.error("Failed to update product or product not found.");
        }
    }

    /**
     * 根据ID删除商品
     */
    @DeleteMapping("/{productId}")
    public Result deleteProductById(@PathVariable String productId) {
        int result = productService.deleteProductById(productId);
        if (result > 0) {
            return Result.success();
        } else {
            // Assuming Result class has an error method
            return Result.error("Failed to delete product or product not found.");
        }
    }

    // Batch delete endpoint removed
    /*
     @DeleteMapping("/batch")
    public Result deleteProductByIds(@RequestBody String[] productIds) {
         // Logic would be in Service or Controller to loop and call deleteById
         return Result.error("Batch delete not implemented in Mapper."); // Example response
    }
    */
}