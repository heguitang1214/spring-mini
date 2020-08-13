package com.tang.spring.frame.registry;

/**
 * 1、实现类封装了spring容器创建出来的所有的单例Bean的集合
 * 2、接口提供了对于其封装的数据进行操作的接口功能（获取Bean\添加Bean）
 *
 * @author heguitang
 */
public interface SingletonBeanRegistry {

    /**
     * 获取单例Bean
     *
     * @param beanName 名称
     * @return 对应的实例
     */
    Object getSingleton(String beanName);

    /**
     * 添加单例Bean到集合缓存中
     *
     * @param beanName 名称
     * @param bean     实例
     */
    void addSingleton(String beanName, Object bean);
}
