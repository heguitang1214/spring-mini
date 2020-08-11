package com.tang.spring.frame.ioc;

/**
 * PropertyValue 中封装着一个<property></property>标签的信息
 *
 * @author heguitang
 */
public class PropertyValue {

    private String name;

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
