package com.smartmall.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smartmall.mapper.ProductMapper;
import com.smartmall.domain.Product;
import com.smartmall.service.ProductService;

/**
 * 商品表 (Product Table)Service业务层处理
 *
 * @author ccut_zzy
 * @date 2025-05-14
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    /**
     * 查询所有商品 (Product Table)
     */
    @Override
    public List<Product> findAll() {
        return productMapper.findAll();
    }

    /**
     * 根据ID查询商品 (Product Table)
     */
    @Override
    public Product selectProductById(String productId) {
        return productMapper.selectProductById(productId);
    }

    /**
     * 根据商家ID查询商品 (Product Table)列表
     */
    @Override
    public List<Product> selectProductsByMerchantId(String merchantId) {
        return productMapper.selectProductsByMerchantId(merchantId);
    }

    /**
     * 根据分类ID查询商品 (Product Table)列表
     */
    @Override
    public List<Product> selectProductsByCategoryId(String categoryId) {
        return productMapper.selectProductsByCategoryId(categoryId);
    }

    /**
     * 查询所有商品 (Product Table)列表 (前端负责过滤)
     */
    @Override
    public List<Product> selectProductList() {
        return productMapper.selectProductList();
    }

    /**
     * 新增商品 (Product Table)
     */
    @Override
    public int insertProduct(Product product) {
        // Generate UUID for productId if not provided
        if (product.getProductId() == null || product.getProductId().isEmpty()) {
            product.setProductId(UUID.randomUUID().toString());
        }
        // Optional: Add business logic validation, e.g., check if merchantId exists
        return productMapper.insertProduct(product);
    }

    /**
     * 修改商品 (Product Table)
     */
    @Override
    public int updateProduct(Product product) {
        // Optional: Add business logic validation, e.g., check if merchantId or categoryId exists
        return productMapper.updateProduct(product);
    }

    /**
     * 删除商品 (Product Table)
     */
    @Override
    public int deleteProductById(String productId) {
        return productMapper.deleteProductById(productId);
    }

    // Batch delete method removed
    /*@Override
    public int deleteProductByIds(String[] productIds)
    {
         // Implementation would involve looping through productIds and calling deleteProductById
        // or using a different batching strategy outside the simple mapper annotation
        return 0; // Placeholder
    }*/
}