// src/main/java/com/smartmall/domain/MallStatistics.java
package com.smartmall.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商场统计数据实体
 *
 * @author ccut_zzy
 * @date 2025-05-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallStatistics {
    private int merchantCount;
    private int productCount;
    private int todayOrderCount;

}