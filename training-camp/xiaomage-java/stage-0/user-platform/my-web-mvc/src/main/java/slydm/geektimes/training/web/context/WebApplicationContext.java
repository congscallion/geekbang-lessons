package slydm.geektimes.training.web.context;

import javax.servlet.ServletContext;
import slydm.geektimes.training.context.ApplicationContext;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/29 17:51
 */
public interface WebApplicationContext extends ApplicationContext {


  /**
   * Return the standard Servlet API ServletContext for this application.
   */
  ServletContext getServletContext();

}
