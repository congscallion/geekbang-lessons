package slydm.geektimes.training.context.annotation;

import slydm.geektimes.training.beans.factory.BeanPostProcessor;
import slydm.geektimes.training.exception.BeansException;

/**
 * 提供 bean 销毁时机的回调
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/29 9:42
 */
public interface DestructionAwareBeanPostProcessor extends BeanPostProcessor {


  /**
   * bean 注销时的 callback
   */
  void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException;

}
