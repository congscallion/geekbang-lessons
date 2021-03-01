package com.slydm.thinking.in.spring.dependency.inject;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 基于 Java Api 依赖 Setter 方法注入示例
 *
 * @author wangcymy@gmail.com(wangcong) 2020/10/29 下午9:54
 */
public class ApiDependencySetterInjectionDemo {

  public static void main(String[] args) {

    // 创建 spring 应用上下文容器
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    // 注册 UserHolder 的 BeanDefinition
    BeanDefinition userHolderBeanDefinition = createUserHolderBeanDefinition();
    applicationContext.registerBeanDefinition("userHolder", userHolderBeanDefinition);

    XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(
        applicationContext);
    String location = "classpath:/META-INF/dependency-import-ioc-overview.xml";
    // 加载 xml 资源，解析生成 BeanDefinition
    xmlBeanDefinitionReader.loadBeanDefinitions(location);

    // 启动 spring 应用上下文
    applicationContext.refresh();

    UserHolder userHolder = applicationContext.getBean("userHolder", UserHolder.class);
    System.out.println(userHolder);

    // 关窗 spring 应用上下文
    applicationContext.close();

  }


  /**
   * {@link UserHolder} 生成 {@link BeanDefinition}
   */
  private static BeanDefinition createUserHolderBeanDefinition() {

    BeanDefinitionBuilder definitionBuilder = BeanDefinitionBuilder
        .genericBeanDefinition(UserHolder.class);

    definitionBuilder.addPropertyReference("user", "superUser");

    return definitionBuilder.getBeanDefinition();
  }


}
