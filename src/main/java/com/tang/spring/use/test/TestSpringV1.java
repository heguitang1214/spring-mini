package com.tang.spring.use.test;


import com.tang.spring.frame.ioc.BeanDefinition;
import com.tang.spring.frame.ioc.PropertyValue;
import com.tang.spring.frame.ioc.RuntimeBeanReference;
import com.tang.spring.frame.ioc.TypedStringValue;
import com.tang.spring.use.po.User;
import com.tang.spring.use.service.UserService;
import com.tang.spring.use.service.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 使用面向过程思维和配置文件的方式去实现容器化管理Bean
 */
public class TestSpringV1 {

    /**
     * 存储单例Bean实例的Map容器
     */
    private Map<String, Object> singletonObjects = new HashMap<>();

    /**
     * 存储BeanDefinition的容器
     */
    private Map<String, BeanDefinition> beanDefinitions = new HashMap<>();

    @Before
    public void beforse() {
        // TODO XML解析spring的配置文件
        // 解析后，会把bean的信息放在类BeanDefinition中，也就是填充 beanDefinitions
    }

    // 由A程序员编写
    @Test
    public void test() {
        // A 程序员他其实只想使用业务对象去调用对应的服务
        // B 程序员编写了一段代码给A程序员提供对象
        UserService userService = (UserService) getBean("userService");

        //实现用户查询功能
        Map<String, Object> map = new HashMap<>();
        map.put("username", "千年老亚瑟");

        List<User> users = userService.queryUsers(map);
        System.out.println(users);
    }

    // 解决的是扩展性问题：通过配置的方式去解决扩展性问题
    // 使用XML配置文件进行Bean的创建
    // 1.管理要new出来的bean的class信息（要new几个对象，就需要配置几个class信息）
    // <bean id="bean的唯一name" class="要new的对象的全路径"></bean>
    // 2.管理要new出来的bean的属性的依赖关系（如果A对象依赖了B对象，那么这两个对象都要配置class信息，并且要确定关系）
    // <bean id="bean的唯一name" class="要new的对象的全路径">
    //      <property name="属性名称" ref="要建立关系的另一个bean的唯一name"/>
    // </bean>
    // 3.读取静态的信息，去创建对象
    // BeanDefinition类--->用来存储<bean>标签中的信息
    // Map<String,BeanDefinition>
    // 4.利用反射从BeanDefinition中获取class信息，区创建对象
    public Object getBean(String beanName) {
        // 1.首先从singletonObjects集合中获取对应beanName的实例
        Object singletonObject = this.singletonObjects.get(beanName);
        // 2.如果有对象，则直接返回
        if (singletonObject != null) {
            return singletonObject;
        }
        // 3.如果没有改对象，则获取对应的BeanDefinition信息
        BeanDefinition beanDefinition = this.beanDefinitions.get(beanName);
        if (beanDefinition == null) {
            return null;
        }
        // 4.判断是单例还是多例，如果是单例，则走单例创建Bean流程
        // 5.单例流程中，需要将创建出来的Bean放入singletonObjects集合中
        // 6.如果是多例，走多例的创建Bean流程
        if (beanDefinition.isSingleton()) {
            singletonObject = doCreateBean(beanDefinition);

            this.singletonObjects.put(beanName, singletonObject);
        } else if (beanDefinition.isPrototype()) {
            singletonObject = doCreateBean(beanDefinition);
        }

        return singletonObject;
    }

    /**
     * 创建一个bean实例
     *
     * @param beanDefinition bean的定义信息
     * @return bean实例
     */
    private Object doCreateBean(BeanDefinition beanDefinition) {
        // 1.Bean的实例化
        Object bean = createBeanByConstructor(beanDefinition);
        // 2.Bean的属性填充（依赖注入）
        populateBean(bean, beanDefinition);
        // 3.Bean的初始化
        initilizeBean(bean, beanDefinition);
        return bean;
    }

    private void initilizeBean(Object bean, BeanDefinition beanDefinition) {
        // TODO 需要针对Aware接口标记的类进行特殊处理

        // TODO 可以进行IntilizingBean接口的处理
        invokeInitMethod(bean, beanDefinition);
    }

    private void invokeInitMethod(Object bean, BeanDefinition beanDefinition) {
        try {
            String initMethod = beanDefinition.getInitMethod();
            if (initMethod == null) {
                return;
            }
            Class<?> clazzType = beanDefinition.getClazzType();
            Method method = clazzType.getDeclaredMethod(initMethod);
            method.setAccessible(true);
            method.invoke(bean);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateBean(Object bean, BeanDefinition beanDefinition) {
        List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue pv : propertyValues) {
            String name = pv.getName();
            // 这不是我们需要给Bean设置的value值
            Object value = pv.getValue();
            Object valueToUse = resoleValue(value);

            setProperty(bean, name, valueToUse);
        }
    }

    private void setProperty(Object bean, String name, Object valueToUse) {
        try {
            Class<?> aClass = bean.getClass();
            Field field = aClass.getDeclaredField(name);
            field.setAccessible(true);
            // 给属性赋值
            field.set(bean, valueToUse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object resoleValue(Object value) {
        if (value instanceof TypedStringValue) {
            TypedStringValue typedStringValue = (TypedStringValue) value;
            Class<?> targetType = typedStringValue.getTargetType();
            String stringValue = typedStringValue.getValue();
            if (targetType == Integer.class) {
                return Integer.parseInt(stringValue);
            } else if (targetType == String.class) {
                return stringValue;
            }//TODO 其他类型
        } else if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference beanReference = (RuntimeBeanReference) value;
            String ref = beanReference.getRef();
            // 递归调用
            return getBean(ref);
        }
        return null;
    }

    private Object createBeanByConstructor(BeanDefinition beanDefinition) {
        // TODO 静态工厂方法、工厂实例方法
        // 构造器方式去创建Bean实例
        try {
            Class<?> clazzType = beanDefinition.getClazzType();
            // 选择无参构造器
            Constructor<?> constructor = clazzType.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
