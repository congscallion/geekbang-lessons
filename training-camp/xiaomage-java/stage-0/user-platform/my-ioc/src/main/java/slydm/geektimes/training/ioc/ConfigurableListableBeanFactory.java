package slydm.geektimes.training.ioc;

import slydm.geektimes.training.exception.BeansException;

/**
 * @author 72089101@vivo.com(wangcong) 2021/3/24 16:15
 */
public interface ConfigurableListableBeanFactory extends BeanFactory {

  /**
   * 判断 容器中是否存在指定名称的bean
   *
   * @param beanName bean名称
   * @return true, 存在；false，不存在
   */
  boolean containsBeanDefinition(String beanName);


  /**
   * 初始化容器中所有bean
   */
  void preInstantiateSingletons() throws BeansException;


}
