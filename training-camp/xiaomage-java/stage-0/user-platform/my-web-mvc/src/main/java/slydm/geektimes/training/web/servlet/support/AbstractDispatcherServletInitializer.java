package slydm.geektimes.training.web.servlet.support;

import java.beans.Introspector;
import java.util.EnumSet;
import java.util.Map;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import slydm.geektimes.training.context.ApplicationContext;
import slydm.geektimes.training.util.Assert;
import slydm.geektimes.training.util.StringUtils;
import slydm.geektimes.training.web.WebApplicationInitializer;
import slydm.geektimes.training.web.context.AnnotationConfigWebApplicationContext;
import slydm.geektimes.training.web.mvc.servlet.MyDispatcherServlet;

/**
 * 抽象的 {@link WebApplicationInitializer} 实现，完成基础的容器初始化工作，方便使用。
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/29 17:05
 */
public abstract class AbstractDispatcherServletInitializer implements WebApplicationInitializer {

  /**
   * web mvc 分发器默认的名称
   */
  public static final String DEFAULT_SERVLET_NAME = "dispatcher";


  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    registerDispatcherServlet(servletContext);
  }

  /**
   * 启动ioc容器，并注册 web mvc 分发器 servlet
   */
  protected void registerDispatcherServlet(ServletContext servletContext) {
    String servletName = getServletName();
    Assert.hasLength(servletName, "getServletName() must not return null or empty");

    ApplicationContext servletAppContext = createServletApplicationContext();
    Assert.notNull(servletAppContext, "createServletApplicationContext() must not return null");

    MyDispatcherServlet dispatcherServlet = createDispatcherServlet(servletAppContext);
    Assert.notNull(dispatcherServlet, "createDispatcherServlet(WebApplicationContext) must not return null");

    ServletRegistration.Dynamic registration = servletContext.addServlet(servletName, dispatcherServlet);
    if (registration == null) {
      throw new IllegalStateException("Failed to register servlet with name '" + servletName + "'. " +
          "Check if there is another servlet registered under the same name.");
    }

    registration.setLoadOnStartup(1);
    registration.addMapping(getServletMappings());
    registration.setAsyncSupported(isAsyncSupported());

    FilterMapping[] filters = getServletFilters();
    if (null != filters && filters.length != 0) {
      for (FilterMapping filter : filters) {
        registerServletFilter(servletContext, filter);
      }
    }

  }

  /**
   * 向 Servlet 容器中添加 Filter
   */
  protected FilterRegistration.Dynamic registerServletFilter(ServletContext servletContext, FilterMapping filter) {

    String filterName = filter.getName();
    if (!StringUtils.hasText(filter.getName())) {
      filterName = Introspector.decapitalize(filter.getClass().getSimpleName());
    }

    Dynamic registration = servletContext.addFilter(filterName, filter.getFilter());

    if (registration == null) {
      int counter = 0;
      while (registration == null) {
        if (counter == 100) {
          throw new IllegalStateException("Failed to register filter with name '" + filterName + "'. " +
              "Check if there is another filter registered under the same name.");
        }
        registration = servletContext.addFilter(filterName + "#" + counter, filter.getFilter());
        counter++;
      }
    }

    registration.setAsyncSupported(isAsyncSupported());

    if (filter.getInitParameters() != null && !filter.getInitParameters().isEmpty()) {
      Map<String, String> initParameters = registration.getInitParameters();
      filter.getInitParameters().putAll(initParameters);
      registration.setInitParameters(filter.getInitParameters());
    }
    registration.addMappingForUrlPatterns(null == filter.getDispatcherTypes() ?
            getDispatcherTypes() : filter.getDispatcherTypes(),
        false, filter.getUrlPatterns()
    );
    return registration;
  }

  private EnumSet<DispatcherType> getDispatcherTypes() {
    return (isAsyncSupported() ?
        EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ASYNC) :
        EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE));
  }

  protected abstract FilterMapping[] getServletFilters();

  protected abstract String getServletMappings();

  protected boolean isAsyncSupported() {
    return true;
  }

  protected MyDispatcherServlet createDispatcherServlet(ApplicationContext servletAppContext) {
    return new MyDispatcherServlet(servletAppContext);
  }

  /**
   * 创建 my ioc 应用上下文,并注册配置类
   */
  protected ApplicationContext createServletApplicationContext() {
    AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
    Class<?> configClasses = getServletConfigClasses();
    if (configClasses != null) {
      context.register(configClasses);
    }
    return context;
  }

  /**
   * 获取配置类, 这里只搞一个配置类
   */
  protected abstract Class<?> getServletConfigClasses();


  protected String getServletName() {
    return DEFAULT_SERVLET_NAME;
  }


  protected static class FilterMapping {

    public FilterMapping() {

    }

    private Filter filter;
    private String name;
    private String[] urlPatterns;
    private EnumSet<DispatcherType> dispatcherTypes;
    private Map<String, String> initParameters;

    public Filter getFilter() {
      return filter;
    }

    public void setFilter(Filter filter) {
      this.filter = filter;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String[] getUrlPatterns() {
      return urlPatterns;
    }

    public void setUrlPatterns(String... urlPatterns) {
      this.urlPatterns = urlPatterns;
    }

    public EnumSet<DispatcherType> getDispatcherTypes() {
      return dispatcherTypes;
    }

    public void setDispatcherTypes(EnumSet<DispatcherType> dispatcherTypes) {
      this.dispatcherTypes = dispatcherTypes;
    }

    public Map<String, String> getInitParameters() {
      return initParameters;
    }

    public void setInitParameters(Map<String, String> initParameters) {
      this.initParameters = initParameters;
    }
  }
}
