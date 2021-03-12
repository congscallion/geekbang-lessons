package slydm.geektimes.training.projects.user.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.ArrayUtils;

/**
 * 字符编码 Filter
 *
 * @author wangcymy@gmail.com(wangcong) 3/4/21 10:49 PM
 */
public class LoginFilter implements Filter {

  private String[] excludes;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    excludes = filterConfig.getInitParameter("excludes").split(",");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    if (request instanceof HttpServletRequest) {
      HttpServletRequest httpRequest = (HttpServletRequest) request;

      String requestURI = httpRequest.getRequestURI();
      if (!ArrayUtils.contains(excludes, requestURI)
        && !requestURI.startsWith("/assets/")) {
        String requestedSessionId = httpRequest.getRequestedSessionId();
        Object user = httpRequest.getSession().getAttribute(requestedSessionId);
        if (null == user) {
          ((HttpServletResponse) response).sendRedirect("/");
          return;
        }
      }
    }

    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {
  }
}
