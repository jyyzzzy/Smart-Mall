package com.smartmall.service;

import com.smartmall.domain.Merchant;

import java.util.List;

/**
 * 商家表 (Merchant Table)Service接口
 *
 * @author ccut_zzy
 * @date 2025-05-14
 */
public interface MerchantService {

    /**
     * 查询所有商家 (Merchant Table)
     *
     * @return 商家 (Merchant Table)集合
     */
    List<Merchant> findAll();

    /**
     * 根据ID查询商家 (Merchant Table)
     *
     * @param merchantId 商家ID (Merchant ID)
     * @return 商家 (Merchant Table)
     */
    Merchant selectMerchantById(String merchantId);

    /**
     * 根据用户ID查询商家 (Merchant Table)列表
     *
     * @param userId 用户ID (User ID)
     * @return 商家 (Merchant Table)集合
     */
    List<Merchant> selectMerchantsByUserId(String userId);

    /**
     * 根据商场ID查询商家 (Merchant Table)列表
     *
     * @param mallId 所属商场ID (Mall ID)
     * @return 商家 (Merchant Table)集合
     */
    List<Merchant> selectMerchantsByMallId(String mallId);

    /**
     * 查询商家 (Merchant Table)列表 (前端负责过滤)
     *
     * @return 商家 (Merchant Table)集合
     */
    List<Merchant> selectMerchantList();

    /**
     * 新增商家 (Merchant Table)
     *
     * @param merchant 商家 (Merchant Table)实体
     * @return 结果 (影响的行数)
     */
    int insertMerchant(Merchant merchant);

    /**
     * 修改商家 (Merchant Table)
     *
     * @param merchant 商家 (Merchant Table)实体
     * @return 结果 (影响的行数)
     */
    int updateMerchant(Merchant merchant);

    /**
     * 删除商家 (Merchant Table)
     *
     * @param merchantId 商家ID (Merchant ID)
     * @return 结果 (影响的行数)
     */
    int deleteMerchantById(String merchantId);

    // Batch delete method removed
    // int deleteMerchantByIds(String[] merchantIds);
}