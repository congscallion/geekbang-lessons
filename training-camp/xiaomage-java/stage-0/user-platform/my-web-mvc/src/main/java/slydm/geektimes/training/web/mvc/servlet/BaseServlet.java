package slydm.geektimes.training.web.mvc.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求处理辅助类
 *
 * @author wangcymy@gmail.com(wangcong) 3/9/21 17:45 PM
 * @see 1.0
 */
public class BaseServlet extends HttpServlet {

  private static final long serialVersionUID = 632349127293785744L;
  protected static final String ROOT = "/";
  protected static final String ROOT2 = "//";
  private static final String VIEW_PREFIX = "/WEB-INF/templates/";
  private static final String VIEW_SUFFIX = ".jsp";

  protected void render(HttpServletRequest request, HttpServletResponse response, String template)
      throws ServletException, IOException {

    String viewPath = VIEW_PREFIX + template + VIEW_SUFFIX;
    if (!viewPath.startsWith(ROOT)) {
      viewPath = ROOT + viewPath;
    }
    getServletContext().getRequestDispatcher(viewPath).forward(request, response);
  }


  protected void renderFromRoot(HttpServletRequest request, HttpServletResponse response, String template)
      throws ServletException, IOException {
    getServletContext().getRequestDispatcher(template).forward(request, response);
  }


  public void redirect(HttpServletResponse response, String path) throws IOException {
    response.sendRedirect(path);
  }

}
