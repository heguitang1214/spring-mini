package com.tang.spring.frame.ioc.registry;


import com.tang.spring.frame.ioc.config.BeanDefinition;

/**
 * bean定义的注册：
 * 1、接口类是提供对于其封装的BeanDefinition信息进行添加和获取功能
 * 2、实现类封装了BeanDefinition的容器信息（Map）
 *
 * @author heguitang
 */
public interface BeanDefinitionRegistry {

    /**
     * 通过beanName获取对应的bean定义
     *
     * @param beanName 名称
     * @return bean的定义
     */
    BeanDefinition getBeanDefinition(String beanName);

    /**
     * 注册到BeanDefinition的容器中
     *
     * @param beanName 名称
     * @param bd       bean的定义信息
     */
    void registerBeanDefinition(String beanName, BeanDefinition bd);
}
