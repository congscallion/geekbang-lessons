package slydm.geektimes.training.context;

import java.util.EventListener;

/**
 * 应用监听器
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/30 17:41
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

  /**
   * 发布事件
   */
  void onApplicationEvent(E event);
  
}
