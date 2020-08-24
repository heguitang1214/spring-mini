package com.tang.spring.frame.ioc.factory;

/**
 * 生成bean的工厂，是个顶级接口
 *
 * @author heguitang
 */
public interface BeanFactory {

    /**
     * 根据beanName 获取bean
     *
     * @param beanName bean的名字
     * @return 返回一个bean对象
     */
    Object getBean(String beanName);
}
