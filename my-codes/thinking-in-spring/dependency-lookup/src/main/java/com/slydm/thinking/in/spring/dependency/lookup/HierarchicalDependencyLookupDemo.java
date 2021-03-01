package com.slydm.thinking.in.spring.dependency.lookup;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 层次性依赖查找示例
 * <p>
 * 层次查找<br>
 *
 * @author wangcymy@gmail.com(wangcong) 2020/10/27 下午10:25
 * @see {@link HierarchicalBeanFactory#containsLocalBean(String)}
 * <p>
 * 根据 Bean 类型查找实例列表<br>
 * @see {@link org.springframework.beans.factory.BeanFactoryUtils#beansOfTypeIncludingAncestors(ListableBeanFactory,
 * Class)}
 * @see {@link org.springframework.beans.factory.BeanFactoryUtils#beanOfType(ListableBeanFactory,
 * Class)}
 * <p>
 * 根据 Java 注解查找名称列表<br>
 * @see {@link org.springframework.beans.factory.BeanFactoryUtils#beanNamesForTypeIncludingAncestors(ListableBeanFactory,
 * Class)}
 */
public class HierarchicalDependencyLookupDemo {

  public static void main(String[] args) {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    applicationContext.register(HierarchicalDependencyLookupDemo.class);

    ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
    System.out
        .println("当前 BeanFactory 的 Parent BeanFactory : " + beanFactory.getParentBeanFactory());

    HierarchicalBeanFactory parentBeanFactory = createParentBeanFactory();
    beanFactory.setParentBeanFactory(parentBeanFactory);
    System.out
        .println("当前 BeanFactory 的 Parent BeanFactory : " + beanFactory.getParentBeanFactory());

    applicationContext.refresh();

    displayLocalBean(beanFactory, "user");
    displayLocalBean(parentBeanFactory, "user");

    displayContainsBean(beanFactory, "user");
    displayContainsBean(parentBeanFactory, "user");

    applicationContext.close();

  }

  private static void displayLocalBean(HierarchicalBeanFactory beanFactory, String beanName) {

    System.out
        .printf("当前 BeanFactory[%s] 是否包含 Local bean[name: %s] : %s%n", beanFactory, beanFactory,
            beanFactory.containsLocalBean(beanName));

  }


  private static void displayContainsBean(HierarchicalBeanFactory beanFactory, String beanName) {

    System.out.printf("当前 BeanFactory[%s] 是否包含 bean[name: %s] : %s%n", beanFactory, beanFactory,
        containsBean(beanFactory, beanName));

  }


  private static boolean containsBean(HierarchicalBeanFactory beanFactory, String beanName) {

    BeanFactory parentBeanFactory = beanFactory.getParentBeanFactory();

    if (parentBeanFactory instanceof HierarchicalBeanFactory) {

      HierarchicalBeanFactory hierarchicalParentBeanFactory = (HierarchicalBeanFactory) parentBeanFactory;

      return containsBean(hierarchicalParentBeanFactory, beanName);

    }

    return beanFactory.containsLocalBean(beanName);


  }


  public static HierarchicalBeanFactory createParentBeanFactory() {

    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
    XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
    String location = "classpath:/META-INF/dependency-lookup-context.xml";
    xmlBeanDefinitionReader.loadBeanDefinitions(location);

    return beanFactory;
  }

}
