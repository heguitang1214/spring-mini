package com.tang.spring.frame.ioc.reader;

import com.tang.spring.frame.ioc.config.*;
import com.tang.spring.frame.ioc.registry.BeanDefinitionRegistry;
import com.tang.spring.frame.utils.ReflectUtils;
import org.dom4j.Element;

import java.util.List;

/**
 * 针对Document对于的InputStream流对象进行解析
 * 解析XML文件
 *
 * @author heguitang
 */
public class XmlBeanDefinitionDocumentReader {

    private BeanDefinitionRegistry registry;

    public XmlBeanDefinitionDocumentReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    /**
     * 从根元素进行注册
     *
     * @param rootElement 根元素
     */
    public void registerBeanDefinitions(Element rootElement) {
        // 获取<bean>和自定义标签（比如mvc:interceptors）
        List<Element> elements = rootElement.elements();
        for (Element element : elements) {
            // 获取标签名称
            String name = element.getName();
            if (name.equals(Constant.BEAN)) {
                // 解析默认标签，其实也就是bean标签
                parseDefaultElement(element);
            } else {
                // 解析自定义标签，比如aop:aspect标签
                parseCustomElement(element);
            }
        }
    }

    /**
     * 默认标签的处理
     *
     * @param beanElement 元素标签
     */
    private void parseDefaultElement(Element beanElement) {
        try {
            if (beanElement == null) {
                return;
            }
            // 获取id属性
            String id = beanElement.attributeValue(Constant.ID);

            // 获取name属性
            String name = beanElement.attributeValue(Constant.NAME);

            // 获取class属性
            String clazzName = beanElement.attributeValue(Constant.ATTR_CLASS);
            if (clazzName == null || "".equals(clazzName)) {
                return;
            }

            // 获取init-method属性
            String initMethod = beanElement.attributeValue(Constant.INIT_METHOD);
            // 获取scope属性
            String scope = beanElement.attributeValue(Constant.SCOPE);
//            scope = scope != null && !scope.equals("") ? scope : "singleton";
            if (scope == null || "".equals(scope)) {
                scope = Constant.SINGLETON;
            }

            // 获取beanName
            String beanName = id == null ? name : id;
            Class<?> clazzType = Class.forName(clazzName);
            beanName = beanName == null ? clazzType.getSimpleName() : beanName;
            // 创建BeanDefinition对象
            // 此次可以使用构建者模式进行优化
            BeanDefinition beanDefinition = new BeanDefinition(clazzName, beanName);
            beanDefinition.setInitMethod(initMethod);
            beanDefinition.setScope(scope);
            // 获取property子标签集合
            List<Element> propertyElements = beanElement.elements();
            for (Element propertyElement : propertyElements) {
                parsePropertyElement(beanDefinition, propertyElement);
            }

            // 注册BeanDefinition信息
            registry.registerBeanDefinition(beanName, beanDefinition);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析标签属性，然后将其赋值给bean的定义实体
     *
     * @param beanDefination  bean的定义
     * @param propertyElement 标签属性
     */
    private void parsePropertyElement(BeanDefinition beanDefination, Element propertyElement) {
        if (propertyElement == null) {
            return;
        }

        // 获取name属性
        String name = propertyElement.attributeValue(Constant.NAME);
        // 获取value属性
        String value = propertyElement.attributeValue(Constant.VALUE);
        // 获取ref属性
        String ref = propertyElement.attributeValue(Constant.REF);

        // 如果value和ref都有值，则返回
        if (value != null && !"".equals(value) && ref != null && !"".equals(ref)) {
            return;
        }

        // PropertyValue就封装着一个property标签的信息
        PropertyValue pv = null;

        if (value != null && !"".equals(value)) {
            // 因为spring配置文件中的value是String类型，而对象中的属性值是各种各样的，所以需要存储类型
            TypedStringValue typeStringValue = new TypedStringValue(value);

            Class<?> targetType = ReflectUtils.getTypeByFieldName(beanDefination.getClazzName(), name);
            typeStringValue.setTargetType(targetType);

            pv = new PropertyValue(name, typeStringValue);
            beanDefination.addPropertyValue(pv);
        } else if (ref != null && !"".equals(ref)) {
            RuntimeBeanReference reference = new RuntimeBeanReference(ref);
            pv = new PropertyValue(name, reference);
            beanDefination.addPropertyValue(pv);
        } else {
            // todo 其他类型的处理
            System.out.println("其他类型的处理");
            return;
        }
    }

    /**
     * 自定义标签的解析流程
     *
     * @param element 元素信息
     */
    private void parseCustomElement(Element element) {
        // AOP、TX、MVC标签的解析，都是走该流程
    }

}
