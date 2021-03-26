package slydm.geektimes.training.core;

import slydm.geektimes.training.exception.NoSuchBeanDefinitionException;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/19 17:11
 */
public interface BeanDefinitionRegistry {


  /**
   * 向容器 IOC 容器注册 Bean
   *
   * @param beanName bean 的名称
   * @param beanDefinition bean描述信息
   */
  void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

  /**
   * 通过bean名称 获取 {@link BeanDefinition}
   */
  BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;


  /**
   * 判断 容器中是否存在指定名称的bean
   *
   * @param beanName bean名称
   * @return true, 存在；false，不存在
   */
  boolean containsBeanDefinition(String beanName);

}
