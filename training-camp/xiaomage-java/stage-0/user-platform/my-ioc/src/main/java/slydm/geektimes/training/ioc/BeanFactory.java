package slydm.geektimes.training.ioc;

import slydm.geektimes.training.exception.BeansException;

/**
 * ioc 容器接口，负责 bean的获取
 *
 * @author 72089101@vivo.com(wangcong) 2021/3/24 16:07
 */
public interface BeanFactory {

  /**
   * 从容器中获取指定名称的 bean
   *
   * @param name bean 名称
   * @return 该 名称称对应的 实例
   */
  Object getBean(String name) throws BeansException;

  /**
   * 获取 指定名称 指定类型的 bean
   *
   * @param name bean名称
   * @param requiredType bean 类型
   */
  <T> T getBean(String name, Class<T> requiredType) throws BeansException;

}
