package slydm.geektimes.training.web.mvc;

import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.substringAfter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import org.apache.commons.lang.StringUtils;
import slydm.geektimes.training.web.mvc.controller.Controller;
import slydm.geektimes.training.web.mvc.controller.PageController;
import slydm.geektimes.training.web.mvc.controller.RestController;

/**
 * my web mvc 框架默认分发器
 *
 * @author wangcymy@gmail.com(wangcong) 3/4/21 10:04 PM
 * @see 1.0
 */
public class MyDispatcherServlet extends HttpServlet {

  /**
   * 请求路径和 Controller 的映射关系缓存
   */
  private Map<String, Controller> controllersMapping = new HashMap<>();

  /**
   * 请求路径和 {@link HandlerMethodInfo} 映射关系缓存
   */
  private Map<String, HandlerMethodInfo> handleMethodInfoMapping = new HashMap<>();


  @Override
  public void init() throws ServletException {
    super.init();
    initDispatcher();
  }


  private void initDispatcher() {

    for (Controller controller : ServiceLoader.load(Controller.class)) {

      Class<?> controllerClass = controller.getClass();
      Path pathFromClass = controllerClass.getAnnotation(Path.class);
      String requestPath = pathFromClass.value();

      Method[] publicMethods = controllerClass.getMethods();
      // 处理支持的 HTTP 方法的方法集合
      for (Method method : publicMethods) {

        Set<String> supportedHttpMethods = findSupportedHttpMethods(method);
        Path pathFromMethod = method.getAnnotation(Path.class);
        if (pathFromMethod != null) {
          String temPath = requestPath + "/" + pathFromMethod.value();
          temPath = temPath.replaceAll("//", "/");
          handleMethodInfoMapping.put(temPath,
              new HandlerMethodInfo(temPath, method, supportedHttpMethods));
        }

      }
      controllersMapping.put(requestPath, controller);
    }
  }

  private Set<String> findSupportedHttpMethods(Method method) {
    Set<String> supportedHttpMethods = new LinkedHashSet<>();
    for (Annotation annotationFromMethod : method.getAnnotations()) {
      HttpMethod httpMethod = annotationFromMethod.annotationType().getAnnotation(HttpMethod.class);
      if (httpMethod != null) {
        supportedHttpMethods.add(httpMethod.value());
      }
    }

    if (supportedHttpMethods.isEmpty()) {
      supportedHttpMethods.addAll(asList(HttpMethod.GET, HttpMethod.POST,
          HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.HEAD, HttpMethod.OPTIONS));
    }

    return supportedHttpMethods;
  }


  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // 请求路径
    String requestURI = request.getRequestURI();

    String servletContextPath = request.getContextPath();

    // context path
    String prefixPath = servletContextPath;

    // 映射路径（子路径）
    String requestMappingPath = substringAfter(requestURI,
        StringUtils.replace(prefixPath, "//", "/"));
    // 映射到 Controller
    Controller controller = controllersMapping.get(requestMappingPath);

    if (controller != null) {

      HandlerMethodInfo handlerMethodInfo = handleMethodInfoMapping.get(requestMappingPath);

      try {

        String httpMethod = request.getMethod();
        if (!handlerMethodInfo.getSupportedHttpMethods().contains(httpMethod)) {
          // HTTP 方法不支持
          response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
          return;
        }

        if (controller instanceof PageController) {

          PageController pageController = PageController.class.cast(controller);
          String viewPath = pageController.execute(request, response);
          ServletContext servletContext = request.getServletContext();
          if (!viewPath.startsWith("/")) {
            viewPath = "/" + viewPath;
          }
          // ServletContext -> RequestDispatcher 必须以 "/" 开头
          RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(viewPath);
          requestDispatcher.forward(request, response);

        } else if (controller instanceof RestController) {
          // TODO
        }


      } catch (Throwable throwable) {
        if (throwable.getCause() instanceof IOException) {
          throw (IOException) throwable.getCause();
        } else {
          throw new ServletException(throwable.getCause());
        }
      }

    }

  }
}
