package slydm.geektimes.training.projects.user.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 字符编码 Filter
 *
 * @author wangcymy@gmail.com(wangcong) 3/4/21 10:49 PM
 */
@WebFilter(filterName = "encodingFilter", urlPatterns = "/*", initParams = {
    @WebInitParam(name = "encoding", value = "utf-8")
})
public class CharsetEncodingFilter implements Filter {

  private String encoding = null;
  private ServletContext servletContext;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    this.encoding = filterConfig.getInitParameter("encoding");
    this.servletContext = filterConfig.getServletContext();
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    if (request instanceof HttpServletRequest) {
      HttpServletRequest httpRequest = (HttpServletRequest) request;
      HttpServletResponse httpResponse = (HttpServletResponse) response;
      httpRequest.setCharacterEncoding(encoding);
      httpResponse.setCharacterEncoding(encoding);
      //servletContext.log(((HttpServletRequest) request).getRequestURI() + ",当前编码已设置为：" + encoding);
    }

    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {

  }
}
