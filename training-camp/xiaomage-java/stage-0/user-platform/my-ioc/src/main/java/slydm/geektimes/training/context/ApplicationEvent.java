package slydm.geektimes.training.context;

import java.util.EventObject;

/**
 * 扩展java 标准的事件对象
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/30 17:36
 */
public abstract class ApplicationEvent extends EventObject {

  /**
   * 抽象的事件对象
   */
  public ApplicationEvent(Object source) {
    super(source);
  }
}
