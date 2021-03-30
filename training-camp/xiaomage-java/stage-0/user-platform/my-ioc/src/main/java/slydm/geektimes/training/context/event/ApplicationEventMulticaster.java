package slydm.geektimes.training.context.event;

import slydm.geektimes.training.context.ApplicationEvent;
import slydm.geektimes.training.context.ApplicationListener;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/30 17:46
 */
public interface ApplicationEventMulticaster {

  /**
   * 添加一个监听者，当发布事件时，将会收到事件
   *
   * @param listener the listener to add
   */
  void addApplicationListener(ApplicationListener<?> listener);


  /**
   * 广播事件
   */
  void multicastEvent(ApplicationEvent event);

}
