package slydm.geektimes.training.projects.user.web.controller;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import org.apache.commons.lang.StringUtils;
import slydm.geektimes.training.web.mvc.controller.Controller;
import slydm.geektimes.training.web.mvc.controller.PageController;
import slydm.geektimes.training.web.mvc.controller.RestController;
import slydm.geektimes.training.web.mvc.servlet.BaseServlet;
import slydm.geektimes.training.web.mvc.servlet.helper.HandlerMethodInfo;

/**
 * my web mvc 框架默认分发器
 *
 * @author wangcymy@gmail.com(wangcong) 3/4/21 10:04 PM
 * @see 1.0
 */
//@WebServlet(name = "myDispatcherServlet", urlPatterns = "/", loadOnStartup = 1)
public class MyDispatcherServlet2 extends BaseServlet {

  private Logger logger;

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
    logger = Logger.getLogger(MyDispatcherServlet2.class.getName());
    logger.setLevel(Level.FINEST);
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
          String temPath = requestPath + ROOT + pathFromMethod.value();
          temPath = temPath.replaceAll(ROOT2, ROOT);
          if (temPath.endsWith(ROOT)) {
            temPath = temPath.substring(0, temPath.length() - 1);
          }

          handleMethodInfoMapping.put(temPath,
              new HandlerMethodInfo(temPath, method, supportedHttpMethods));
          controllersMapping.put(temPath, controller);
          logger.log(Level.INFO, "mapping " + temPath + " to " + controllerClass.getSimpleName());
        }

      }
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
        StringUtils.replace(prefixPath, ROOT2, ROOT));
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

          Object viewPath = handlerMethodInfo.getHandlerMethod()
              .invoke(pageController, new Object[]{request, response});

          if (null != viewPath) {
            render(request, response, viewPath.toString());
          }
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

    } else {
      render(request, response, "error/404");
    }

  }
}
