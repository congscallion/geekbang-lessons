package slydm.geektimes.training.beans.factory;

import slydm.geektimes.training.exception.BeansException;
import slydm.geektimes.training.ioc.BeanFactory;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/27 17:17
 */
public interface BeanFactoryAware extends Aware {

  /**
   * 注入 {@link BeanFactory } 实例的 callback
   */
  void setBeanFactory(BeanFactory beanFactory) throws BeansException;

}
