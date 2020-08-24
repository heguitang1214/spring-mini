package com.tang.spring.frame.ioc.factory.support;


import com.tang.spring.frame.ioc.config.BeanDefinition;
import com.tang.spring.frame.ioc.config.PropertyValue;
import com.tang.spring.frame.ioc.resolver.BeanDefinitionValueResolver;
import com.tang.spring.frame.utils.ReflectUtils;

import java.util.List;

/**
 * 完成Bean的创建和依赖装配
 *
 * @author heguitang
 */
public abstract class AbstractAutowiredCapableBeanFactory extends AbstractBeanFactory {

    @Override
    public Object createBean(BeanDefinition bd) {
        // 1.Bean的实例化
        Object bean = createBeanByConstructor(bd);

        // TODO 处理循环依赖

        // 2.Bean的属性填充（依赖注入）
        populateBean(bean, bd);
        // 3.Bean的初始化
        initilizeBean(bean, bd);
        return bean;
    }

    /**
     * 通过构造器的方式创建bean
     *
     * @param beanDefinition bean定义信息
     * @return 对象
     */
    private Object createBeanByConstructor(BeanDefinition beanDefinition) {
        // TODO 还可以使用 静态工厂方法、工厂实例方法
        // 构造器方式去创建Bean实例
        Class<?> clazzType = beanDefinition.getClazzType();
        return ReflectUtils.createBean(clazzType);
    }

    /**
     * 对应bean的属性填充
     *
     * @param bean           bean实例
     * @param beanDefinition bean定义信息
     */
    private void populateBean(Object bean, BeanDefinition beanDefinition) {
        // 获取对应的属性信息
        List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue pv : propertyValues) {
            String name = pv.getName();
            // 这不是我们需要给Bean设置的value值
            Object value = pv.getValue();

            BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this);
            Object valueToUse = resolver.resoleValue(value);
            // 使用反射进行属性值的填充
            ReflectUtils.setProperty(bean, name, valueToUse);
        }
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
            ReflectUtils.invokeMethod(bean, initMethod);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
