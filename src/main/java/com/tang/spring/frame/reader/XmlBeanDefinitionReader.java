package com.tang.spring.frame.reader;

import com.tang.spring.frame.registry.BeanDefinitionRegistry;
import com.tang.spring.frame.utils.DocumentUtils;
import org.dom4j.Document;

import java.io.InputStream;

/**
 * 针对XML对于的InputStream流对象进行解析
 *
 * @author heguitang
 */
public class XmlBeanDefinitionReader {

    private BeanDefinitionRegistry registry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void loadBeanDefinitions(InputStream inputStream) {
        // 创建文档对象
        Document document = DocumentUtils.getDocument(inputStream);
        XmlBeanDefinitionDocumentReader documentReader = new XmlBeanDefinitionDocumentReader(registry);
        // 对document中包含的元素，进行注册
        documentReader.registerBeanDefinitions(document.getRootElement());
    }
}
