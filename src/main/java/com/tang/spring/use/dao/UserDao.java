package com.tang.spring.use.dao;

import com.tang.spring.use.po.User;

import java.util.List;
import java.util.Map;

/**
 * 查询用户数据接口
 */
public interface UserDao {

    List<User> queryUserList(Map<String, Object> param);
}
