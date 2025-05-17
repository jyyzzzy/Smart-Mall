// src/main/java/com/smartmall/service/MallStatsService.java
package com.smartmall.service;

import com.smartmall.domain.MallStatistics;

/**
 * 商场统计业务Service接口
 *
 * @author ccut_zzy
 * @date 2025-05-14
 */
public interface MallStatsService {

    /**
     * 获取指定商场的统计数据 (总商户数, 总商品数, 今日订单数)
     *
     * @param mallId 商场ID
     * @return 商场统计数据
     */
    MallStatistics getMallStatistics(String mallId);
}