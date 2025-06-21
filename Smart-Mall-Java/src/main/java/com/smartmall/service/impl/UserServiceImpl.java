package com.smartmall.service.impl;

import com.smartmall.domain.User;
import com.smartmall.mapper.UserMapper;
import com.smartmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * UserService 业务实现 & Spring Security UserDetailsService
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired private UserMapper userMapper;
    @Autowired private PasswordEncoder passwordEncoder;

    /* ==================== 基础 CRUD ==================== */
    @Override
    public List<User> findAll() {return userMapper.findAll();}

    @Override
    public User findByUsername(String username) {
        if (username == null) return null;
        return userMapper.findByUsername(username.trim());
    }

    @Override
    public boolean exists(String username) {return userMapper.countByUsername(username) > 0;}

    @Override
    public int insertUser(User user) {
        // 注册时自动加密密码——保持幂等，若已加密则不重复加密
        if (!user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userMapper.insertUser(user); // useGeneratedKeys 回填 userId
    }

    @Override
    public int updateUser(User user) {return userMapper.updateUser(user);}

    @Override
    public int deleteUserByUserId(String userId) {return userMapper.deleteUserByUserId(userId);}

    /* ==================== Spring Security ==================== */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = findByUsername(username);
        if (u == null) throw new UsernameNotFoundException("User not found: " + username);
        GrantedAuthority auth = new SimpleGrantedAuthority("ROLE_" + (u.getRole()==null?"USER":u.getRole().toUpperCase()));
        return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(), Collections.singletonList(auth));
    }

    /* ==================== 旧接口兼容 ==================== */
    @Override
    public List<User> selectUserByUserName(User user) {return userMapper.selectUserByUserName(user);} // TODO: migrate callers
    @Override
    public User selectUserByUserId(User user) {return userMapper.selectUserByUserId(user);}           // TODO: migrate callers
    @Override
    public List<User> selectUserByUserNameAndPassword(User user) {return userMapper.selectUserByUserNameAndPassword(user);} // 仅历史代码使用
    @Override
    public List<User> selectUserList(User user) {return userMapper.selectUserList(user);}
}
