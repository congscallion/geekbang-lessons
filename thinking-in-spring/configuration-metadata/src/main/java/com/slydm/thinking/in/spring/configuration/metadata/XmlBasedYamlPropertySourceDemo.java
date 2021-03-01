package com.slydm.thinking.in.spring.configuration.metadata;


import java.util.Map;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * 基于 XML 资源的 YAML 外部化配置示例
 *
 * @author wangcymy@gmail.com(wangcong) 2020/12/28 10:59 AM
 */
public class XmlBasedYamlPropertySourceDemo {

  public static void main(String[] args) {
    // 创建 IoC 底层容器
    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
    // 创建 XML 资源的 BeanDefinitionReader
    XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
    // 记载 XML 资源
    reader.loadBeanDefinitions("classpath:/META-INF/yaml-property-source-context.xml");
    // 获取 Map YAML 对象
    Map<String, Object> yamlMap = beanFactory.getBean("yamlMap", Map.class);
    System.out.println(yamlMap);
  }

}