package com.smartmall.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品表 (Product Table)实体类
 *
 * @author ccut_zzy
 * @date 2025-05-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    /**
     * 商品ID (Product ID, UUID)
     */
    private String productId;

    /**
     * 商家ID (Merchant ID)
     */
    private String merchantId;

    /**
     * 商品名称 (Product Name)
     */
    private String productName;

    /**
     * 商品价格 (Price)
     */
    private Double price;

    /**
     * 库存 (Stock)
     */
    private Integer stock;

    /**
     * 商品分类ID (Category)
     */
    private String categoryId;

    /**
     * 商品描述 (Product Description)
     */
    private String description;

    /**
     * 存貨單位
     */
    private String sku;

}
