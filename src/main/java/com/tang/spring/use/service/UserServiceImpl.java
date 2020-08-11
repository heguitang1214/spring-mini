package com.tang.spring.use.service;

import com.tang.spring.use.dao.UserDao;
import com.tang.spring.use.po.User;

import java.util.List;
import java.util.Map;


public class UserServiceImpl implements UserService {

    /**
     * 依赖注入UserDao
     */
    private UserDao userDao;

    /**
     * 使用setter方法注入UserDao
     *
     * @param userDao 需要注入的对象
     */
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> queryUsers(Map<String, Object> param) {
        return userDao.queryUserList(param);
    }

}
