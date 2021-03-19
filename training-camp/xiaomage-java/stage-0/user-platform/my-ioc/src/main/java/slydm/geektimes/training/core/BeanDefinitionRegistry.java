package slydm.geektimes.training.core;

/**
 * @author 72089101@vivo.com(wangcong) 2021/3/19 17:11
 */
public interface BeanDefinitionRegistry {


  /**
   * 向容器 IOC 容器注册 Bean
   *
   * @param beanName bean 的名称
   * @param beanDefinition bean描述信息
   */
  void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);


}
