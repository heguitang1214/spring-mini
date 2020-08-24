package com.tang.spring.frame.ioc.resource;

import java.io.InputStream;

/**
 * 获取资源接口
 *
 * @author heguitang
 */
public interface Resource {

    /**
     * 获取资源
     *
     * @return 返回对应的流资源
     */
    InputStream getResource();
}
