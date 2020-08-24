package com.tang.spring.frame.ioc.registry;

import java.util.HashMap;
import java.util.Map;

/**
 * 单例bean默认的注册实现
 *
 * @author heguitang
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    /**
     * 使用 HashMap 容器来存储单例bean的实例（可考虑线程安全的进行单例管理）
     */
    private Map<String, Object> singletonObjects = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return this.singletonObjects.get(beanName);
    }

    @Override
    public void addSingleton(String beanName, Object bean) {
        // TODO 可以使用双重检查锁进行安全处理
        this.singletonObjects.put(beanName, bean);
    }
}
