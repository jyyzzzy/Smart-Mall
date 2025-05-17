package com.smartmall.mapper;

import com.smartmall.domain.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 商品表 (Product Table)Mapper接口
 *
 * @author ccut_zzy
 * @date 2025-05-14
 */
@Mapper
public interface ProductMapper {

    /**
     * 查询所有商品 (Product Table)
     *
     * @return 商品 (Product Table)集合
     */
    @Select("select product_id, merchant_id, product_name, price, stock, category_id, description, sku from products")
    List<Product> findAll();

    /**
     * 根据ID查询商品 (Product Table)
     *
     * @param productId 商品ID (Product ID)
     * @return 商品 (Product Table)
     */
    @Select("select product_id, merchant_id, product_name, price, stock, category_id, description, sku from products where product_id = #{productId}")
    Product selectProductById(String productId);

    /**
     * 根据商家ID查询商品 (Product Table)列表
     *
     * @param merchantId 商家ID (Merchant ID)
     * @return 商品 (Product Table)集合
     */
    @Select("select product_id, merchant_id, product_name, price, stock, category_id, description, sku from products where merchant_id = #{merchantId}")
    List<Product> selectProductsByMerchantId(String merchantId);

    /**
     * 根据分类ID查询商品 (Product Table)列表
     *
     * @param categoryId 商品分类ID (Category)
     * @return 商品 (Product Table)集合
     */
    @Select("select product_id, merchant_id, product_name, price, stock, category_id, description, sku from products where category_id = #{categoryId}")
    List<Product> selectProductsByCategoryId(String categoryId);

    /**
     * 查询所有商品 (Product Table)列表 (前端负责过滤)
     *
     * @return 商品 (Product Table)集合
     */
    @Select("select product_id, merchant_id, product_name, price, stock, category_id, description, sku from products")
    List<Product> selectProductList();


    /**
     * 新增商品 (Product Table)
     *
     * @param product 商品 (Product Table)实体
     * @return 结果 (影响的行数)
     */
    @Insert("""
            INSERT INTO products (product_id, merchant_id, product_name, price, stock, category_id, description, sku)
            VALUES (#{productId}, #{merchantId}, #{productName}, #{price}, #{stock}, #{categoryId}, #{description}, #{sku});
            """)
    int insertProduct(Product product);

    /**
     * 修改商品 (Product Table)
     *
     * @param product 商品 (Product Table)实体
     * @return 结果 (影响的行数)
     */
    @Update("""
            UPDATE products
            SET merchant_id = #{merchantId}, product_name = #{productName}, price = #{price}, stock = #{stock},
                category_id = #{categoryId}, description = #{description}, sku = #{sku}
            WHERE product_id = #{productId};
            """)
    int updateProduct(Product product);

    /**
     * 删除商品 (Product Table)
     *
     * @param productId 商品ID (Product ID)
     * @return 结果 (影响的行数)
     */
    @Delete("DELETE FROM products where product_id = #{productId}")
    int deleteProductById(String productId);

    /**
     * 根据商场ID统计商品数量
     * <p>
     * Note: This assumes a merchant belongs to only one mall. If a product can be associated
     * directly with a mall, or if merchants can be in multiple malls, this query needs adjustment
     * to join with the 'merchants' table. Based on your schema `products` links to `merchants`,
     * and `merchants` links to `malls`, so a join is appropriate.
     * Let's join with merchants table to get products count per mall.
     *
     * @param mallId 所属商场ID (Mall ID)
     * @return 商品数量
     */
    @Select("select count(p.product_id) from products p JOIN merchants m ON p.merchant_id = m.merchant_id where m.mall_id = #{mallId}")
    int countProductsByMallId(@Param("mallId") String mallId);

    // Batch delete method removed as requested to remove <script>
    // public int deleteProductByIds(String[] productIds);
}