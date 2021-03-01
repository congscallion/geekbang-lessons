package com.slydm.thinking.in.spring.dependency.inject;

import com.slydm.thinking.in.spring.dependency.inject.annotation.UserGroup;
import com.slydm.thinking.in.spring.dependency.inject.config.UserConfig;
import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * {@link org.springframework.beans.factory.annotation.Qualifier}  注解示例
 * <p>
 * 1. allUser 注入结果说明： 当不加载 /META-INF/dependency-import-ioc-overview.xml 文件时， allUser 中包含了
 *
 * @author wangcymy@gmail.com(wangcong) 2020/11/2 下午9:24
 * @Qualifier
 * @UserGroup 两个注解标的bean; 声明后，只包含 xml 文件中声明的bean.
 */
public class QualifierAnnotationDependencyInjectDemo {

  @Autowired
  private User user;

  @Autowired
  @Qualifier("user")
  private User namedUser;

  @Autowired
  private Collection<User> allUsers;

  @Autowired
  @Qualifier
  private Collection<User> qualifierUsers;

  @Autowired
  @UserGroup
  private Collection<User> userGroupUsers;


  public static void main(String[] args) {

// 创建 spring 应用上下文容器
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    applicationContext.register(QualifierAnnotationDependencyInjectDemo.class, UserConfig.class);
    XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(
        applicationContext);
    String location = "classpath:/META-INF/dependency-import-ioc-overview.xml";
    // 加载 xml 资源，解析生成 BeanDefinition
    xmlBeanDefinitionReader.loadBeanDefinitions(location);

    // 启动 spring 应用上下文
    applicationContext.refresh();

    QualifierAnnotationDependencyInjectDemo demo = applicationContext
        .getBean(QualifierAnnotationDependencyInjectDemo.class);

    System.out.println("demo.user = " + demo.user);
    System.out.println("demo.namedUser = " + demo.namedUser);
    System.out.println("demo.allUsers = " + demo.allUsers + ", size=" + demo.allUsers.size());
    System.out.println(
        "demo.qualifierUsers = " + demo.qualifierUsers + ", size=" + demo.qualifierUsers.size());
    System.out.println(
        "demo.userGroupUsers = " + demo.userGroupUsers + ", size=" + demo.userGroupUsers.size());

    // 关窗 spring 应用上下文
    applicationContext.close();

  }


}
