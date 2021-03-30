package slydm.geektimes.training.context.event;

import slydm.geektimes.training.context.ApplicationContext;
import slydm.geektimes.training.context.ApplicationEvent;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/30 17:38
 */
public class ApplicationStartedEvent extends ApplicationEvent {

  private final ApplicationContext context;

  /**
   * 抽象的事件对象
   */
  public ApplicationStartedEvent(ApplicationContext context) {
    super("started");
    this.context = context;
  }

}
