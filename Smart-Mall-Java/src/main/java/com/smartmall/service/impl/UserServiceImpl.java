package com.smartmall.service.impl;

import java.util.List;

import com.smartmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smartmall.mapper.UserMapper;
import com.smartmall.domain.User;
import com.smartmall.service.UserService;

/**
 * 用户 (User Table)Service业务层处理
 *
 * @author ccut_zzy
 * @date 2025-04-08
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    /**
     * 查询用户 (User Table)
     *
     * @param user 用户 (User Table)主键
     * @return 用户 (User Table)
     */
    @Override
    public List<User> selectUserByUserName(User user) {
        return userMapper.selectUserByUserName(user);
    }

    @Override
    public User selectUserByUserId(User user) {
        return userMapper.selectUserByUserId(user);
    }

    @Override
    public List<User> selectUserByUserNameAndPassword(User user) {
        return userMapper.selectUserByUserNameAndPassword(user);
    }

    /**
     * 查询用户 (User Table)列表
     *
     * @param user 用户 (User Table)
     * @return 用户 (User Table)
     */
    @Override
    public List<User> selectUserList(User user) {
        return userMapper.selectUserList(user);
    }

    /**
     * 新增用户 (User Table)
     *
     * @param user 用户 (User Table)
     * @return 结果
     */
    @Override
    public int insertUser(User user) {
        return userMapper.insertUser(user);
    }

    /**
     * 修改用户 (User Table)
     *
     * @param user 用户 (User Table)
     * @return 结果
     */
    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    /**
     * 批量删除用户 (User Table)
     *
     * @param userIds 需要删除的用户 (User Table)主键
     * @return 结果
     */
    /*@Override
    public int deleteUserByUserIds(String[] userIds)
    {
        return userMapper.deleteUserByUserIds(userIds);
    }*/

    /**
     * 删除用户 (User Table)信息
     *
     * @param userId 用户 (User Table)主键
     * @return 结果
     */
    @Override
    public int deleteUserByUserId(String userId) {
        return userMapper.deleteUserByUserId(userId);
    }
}
