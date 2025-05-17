// src/main/java/com/smartmall/service/impl/MallStatsServiceImpl.java
package com.smartmall.service.impl;

import com.smartmall.domain.MallStatistics;
import com.smartmall.mapper.MerchantMapper;
import com.smartmall.mapper.ProductMapper;
import com.smartmall.mapper.OrderMapper;
import com.smartmall.service.MallStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * 商场统计业务Service实现
 *
 * @author ccut_zzy
 * @date 2025-05-14
 */
@Service
public class MallStatsServiceImpl implements MallStatsService {

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 获取指定商场的统计数据 (总商户数, 总商品数, 今日订单数)
     */
    @Override
    public MallStatistics getMallStatistics(String mallId) {
        // 1. Get merchant count by mall ID
        int merchantCount = merchantMapper.countMerchantsByMallId(mallId);

        // 2. Get product count by mall ID (joining through merchants)
        int productCount = productMapper.countProductsByMallId(mallId);

        // 3. Get today's order count by mall ID
        Date todayStart = getStartOfToday();
        Date todayEnd = getStartOfTomorrow();
        int todayOrderCount = orderMapper.countOrdersByMallIdAndDateRange(mallId, todayStart, todayEnd);

        // 4. Assemble and return statistics
        return new MallStatistics(merchantCount, productCount, todayOrderCount);
    }

    /**
     * Helper method to get the start of the current day.
     */
    private Date getStartOfToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Helper method to get the start of the next day.
     */
    private Date getStartOfTomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getStartOfToday());
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }
}