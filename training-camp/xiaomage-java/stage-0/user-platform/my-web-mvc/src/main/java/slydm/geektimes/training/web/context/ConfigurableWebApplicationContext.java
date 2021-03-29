package slydm.geektimes.training.web.context;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import slydm.geektimes.training.context.ConfigurableApplicationContext;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/29 17:53
 */
public interface ConfigurableWebApplicationContext extends WebApplicationContext, ConfigurableApplicationContext {

  /**
   * Set the ServletContext for this web application context.
   * <p>Does not cause an initialization of the context: refresh needs to be
   * called after the setting of all configuration properties.
   *
   * @see #refresh()
   */
  void setServletContext(ServletContext servletContext);

  /**
   * Set the ServletConfig for this web application context.
   * Only called for a WebApplicationContext that belongs to a specific Servlet.
   *
   * @see #refresh()
   */
  void setServletConfig(ServletConfig servletConfig);

  /**
   * Return the ServletConfig for this web application context, if any.
   */
  ServletConfig getServletConfig();


}
