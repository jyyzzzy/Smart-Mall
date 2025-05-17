package com.smartmall.service;

import com.smartmall.domain.Mall;

import java.util.List;

/**
 * 商场表 (Mall Table)Service接口
 *
 * @author ccut_zzy
 * @date 2025-05-14
 */
public interface MallService {

    /**
     * 查询所有商场 (Mall Table)
     *
     * @return 商场 (Mall Table)集合
     */
    List<Mall> findAll();

    /**
     * 根据ID查询商场 (Mall Table)
     *
     * @param mallId 商场ID (Mall ID)
     * @return 商场 (Mall Table)
     */
    Mall selectMallById(String mallId);

    /**
     * 根据用户ID查询商场 (Mall Table)列表
     *
     * @param userId 用户ID (User ID)
     * @return 商场 (Mall Table)集合
     */
    List<Mall> selectMallsByUserId(String userId);

    /**
     * 查询商场 (Mall Table)列表 (前端负责过滤)
     *
     * @return 商场 (Mall Table)集合
     */
    List<Mall> selectMallList();

    /**
     * 新增商场 (Mall Table)
     *
     * @param mall 商场 (Mall Table)实体
     * @return 结果 (影响的行数)
     */
    int insertMall(Mall mall);

    /**
     * 修改商场 (Mall Table)
     *
     * @param mall 商场 (Mall Table)实体
     * @return 结果 (影响的行数)
     */
    int updateMall(Mall mall);

    /**
     * 删除商场 (Mall Table)
     *
     * @param mallId 商场ID (Mall ID)
     * @return 结果 (影响的行数)
     */
    int deleteMallById(String mallId);

    // Batch delete method removed
    // int deleteMallByIds(String[] mallIds);
}