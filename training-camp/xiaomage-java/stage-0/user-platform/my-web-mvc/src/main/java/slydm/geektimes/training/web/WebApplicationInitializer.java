package slydm.geektimes.training.web;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * web 应用上下文启动钩子，由 Servlet 容器启动化时，通过 spi 方式加载  {@link ServletContainerInitializer} 实例时执行。
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/29 16:19
 */
public interface WebApplicationInitializer {

  void onStartup(ServletContext servletContext) throws ServletException;


}
