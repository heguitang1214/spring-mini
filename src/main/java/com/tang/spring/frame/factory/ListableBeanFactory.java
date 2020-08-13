package com.tang.spring.frame.factory;

import java.util.List;

/**
 * 对于Bean容器中的Bean可以进行集合（批量）操作
 *
 * @author heguitang
 */
public interface ListableBeanFactory extends BeanFactory {
    /**
     * 可以根据指定类型回去它或者它实现类的对象
     *
     * @param type 类型
     * @return bean集合
     */
    List<Object> getBeansByType(Class type);
}
