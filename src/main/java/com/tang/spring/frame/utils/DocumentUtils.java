package com.tang.spring.frame.utils;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * 文档工具类
 */
public class DocumentUtils {
    public static Document getDocument(InputStream inputStream) {
        try {
            SAXReader reader = new SAXReader();
            return reader.read(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
