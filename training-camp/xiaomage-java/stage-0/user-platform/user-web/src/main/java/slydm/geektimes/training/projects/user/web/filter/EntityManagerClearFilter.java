package slydm.geektimes.training.projects.user.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import slydm.geektimes.training.projects.user.orm.jpa.EntityManagerHolder;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/17 23:32
 */
public class EntityManagerClearFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    System.out.println("EntityManagerClearFilter...");
    filterChain.doFilter(servletRequest, servletResponse);
    EntityManagerHolder.clear();
  }

  @Override
  public void destroy() {

  }
}
