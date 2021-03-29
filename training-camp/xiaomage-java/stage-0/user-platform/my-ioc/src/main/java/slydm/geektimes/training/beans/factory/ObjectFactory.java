package slydm.geektimes.training.beans.factory;

import slydm.geektimes.training.exception.BeansException;

/**
 * 本接口用于返回一个对象，用于在循环依赖中 Bean 未初始化完成但又需要被其它 Bean 依赖消耗时使用。
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/27 15:30
 */
@FunctionalInterface
public interface ObjectFactory<T> {

  /**
   * 返回 Bean 实例
   */
  T getObject() throws BeansException;

}
