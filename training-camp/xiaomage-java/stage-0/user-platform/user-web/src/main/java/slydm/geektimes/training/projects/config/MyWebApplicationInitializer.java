package slydm.geektimes.training.projects.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slydm.geektimes.training.projects.user.web.filter.CharsetEncodingFilter;
import slydm.geektimes.training.projects.user.web.filter.EntityManagerClearFilter;
import slydm.geektimes.training.projects.user.web.filter.LoginFilter;
import slydm.geektimes.training.web.servlet.support.AbstractDispatcherServletInitializer;

/**
 * spring
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/29 16:49
 */
public class MyWebApplicationInitializer extends AbstractDispatcherServletInitializer {

  @Override
  protected FilterMapping[] getServletFilters() {
    List<FilterMapping> filterList = new ArrayList<>();

    // add CharsetEncodingFilter
    FilterMapping encodingFilter = new FilterMapping();
    encodingFilter.setFilter(new CharsetEncodingFilter());
    encodingFilter.setName("encodingFilter");
    encodingFilter.setUrlPatterns("/*");
    Map<String, String> initP = new HashMap<>();
    initP.put("encoding", "utf-8");
    encodingFilter.setInitParameters(initP);
    filterList.add(encodingFilter);

    // add LoginFilter
    FilterMapping loginFilter = new FilterMapping();
    loginFilter.setFilter(new LoginFilter());
    loginFilter.setName("LoginFilter");
    initP = new HashMap<>();
    initP.put("excludes", "/,/users/register,/users/login");
    loginFilter.setInitParameters(initP);
    filterList.add(loginFilter);

    // add EntityManagerClearFilter
    FilterMapping entityManagerClearFilter = new FilterMapping();
    entityManagerClearFilter.setFilter(new EntityManagerClearFilter());
    entityManagerClearFilter.setName("EntityManagerClearFilter");
    entityManagerClearFilter.setUrlPatterns("/*");
    filterList.add(entityManagerClearFilter);

    return filterList.toArray(new FilterMapping[0]);
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
