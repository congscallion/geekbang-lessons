package slydm.geektimes.training.projects.config;

import javax.servlet.Filter;
import slydm.geektimes.training.web.servlet.support.AbstractDispatcherServletInitializer;

/**
 * spring
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/29 16:49
 */
public class MyWebApplicationInitializer extends AbstractDispatcherServletInitializer {

  @Override
  protected Filter[] getServletFilters() {
    return new Filter[0];
  }

  @Override
  protected String getServletMappings() {
    return "/";
  }

  @Override
  protected Class<?> getServletConfigClasses() {
    return UserWebConfig.class;
  }

}
