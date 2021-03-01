package com.slydm.thinking.in.spring.bean.lifecycle;

import com.slydm.thinking.in.spring.ioc.overview.domain.SuperUser;
import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * BeanDefinition 合并示例
 *
 * 默认 BeanDefinition 是 {@link GenericBeanDefinition}
 *
 * BeanDefinition merge 之后，会变成: {@link RootBeanDefinition}
 *
 * 当一个 BeanDefinition 存在 parent 时， 合并时，会递归加载 parent BeanDefinition.
 * 之后将 parent BeanDefinition 的属性复制到子 BeanDefinition.
 *
 * @author wangcymy@gmail.com(wangcong) 2020/12/13 下午1:04
 */
public class MergeBeanDefinitionDemo {

  public static void main(String[] args) {

    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

    XmlBeanDefinitionReader definitionReader = new XmlBeanDefinitionReader(beanFactory);

    // 加载 xml 资源文件
    String location = "META-INF/dependency-lookup-context.xml";
    int beanDefinitions = definitionReader.loadBeanDefinitions(location);

    System.out.println("已加载 BeanDefinition 数量: " + beanDefinitions);

    User user = beanFactory.getBean("user", User.class);
    System.out.println(user);

    SuperUser superUser = beanFactory.getBean("superUser", SuperUser.class);
    System.out.println(superUser);

  }

}
