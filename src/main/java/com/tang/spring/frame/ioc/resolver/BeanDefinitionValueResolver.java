package com.tang.spring.frame.ioc.resolver;


import com.tang.spring.frame.ioc.config.RuntimeBeanReference;
import com.tang.spring.frame.ioc.config.TypedStringValue;
import com.tang.spring.frame.ioc.factory.BeanFactory;

/**
 * 专门负责BeanDefinition中存储的value的转换
 */
public class BeanDefinitionValueResolver {

    private BeanFactory beanFactory;

    public BeanDefinitionValueResolver(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 将属性标签中的值转换成真实的值
     *
     * @param value 属性标签的值
     * @return 具体的值
     */
    public Object resoleValue(Object value) {
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
            return beanFactory.getBean(ref);
        }
        return null;
    }
}
