package com.slydm.thinking.in.spring.dependency.inject;

import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import java.util.Collection;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * {@link org.springframework.beans.factory.ObjectProvider}  完成延迟注入示例
 * <p>
 *
 * @author wangcymy@gmail.com(wangcong) 2020/11/2 下午9:24
 */
public class LazyAnnotationDependencyInjectDemo {

  @Autowired
  private User user;

  @Autowired
  private ObjectProvider<User> userObjectProvider;

  @Autowired
  private ObjectFactory<Collection<User>> userObjectFactory;


  public static void main(String[] args) {

// 创建 spring 应用上下文容器
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    applicationContext.register(LazyAnnotationDependencyInjectDemo.class);
    XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(
        applicationContext);
    String location = "classpath:/META-INF/dependency-import-ioc-overview.xml";
    // 加载 xml 资源，解析生成 BeanDefinition
    xmlBeanDefinitionReader.loadBeanDefinitions(location);

    // 启动 spring 应用上下文
    applicationContext.refresh();

    LazyAnnotationDependencyInjectDemo demo = applicationContext
        .getBean(LazyAnnotationDependencyInjectDemo.class);

    System.out.println("demo.userObjectProvider = " + demo.userObjectProvider.getObject());
    System.out.println("demo.userObjectFactory = " + demo.userObjectFactory.getObject());

    System.out.println();

    demo.userObjectProvider.forEach(System.out::println);

    // 关窗 spring 应用上下文
    applicationContext.close();

  }


}
