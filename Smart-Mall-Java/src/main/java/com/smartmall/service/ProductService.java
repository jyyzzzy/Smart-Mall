package com.smartmall.service;

import com.smartmall.domain.Product;

import java.util.List;

/**
 * 商品表 (Product Table)Service接口
 *
 * @author ccut_zzy
 * @date 2025-05-14
 */
public interface ProductService {

    /**
     * 查询所有商品 (Product Table)
     *
     * @return 商品 (Product Table)集合
     */
    List<Product> findAll();

    /**
     * 根据ID查询商品 (Product Table)
     *
     * @param productId 商品ID (Product ID)
     * @return 商品 (Product Table)
     */
    Product selectProductById(String productId);

    /**
     * 根据商家ID查询商品 (Product Table)列表
     *
     * @param merchantId 商家ID (Merchant ID)
     * @return 商品 (Product Table)集合
     */
    List<Product> selectProductsByMerchantId(String merchantId);

    /**
     * 根据分类ID查询商品 (Product Table)列表
     *
     * @param categoryId 商品分类ID (Category)
     * @return 商品 (Product Table)集合
     */
    List<Product> selectProductsByCategoryId(String categoryId);

    /**
     * 查询所有商品 (Product Table)列表 (前端负责过滤)
     *
     * @return 商品 (Product Table)集合
     */
    List<Product> selectProductList();

    /**
     * 新增商品 (Product Table)
     *
     * @param product 商品 (Product Table)实体
     * @return 结果 (影响的行数)
     */
    int insertProduct(Product product);

    /**
     * 修改商品 (Product Table)
     *
     * @param product 商品 (Product Table)实体
     * @return 结果 (影响的行数)
     */
    int updateProduct(Product product);

    /**
     * 删除商品 (Product Table)
     *
     * @param productId 商品ID (Product ID)
     * @return 结果 (影响的行数)
     */
    int deleteProductById(String productId);

    // Batch delete method removed
    // int deleteProductByIds(String[] productIds);
}