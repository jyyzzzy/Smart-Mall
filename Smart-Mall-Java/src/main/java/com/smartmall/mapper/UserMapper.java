package com.smartmall.mapper;

import com.smartmall.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户 (User Table)Mapper接口
 *
 * @author ccut_zzy
 * @date 2025-04-08
 */
@Mapper
public interface UserMapper {
    @Select("select * from t_user")
    public List<User> findAll();
    /**
     * 查询用户 (User Table)
     *
     * @param userId 用户 (User Table)主键
     * @return 用户 (User Table)
     */
    public User selectUserByUserId(String userId);

    /**
     * 查询用户 (User Table)列表
     *
     * @param user 用户 (User Table)
     * @return 用户 (User Table)集合
     */
    public List<User> selectUserList(User user);

    /**
     * 新增用户 (User Table)
     *
     * @param user 用户 (User Table)
     * @return 结果
     */
    public int insertUser(User user);

    /**
     * 修改用户 (User Table)
     *
     * @param user 用户 (User Table)
     * @return 结果
     */
    public int updateUser(User user);

    /**
     * 删除用户 (User Table)
     *
     * @param userId 用户 (User Table)主键
     * @return 结果
     */
    public int deleteUserByUserId(String userId);

    /**
     * 批量删除用户 (User Table)
     *
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserByUserIds(String[] userIds);
}
