package com.smartmall.mapper;

import com.smartmall.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * UserMapper ‑ 基于 MyBatis 的用户表操作接口。
 * ▶ 新增 findByUsername / countByUsername，配合 BCrypt 不再按明文密码查询。
 * ▶ insertUser 支持 useGeneratedKeys 自动回填 userId。
 */
@Mapper
public interface UserMapper {

    /* ==================== 查询 ==================== */
    @Select("SELECT user_id, username, password, role, phone, email, created_at, updated_at FROM users")
    List<User> findAll();

    /** 根据用户名查单条 */
    @Select("SELECT user_id, username, password, role, phone, email, created_at, updated_at FROM users WHERE username = #{username}")
    User findByUsername(String username);

    /** 判断重名 */
    @Select("SELECT COUNT(1) FROM users WHERE username = #{username}")
    int countByUsername(String username);

    /** 按条件模糊查询（保留旧接口，可逐步迁移） */
    @Select("""
            SELECT user_id, username, password, role, phone, email, created_at, updated_at
            FROM users
            WHERE (#{userId} IS NULL OR user_id = #{userId})
              AND (#{username} IS NULL OR username = #{username})
              AND (#{role} IS NULL OR role = #{role})
            """)
    List<User> selectUserList(User user);

    /* ==================== 新增 / 修改 / 删除 ==================== */
    @Insert("""
            INSERT INTO users (user_id, username, password, role, phone, email, created_at, updated_at)
            VALUES (UUID(), #{username}, #{password}, #{role}, #{phone}, #{email}, NOW(), NOW())
            """)
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insertUser(User user);

    @Update("""
            UPDATE users
               SET username = #{username},
                   password = #{password},
                   role     = #{role},
                   phone    = #{phone},
                   email    = #{email},
                   updated_at = NOW()
             WHERE user_id = #{userId}
            """)
    int updateUser(User user);

    @Delete("DELETE FROM users WHERE user_id = #{userId}")
    int deleteUserByUserId(String userId);

    /* ==================== 兼容旧方法 ==================== */
    @Deprecated
    default List<User> selectUserByUserName(User user) {
        return List.of(findByUsername(user.getUsername()));
    }

    @Deprecated
    default List<User> selectUserByUserNameAndPassword(User user) {
        // BCrypt 无法直接 SQL 比对密码，保持空实现避免编译错；应在 Service 层使用 matches()
        return List.of();
    }

    @Deprecated
    default User selectUserByUserId(User user) {
        return findById(user.getUserId());
    }

    /* 私有辅助 */
    @Select("SELECT user_id, username, password, role, phone, email, created_at, updated_at FROM users WHERE user_id = #{id}")
    User findById(String id);
}
