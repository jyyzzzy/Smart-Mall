package com.smartmall.mapper;

import com.smartmall.domain.Mall;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 商场表 (Mall Table)Mapper接口
 *
 * @author ccut_zzy
 * @date 2025-05-14
 */
@Mapper
public interface MallMapper {

    /**
     * 查询所有商场 (Mall Table)
     *
     * @return 商场 (Mall Table)集合
     */
    @Select("select mall_id, user_id, mall_name, description, address, contact_phone, contact_email from malls")
    List<Mall> findAll();

    /**
     * 根据ID查询商场 (Mall Table)
     *
     * @param mallId 商场ID (Mall ID)
     * @return 商场 (Mall Table)
     */
    @Select("select mall_id, user_id, mall_name, description, address, contact_phone, contact_email from malls where mall_id = #{mallId}")
    Mall selectMallById(String mallId);

    /**
     * 根据用户ID查询商场 (Mall Table)列表
     *
     * @param userId 用户ID (User ID)
     * @return 商场 (Mall Table)集合
     */
    @Select("select mall_id, user_id, mall_name, description, address, contact_phone, contact_email from malls where user_id = #{userId}")
    List<Mall> selectMallsByUserId(String userId);

    /**
     * 查询所有商场 (Mall Table)列表 (前端负责过滤)
     *
     * @return 商场 (Mall Table)集合
     */
    @Select("select mall_id, user_id, mall_name, description, address, contact_phone, contact_email from malls")
    List<Mall> selectMallList();


    /**
     * 新增商场 (Mall Table)
     *
     * @param mall 商场 (Mall Table)实体
     * @return 结果 (影响的行数)
     */
    @Insert("""
            INSERT INTO malls (mall_id, user_id, mall_name, description, address, contact_phone, contact_email)
            VALUES (UUID(), #{userId}, #{mallName}, #{description}, #{address}, #{contactPhone}, #{contactEmail});
            """)
    int insertMall(Mall mall);

    /**
     * 修改商场 (Mall Table)
     *
     * @param mall 商场 (Mall Table)实体
     * @return 结果 (影响的行数)
     */
    @Update("""
            UPDATE malls
            SET user_id = #{userId}, mall_name = #{mallName}, description = #{description}, address = #{address},
                contact_phone = #{contactPhone}, contact_email = #{contactEmail}
            WHERE mall_id = #{mallId};
            """)
    int updateMall(Mall mall);

    /**
     * 删除商场 (Mall Table)
     *
     * @param mallId 商场ID (Mall ID)
     * @return 结果 (影响的行数)
     */
    @Delete("DELETE FROM malls where mall_id = #{mallId}")
    int deleteMallById(String mallId);

    // Batch delete method removed as requested to remove <script>
    // public int deleteMallByIds(String[] mallIds);
}