package com.slydm.thinking.in.spring.dependency.inject;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * "byName" Autowiring 依赖 Setter 方法注入
 *
 * @author wangcymy@gmail.com(wangcong) 2020/10/29 下午10:21
 */
public class AutoWiringByNameDependencySetterInjectionDemo {

  public static void main(String[] args) {

    // 创建 spring 应用上下文容器
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(
        applicationContext);
    String location = "classpath:/META-INF/dependency-autowiring-setter-injection.xml";
    // 加载 xml 资源，解析生成 BeanDefinition
    xmlBeanDefinitionReader.loadBeanDefinitions(location);

    // 启动 spring 应用上下文
    applicationContext.refresh();

    UserHolder userHolder = applicationContext.getBean("userHolder", UserHolder.class);
    System.out.println(userHolder);

    userHolder = applicationContext.getBean("userHolder2", UserHolder.class);
    System.out.println(userHolder);

    // 关窗 spring 应用上下文
    applicationContext.close();

  }

}
