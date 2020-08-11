package com.tang.spring.use.service;

import com.tang.spring.use.po.User;

import java.util.List;
import java.util.Map;

/**
 * 用户数据查询service层接口
 *
 * @author heguitang
 */
public interface UserService {

    List<User> queryUsers(Map<String, Object> param);
}
