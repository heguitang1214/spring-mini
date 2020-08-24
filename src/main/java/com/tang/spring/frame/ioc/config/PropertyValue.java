package com.tang.spring.frame.ioc.config;

/**
 * PropertyValue 中封装着一个<property></property>标签的信息
 *
 * @author heguitang
 */
public class PropertyValue {

    private String name;

    /**
     * 可能是一个具体的值，也可能是ref，引用的一个对象属性
     */
    private Object value;

    public PropertyValue(String name, Object value) {
        super();
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
