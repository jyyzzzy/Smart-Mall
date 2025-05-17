package com.smartmall.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smartmall.mapper.MallMapper;
import com.smartmall.domain.Mall;
import com.smartmall.service.MallService;

/**
 * 商场表 (Mall Table)Service业务层处理
 *
 * @author ccut_zzy
 * @date 2025-05-14
 */
@Service
public class MallServiceImpl implements MallService {
    @Autowired
    private MallMapper mallMapper;

    /**
     * 查询所有商场 (Mall Table)
     */
    @Override
    public List<Mall> findAll() {
        return mallMapper.findAll();
    }

    /**
     * 根据ID查询商场 (Mall Table)
     */
    @Override
    public Mall selectMallById(String mallId) {
        return mallMapper.selectMallById(mallId);
    }

    /**
     * 根据用户ID查询商场 (Mall Table)列表
     */
    @Override
    public List<Mall> selectMallsByUserId(String userId) {
        return mallMapper.selectMallsByUserId(userId);
    }

    /**
     * 查询商场 (Mall Table)列表 (前端负责过滤)
     */
    @Override
    public List<Mall> selectMallList() {
        return mallMapper.selectMallList();
    }

    /**
     * 新增商场 (Mall Table)
     */
    @Override
    public int insertMall(Mall mall) {
        // Generate UUID for mallId if not provided
        if (mall.getMallId() == null || mall.getMallId().isEmpty()) {
            mall.setMallId(UUID.randomUUID().toString());
        }
        return mallMapper.insertMall(mall);
    }

    /**
     * 修改商场 (Mall Table)
     */
    @Override
    public int updateMall(Mall mall) {
        return mallMapper.updateMall(mall);
    }

    /**
     * 删除商场 (Mall Table)
     */
    @Override
    public int deleteMallById(String mallId) {
        return mallMapper.deleteMallById(mallId);
    }

    // Batch delete method removed
    /*@Override
    public int deleteMallByIds(String[] mallIds)
    {
        // Implementation would involve looping through mallIds and calling deleteMallById
        // or using a different batching strategy outside the simple mapper annotation
        return 0; // Placeholder
    }*/
}