package slydm.geektimes.training.context.event;

import java.util.LinkedHashSet;
import java.util.Set;
import slydm.geektimes.training.context.ApplicationEvent;
import slydm.geektimes.training.context.ApplicationListener;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/30 17:49
 */
public class SimpleApplicationEventMulticaster implements ApplicationEventMulticaster {

  public final Set<ApplicationListener<?>> applicationListeners = new LinkedHashSet<>();


  @Override
  public void addApplicationListener(ApplicationListener<?> listener) {
    applicationListeners.add(listener);
  }

  @Override
  public void multicastEvent(ApplicationEvent event) {
    invokeListener(event);
  }


  protected void invokeListener(ApplicationEvent event) {

    for (ApplicationListener applicationListener : applicationListeners) {
      applicationListener.onApplicationEvent(event);
    }

  }
}
