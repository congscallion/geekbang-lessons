package slydm.geektimes.training.web.mvc.servlet;

import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.substringAfter;

import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.MethodInfo;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import org.apache.commons.lang.StringUtils;
import slydm.geektimes.training.context.ApplicationContext;
import slydm.geektimes.training.core.BeanDefinition;
import slydm.geektimes.training.core.BeanDefinitionRegistry;
import slydm.geektimes.training.ioc.ConfigurableListableBeanFactory;
import slydm.geektimes.training.web.annotation.Controller;
import slydm.geektimes.training.web.context.ConfigurableWebApplicationContext;
import slydm.geektimes.training.web.mvc.controller.PageController;
import slydm.geektimes.training.web.mvc.controller.RestController;
import slydm.geektimes.training.web.mvc.servlet.helper.HandlerMethodInfo;

/**
 * my web mvc 框架默认分发器
 *
 * @author wangcymy@gmail.com(wangcong) 3/4/21 10:04 PM
 * @see 1.0
 */
public class MyDispatcherServlet extends BaseServlet {

  private Logger logger;

  /**
   * 请求路径和 Controller 的映射关系缓存
   */
  private Map<String, Object> controllersMapping = new HashMap<>();

  /**
   * 请求路径和 {@link HandlerMethodInfo} 映射关系缓存
   */
  private Map<String, HandlerMethodInfo> handleMethodInfoMapping = new HashMap<>();

  private ApplicationContext applicationContext;

  public MyDispatcherServlet(ApplicationContext servletAppContext) {
    this.applicationContext = servletAppContext;
  }


  @Override
  public void init() throws ServletException {
    super.init();
    logger = Logger.getLogger(MyDispatcherServlet.class.getName());
    logger.setLevel(Level.FINEST);

    initWebApplicationContext();

    initDispatcher();
  }

  /**
   * 初始化 web 应该上下文
   */
  private void initWebApplicationContext() {
    ConfigurableWebApplicationContext cwa = (ConfigurableWebApplicationContext) applicationContext;

    cwa.setServletContext(getServletContext());
    cwa.setServletConfig(getServletConfig());

    cwa.refresh();
  }


  /**
   * 搜索所有 {@link Controller} 注解类
   */
  private void initDispatcher() {

    ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();

    String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
    for (String beanDefinitionName : beanDefinitionNames) {
      BeanDefinition beanDefinition = ((BeanDefinitionRegistry) beanFactory).getBeanDefinition(beanDefinitionName);
      if (!isController(beanDefinition)) {
        continue;
      }

      String requestPath = getAnnotationValue(beanDefinition.getClassAnnotationList(), Path.class, "value");

      beanDefinition.getAnnotationMethodList()
          .stream()
          .filter(methodInfo -> methodInfo.hasAnnotation(Path.class.getName()))
          .forEach(methodInfo -> {
            Set<String> supportedHttpMethods = findSupportedHttpMethods(methodInfo);

            String pathValueFromMethod = getAnnotationValue(methodInfo.getAnnotationInfo(), Path.class, "value");
            String temPath = requestPath + ROOT + pathValueFromMethod;
            temPath = temPath.replaceAll(ROOT2, ROOT);
            if (temPath.endsWith(ROOT)) {
              temPath = temPath.substring(0, temPath.length() - 1);
            }

            Method method = beanDefinition.getMethodByMethodInfo(methodInfo);
            handleMethodInfoMapping.put(temPath, new HandlerMethodInfo(temPath, method, supportedHttpMethods));
            controllersMapping.put(temPath, applicationContext.getBean(beanDefinitionName));
            logger.log(Level.INFO, "mapping " + temPath + " to " + method.getDeclaringClass());
          });
    }
  }


  /**
   * 获取指定注解的指定属性的值
   */
  private String getAnnotationValue(List<AnnotationInfo> annotationInfoList, Class<?> annotationClz,
      String propName) {

    return annotationInfoList
        .stream()
        .filter(annotationInfo -> annotationInfo.getName().equals(annotationClz.getName()))
        .findFirst()
        .map(annotationInfo -> annotationInfo.getParameterValues().getValue(propName).toString()).orElse("");
  }

  private boolean isController(BeanDefinition beanDefinition) {

    Optional<AnnotationInfo> first = beanDefinition.getClassAnnotationList().stream()
        .filter(annotationInfo -> annotationInfo.getName().equals(Controller.class.getName()))
        .findFirst();
    return first.map(annotation -> true).orElse(false);
  }

  private Set<String> findSupportedHttpMethods(MethodInfo methodInfo) {
    Set<String> supportedHttpMethods = new LinkedHashSet<>();

    if (methodInfo.hasAnnotation(HttpMethod.class.getName())) {
      String value = getAnnotationValue(methodInfo.getAnnotationInfo(), HttpMethod.class, "value");
      supportedHttpMethods.add(value);
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
    Object controller = controllersMapping.get(requestMappingPath);

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

          if (null != viewPath && !viewPath.toString().isEmpty()) {
            goToNext(viewPath.toString(), request, response);
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


  /**
   * 跳转到目标 jsp
   *
   * @param viewPath jsp 路径
   */
  private void goToNext(String viewPath, HttpServletRequest request, HttpServletResponse response) throws Exception {

    if (viewPath.startsWith(REDIRECT_PREFIX)) {
      String redirectPth = viewPath.substring(REDIRECT_PREFIX.length());
      redirect(response, redirectPth);
      return;
    }

    if (null != viewPath && !viewPath.startsWith("/")) {
      render(request, response, viewPath);
    } else if (null != viewPath && viewPath.startsWith("/")) {
      renderFromRoot(request, response, viewPath);
    }
  }

}
