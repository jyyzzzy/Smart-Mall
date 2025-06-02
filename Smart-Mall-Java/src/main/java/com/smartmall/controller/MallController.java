package com.smartmall.controller;

import com.smartmall.domain.MallStatistics;
import com.smartmall.domain.Result; // Assuming Result class exists
import com.smartmall.domain.Mall;
import com.smartmall.service.MallService;
import com.smartmall.service.MallStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mall")
public class MallController {

    @Autowired
    private MallService mallService;

    @Autowired
    private MallStatsService mallStatsService; // Inject the new Service

    /**
     * 获取所有商场列表
     */
    @GetMapping("/all")
    public Result getAllMalls() {
        List<Mall> mallList = mallService.findAll();
        return Result.success(mallList);
    }

    /**
     * 根据ID获取商场信息
     */
    @GetMapping("/{mallId}")
    public Result getMallById(@PathVariable String mallId) {
        Mall mall = mallService.selectMallById(mallId);
        if (mall != null) {
            return Result.success(mall);
        } else {
            // Assuming Result class has an error method with message
            return Result.error("Mall not found with ID: " + mallId);
        }
    }

    /**
     * 根据用户ID获取商场列表
     */
    @GetMapping("/user/{userId}")
    public Result getMallsByUserId(@PathVariable String userId) {
        List<Mall> mallList = mallService.selectMallsByUserId(userId);
        return Result.success(mallList);
    }


    /**
     * 查询所有商场列表 (前端负责过滤)
     */
    @GetMapping("/list") // Changed to GET as no filtering body is expected here
    public Result getMallList() {
        List<Mall> mallList = mallService.selectMallList();
        return Result.success(mallList);
    }


    /**
     * 创建新商场
     */
    @PostMapping
    public Result createMall(@RequestBody Mall mall) {
        int result = mallService.insertMall(mall);
        if (result > 0) {
            return Result.success();
        } else {
            // Assuming Result class has an error method
            return Result.error("Failed to create mall.");
        }
    }

    /**
     * 更新商场信息
     */
    @PutMapping
    public Result updateMall(@RequestBody Mall mall) {
        if (mall.getMallId() == null || mall.getMallId().isEmpty()) {
            // Assuming Result class has an error method
            return Result.error("Mall ID is required for update.");
        }
        int result = mallService.updateMall(mall);
        if (result > 0) {
            return Result.success();
        } else {
            // Assuming Result class has an error method
            return Result.error("Failed to update mall or mall not found.");
        }
    }

    /**
     * 根据ID删除商场
     */
    @DeleteMapping("/{mallId}")
    public Result deleteMallById(@PathVariable String mallId) {
        int result = mallService.deleteMallById(mallId);
        if (result > 0) {
            return Result.success();
        } else {
            // Assuming Result class has an error method
            return Result.error("Failed to delete mall or mall not found.");
        }
    }

    /**
     * 获取指定商场的统计数据
     * 包括总商户数、总商品数和今日订单数。
     */
    @GetMapping("/{mallId}/stats")
    public Result getMallStatistics(@PathVariable String mallId) {
        // 1. 验证 mallId 是否有效 (可选，但推荐)
        Mall mall = mallService.selectMallById(mallId);
        if (mall == null) {
            return Result.error("未找到ID为 " + mallId + " 的商场");
        }

        // 2. 从 MallStatsService 获取统计数据
        // 假设 MallStatsService 有一个 getMallStatistics 方法返回 MallStatistics 对象
        // MallStatistics 对象应包含 merchantCount, productCount, todayOrderCount 等字段
        try {
            MallStatistics stats = mallStatsService.getMallStatistics(mallId);
            if (stats != null) {
                return Result.success(stats);
            } else {
                // 如果 stats 为 null，可能表示没有统计数据或获取失败
                return Result.error("无法获取ID为 " + mallId + " 的商场统计数据");
            }
        } catch (Exception e) {
            // 处理获取统计数据时可能发生的任何异常
            // log.error("获取商场统计数据时出错 mallId: {}: {}", mallId, e.getMessage()); // 假设有日志记录器
            return Result.error("获取商场统计数据时发生内部错误");
        }
    }

    // Batch delete endpoint removed
    /*
     @DeleteMapping("/batch")
    public Result deleteMallByIds(@RequestBody String[] mallIds) {
         // Logic would be in Service or Controller to loop and call deleteById
         return Result.error("Batch delete not implemented in Mapper."); // Example response
    }
    */

    // Batch delete endpoint removed
    /*
     @DeleteMapping("/batch")
    public Result deleteMallByIds(@RequestBody String[] mallIds) {
         // Logic would be in Service or Controller to loop and call deleteById
         return Result.error("Batch delete not implemented in Mapper."); // Example response
    }
    */
}