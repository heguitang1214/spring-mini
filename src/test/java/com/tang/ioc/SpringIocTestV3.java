package com.tang.ioc;


import com.tang.spring.frame.ioc.factory.support.DefaultListableBeanFactory;
import com.tang.spring.frame.ioc.reader.XmlBeanDefinitionReader;
import com.tang.spring.frame.ioc.resource.ClasspathResource;
import com.tang.spring.frame.ioc.resource.Resource;
import com.tang.spring.use.po.User;
import com.tang.spring.use.service.UserService;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 使用面向对象思维和配置文件的方式去实现容器化管理Bean
 */
public class SpringIocTestV3 {

    private DefaultListableBeanFactory beanFactory;

    /**
     * 注册BeanDefinition流程
     */
    @Before
    public void before() {
        //完成XML解析，其实就是完成BeanDefinition的注册
        // XML解析，解析的结果，放入beanDefinitions中
        String location = "beans.xml";
        // 获取流对象(使用了策略模式)
        Resource resource = new ClasspathResource(location);
        InputStream inputStream = resource.getResource();

        // 按照spring定义的标签语义去解析Document文档
        beanFactory = new DefaultListableBeanFactory();
        // 向上转型
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        beanDefinitionReader.loadBeanDefinitions(inputStream);
    }


    /**
     * getBean获取业务对象之后，调用业务处理流程
     */
    @Test
    public void test() {
        // A 程序员他其实只想使用业务对象去调用对应的服务
        // B 程序员编写了一段代码给A程序员提供对象
        UserService userService = (UserService) beanFactory.getBean("userService");


        //实现用户查询功能
        Map<String, Object> map = new HashMap<>();
        map.put("username", "唐家三少");

        List<User> users = userService.queryUsers(map);
        System.out.println(users);
    }
    
}
