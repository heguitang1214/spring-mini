package com.tang.ioc;


import com.tang.spring.frame.ioc.config.BeanDefinition;
import com.tang.spring.use.dao.UserDaoImpl;
import com.tang.spring.use.po.User;
import com.tang.spring.use.service.UserService;
import com.tang.spring.use.service.UserServiceImpl;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 使用面向过程思维实现数据的访问
 */
public class SpringIocTestV1 {
    
    /**
     * 使用手动创建对象的方式，不使用容器来管理我们的bean
     */
    @Test
    public void testUserService() {
        // 使用业务对象去调用对应的服务
        UserService userService = getUserService();

        //实现用户查询功能
        Map<String, Object> map = new HashMap<>();
        map.put("username", "一刀修罗");

        List<User> users = userService.queryUsers(map);
        System.out.println(users);
    }


    /**
     * 使用简单获取对象的方式
     */
    @Test
    public void testGetBean() {
        // 使用业务对象去调用对应的服务
        UserService userService = (UserService) getBean("userService");

        //实现用户查询功能
        Map<String, Object> map = new HashMap<>();
        map.put("username", "一刀修罗");

        List<User> users = userService.queryUsers(map);
        System.out.println(users);
    }


    public UserService getUserService() {
        UserServiceImpl userService = new UserServiceImpl();
        UserDaoImpl userDao = new UserDaoImpl();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/demo?characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        userDao.setDataSource(dataSource);
        userService.setUserDao(userDao);
        return userService;
    }


    public Object getBean(String beanName) {
        if ("userService".equals(beanName)) {
            UserServiceImpl userService = new UserServiceImpl();
            UserDaoImpl userDao = new UserDaoImpl();
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://localhost:3306/demo?characterEncoding=utf-8");
            dataSource.setUsername("root");
            dataSource.setPassword("root");
            userDao.setDataSource(dataSource);
            userService.setUserDao(userDao);
            return userService;
        }
        return null;
    }


}
