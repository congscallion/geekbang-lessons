package slydm.geektimes.training.projects.user.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import slydm.geektimes.training.projects.context.ComponentContext;


/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/10 21:52
 */
public class ComponentContextInitializerListener implements ServletContextListener {

  private ServletContext servletContext;

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {

    servletContext = servletContextEvent.getServletContext();
    ComponentContext context = new ComponentContext();
    context.init(servletContext);
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {

  }
}
