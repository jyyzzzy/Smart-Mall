package com.smartmall.mapper;

import com.smartmall.domain.Merchant;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 商家表 (Merchant Table)Mapper接口
 *
 * @author ccut_zzy
 * @date 2025-05-14
 */
@Mapper
public interface MerchantMapper {

    /**
     * 查询所有商家 (Merchant Table)
     *
     * @return 商家 (Merchant Table)集合
     */
    @Select("select merchant_id, user_id, mall_id, merchant_name, contact_phone, contact_email, address from merchants")
    List<Merchant> findAll();

    /**
     * 根据ID查询商家 (Merchant Table)
     *
     * @param merchantId 商家ID (Merchant ID)
     * @return 商家 (Merchant Table)
     */
    @Select("select merchant_id, user_id, mall_id, merchant_name, contact_phone, contact_email, address from merchants where merchant_id = #{merchantId}")
    Merchant selectMerchantById(String merchantId);

    /**
     * 根据用户ID查询商家 (Merchant Table)列表
     *
     * @param userId 用户ID (User ID)
     * @return 商家 (Merchant Table)集合
     */
    @Select("select merchant_id, user_id, mall_id, merchant_name, contact_phone, contact_email, address from merchants where user_id = #{userId}")
    List<Merchant> selectMerchantsByUserId(String userId);

    /**
     * 根据商场ID查询商家 (Merchant Table)列表
     *
     * @param mallId 所属商场ID (Mall ID)
     * @return 商家 (Merchant Table)集合
     */
    @Select("select merchant_id, user_id, mall_id, merchant_name, contact_phone, contact_email, address from merchants where mall_id = #{mallId}")
    List<Merchant> selectMerchantsByMallId(String mallId);

    /**
     * 查询所有商家 (Merchant Table)列表 (前端负责过滤)
     *
     * @return 商家 (Merchant Table)集合
     */
    @Select("select merchant_id, user_id, mall_id, merchant_name, contact_phone, contact_email, address from merchants")
    List<Merchant> selectMerchantList();


    /**
     * 新增商家 (Merchant Table)
     *
     * @param merchant 商家 (Merchant Table)实体
     * @return 结果 (影响的行数)
     */
    @Insert("""
            INSERT INTO merchants (merchant_id, user_id, mall_id, merchant_name, contact_phone, contact_email, address)
            VALUES (UUID(), #{userId}, #{mallId}, #{merchantName}, #{contactPhone}, #{contactEmail}, #{address});
            """)
    int insertMerchant(Merchant merchant);

    /**
     * 修改商家 (Merchant Table)
     *
     * @param merchant 商家 (Merchant Table)实体
     * @return 结果 (影响的行数)
     */
    @Update("""
            UPDATE merchants
            SET user_id = #{userId}, mall_id = #{mallId}, merchant_name = #{merchantName},
                contact_phone = #{contactPhone}, contact_email = #{contactEmail}, address = #{address}
            WHERE merchant_id = #{merchantId};
            """)
    int updateMerchant(Merchant merchant);

    /**
     * 删除商家 (Merchant Table)
     *
     * @param merchantId 商家ID (Merchant ID)
     * @return 结果 (影响的行数)
     */
    @Delete("DELETE FROM merchants where merchant_id = #{merchantId}")
    int deleteMerchantById(String merchantId);

    /**
     * 根据商场ID统计商家数量
     *
     * @param mallId 所属商场ID (Mall ID)
     * @return 商家数量
     */
    @Select("select count(*) from merchants where mall_id = #{mallId}")
    int countMerchantsByMallId(@Param("mallId") String mallId);

    // Batch delete method removed as requested to remove <script>
    // public int deleteMerchantByIds(String[] merchantIds);
}