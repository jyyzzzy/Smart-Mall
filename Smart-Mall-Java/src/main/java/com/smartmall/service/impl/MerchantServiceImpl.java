package com.smartmall.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smartmall.mapper.MerchantMapper;
import com.smartmall.domain.Merchant;
import com.smartmall.service.MerchantService;

/**
 * 商家表 (Merchant Table)Service业务层处理
 *
 * @author ccut_zzy
 * @date 2025-05-14
 */
@Service
public class MerchantServiceImpl implements MerchantService {
    @Autowired
    private MerchantMapper merchantMapper;

    /**
     * 查询所有商家 (Merchant Table)
     */
    @Override
    public List<Merchant> findAll() {
        return merchantMapper.findAll();
    }

    /**
     * 根据ID查询商家 (Merchant Table)
     */
    @Override
    public Merchant selectMerchantById(String merchantId) {
        return merchantMapper.selectMerchantById(merchantId);
    }

    /**
     * 根据用户ID查询商家 (Merchant Table)列表
     */
    @Override
    public List<Merchant> selectMerchantsByUserId(String userId) {
        return merchantMapper.selectMerchantsByUserId(userId);
    }

    /**
     * 根据商场ID查询商家 (Merchant Table)列表
     */
    @Override
    public List<Merchant> selectMerchantsByMallId(String mallId) {
        return merchantMapper.selectMerchantsByMallId(mallId);
    }

    /**
     * 查询商家 (Merchant Table)列表 (前端负责过滤)
     */
    @Override
    public List<Merchant> selectMerchantList() {
        return merchantMapper.selectMerchantList();
    }

    /**
     * 新增商家 (Merchant Table)
     */
    @Override
    public int insertMerchant(Merchant merchant) {
        // Generate UUID for merchantId if not provided
        if (merchant.getMerchantId() == null || merchant.getMerchantId().isEmpty()) {
            merchant.setMerchantId(UUID.randomUUID().toString());
        }
        return merchantMapper.insertMerchant(merchant);
    }

    /**
     * 修改商家 (Merchant Table)
     */
    @Override
    public int updateMerchant(Merchant merchant) {
        return merchantMapper.updateMerchant(merchant);
    }

    /**
     * 删除商家 (Merchant Table)
     */
    @Override
    public int deleteMerchantById(String merchantId) {
        return merchantMapper.deleteMerchantById(merchantId);
    }

    // Batch delete method removed
    /*@Override
    public int deleteMerchantByIds(String[] merchantIds)
    {
         // Implementation would involve looping through merchantIds and calling deleteMerchantById
        // or using a different batching strategy outside the simple mapper annotation
        return 0; // Placeholder
    }*/
}