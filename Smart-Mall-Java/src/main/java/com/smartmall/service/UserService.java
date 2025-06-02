package com.smartmall.service;

import com.smartmall.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户 (User Table)Service接口
 *
 * @author ccut_zzy
 * @date 2025-04-08
 */
public interface UserService {
    public List<User> findAll();

    /**
     * 查询用户 (User Table)
     *
     * @param user 用户 (User Table)主键
     * @return 用户 (User Table)
     */
    public List<User> selectUserByUserName(User user);

    public User selectUserByUserId(User user);

    public List<User> selectUserByUserNameAndPassword(User user);

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
     * 批量删除用户 (User Table)
     *
     * @param userIds 需要删除的用户 (User Table)主键集合
     * @return 结果
     */
    //public int deleteUserByUserIds(String[] userIds);

    /**
     * 删除用户 (User Table)信息
     *
     * @param userId 用户 (User Table)主键
     * @return 结果
     */
    public int deleteUserByUserId(String userId);
}
