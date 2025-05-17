package com.smartmall.mapper;

import com.smartmall.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 用户 (User Table)Mapper接口
 *
 * @author ccut_zzy
 * @date 2025-04-08
 */
@Mapper
public interface UserMapper {
    @Select("select * from users")
    public List<User> findAll();

    /**
     * 查询用户 (User Table)
     *
     * @param user 用户 (User Table)主键
     * @return 用户 (User Table)
     */
    @Select("""
                        select user_id, username, password, role, phone, email, created_at, updated_at
                        from users
                        where username = #{username} and password = #{password};
            """)
    public List<User> selectUserByUserNameAndPassword(User user);

    /**
     * 查询用户 (User Table)
     *
     * @param user 用户 (User Table)主键
     * @return 用户 (User Table)
     */
    @Select("""
                        select user_id, username, password, role, phone, email, created_at, updated_at
                        from users
                        where username = #{username};
            """)
    public List<User> selectUserByUserName(User user);

    /**
     * 查询用户 (User Table)列表
     *
     * @param user 用户 (User Table)
     * @return 用户 (User Table)集合
     */
    @Select("""
            select user_id, username, password, role, phone, email, created_at, updated_at
            from users
            where user_id = #{userId} or username = #{username} or password = #{password} or role = #{role};
            """)
    public List<User> selectUserList(User user);

    /**
     * 新增用户 (User Table)
     *
     * @param user 用户 (User Table)
     * @return 结果
     */
    @Insert("""
            INSERT INTO users (user_id, username, password, role, phone, email, created_at, updated_at)
            VALUES (UUID(), #{username}, #{password}, #{role}, #{phone}, #{email}, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
            """)
    public int insertUser(User user);

    /**
     * 修改用户 (User Table)
     *
     * @param user 用户 (User Table)
     * @return 结果
     */
    @Update("""
            UPDATE users
            SET username = #{username}, password = #{password},  role = #{role}, phone = #{phone}, email = #{email}, updated_at = CURRENT_TIMESTAMP
            WHERE user_id = #{userId};
            """)
    public int updateUser(User user);

    /**
     * 删除用户 (User Table)
     *
     * @param userId 用户 (User Table)主键
     * @return 结果
     */
    @Delete("""
            DELETE FROM users where user_id = #{userId};
            """)
    public int deleteUserByUserId(String userId);

    /**
     * 批量删除用户 (User Table)
     *
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    //public int deleteUserByUserIds(String[] userIds);
}
