package com.tang.spring.frame.ioc.factory.support;


import com.tang.spring.frame.ioc.config.BeanDefinition;
import com.tang.spring.frame.ioc.registry.BeanDefinitionRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * Spring 中真正用来完成容器管理的 BeanFactory 实现类，它是集所有接口或者抽象类的功能与一身。
 * 它本身即是 BeanFactory 的实现类，又是 BeanDefinitionRegistry 的实现类(BeanDefinition的注册器)
 *
 * @author heguitang
 */
public class DefaultListableBeanFactory extends AbstractAutowiredCapableBeanFactory implements BeanDefinitionRegistry {
    /**
     * 存储BeanDefinition的容器
     */
    private Map<String, BeanDefinition> beanDefinitions = new HashMap<>();


    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return this.beanDefinitions.get(beanName);
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition bd) {
        this.beanDefinitions.put(beanName, bd);
    }
}
