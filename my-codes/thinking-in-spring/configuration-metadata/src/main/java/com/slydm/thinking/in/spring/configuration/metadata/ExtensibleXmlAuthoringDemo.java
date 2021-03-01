package com.slydm.thinking.in.spring.configuration.metadata;

import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * Spring XML 元素扩展示例
 *
 * <p>
 * <br> 1. 先定义 .xsd 文件， 描述需要创建的 Bean
 * <br> 2. 创建 {@link UsersNamespaceHandler} 用于处理 xml 文件与之关联的 namespace下的元素
 * <br> 3. 创建 {@link UserBeanDefinitionParser} 用于解析 namespace 下元素并转换成 {@link BeanDefinitionBuilder}
 * <br> 4. 创建 spring.handlers 文件， 将 xsd 中的 namespace 与 Handler 关联
 * <br> 5. 创建 spring.schemas 文件，将 xsd 中的　namespace 与 xsd 文件路径关联，避免从 namespace指定的在线地址获取
 * <br> 6. 在 content xml文件中引入xsd定义的namespace,即可使用
 *
 * @author wangcymy@gmail.com(wangcong) 2020/12/28 11:44
 */
public class ExtensibleXmlAuthoringDemo {

  public static void main(String[] args) {

    // 创建 IoC 底层容器
    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
    // 创建 XML 资源的 BeanDefinitionReader
    XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
    // 记载 XML 资源
    reader.loadBeanDefinitions("META-INF/users-context.xml");
    // 获取 User Bean 对象
    User user = beanFactory.getBean(User.class);
    System.out.println(user);


  }


}
