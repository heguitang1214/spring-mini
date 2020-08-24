package com.tang.spring.frame.ioc.resource;

import java.io.InputStream;

/**
 * 根据classpath路径下的配置文件路径，获取对应的资源
 *
 * @author heguitang
 */
public class ClasspathResource implements Resource {

    /**
     * 资源路径
     */
    private String location;

    public ClasspathResource(String location) {
        this.location = location;
    }

    @Override
    public InputStream getResource() {
        return this.getClass().getClassLoader().getResourceAsStream(location);
    }
}
