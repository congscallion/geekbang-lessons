package slydm.geektimes.training.web.context;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import slydm.geektimes.training.context.AnnotationConfigApplicationContext;
import slydm.geektimes.training.context.ApplicationContext;

/**
 * 用于 web 环境的 {@link ApplicationContext} 实例
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/29 16:13
 */
public class AnnotationConfigWebApplicationContext extends AnnotationConfigApplicationContext
    implements ConfigurableWebApplicationContext {

  @Override
  public void setServletContext(ServletContext servletContext) {

  }

  @Override
  public void setServletConfig(ServletConfig servletConfig) {

  }

  @Override
  public ServletConfig getServletConfig() {
    return null;
  }

  @Override
  public ServletContext getServletContext() {
    return null;
  }
}
