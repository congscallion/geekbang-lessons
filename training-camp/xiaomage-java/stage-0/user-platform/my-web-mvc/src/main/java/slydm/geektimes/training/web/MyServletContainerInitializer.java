package slydm.geektimes.training.web;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;

/**
 * {@link ServletContainerInitializer} 实例，该实例由 Servlet 容器在容器启动时通过 spi 的方式实例化，提供了 Servlet 容器之外的程序
 * 执行初始化工作的机会。比如：注入 Servlet \ Filter 等。
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/29 16:18
 */
@HandlesTypes(WebApplicationInitializer.class)
public class MyServletContainerInitializer implements ServletContainerInitializer {

  @Override
  public void onStartup(Set<Class<?>> webAppInitializerClasses, ServletContext servletContext) throws ServletException {

    List<WebApplicationInitializer> initializers = new LinkedList<>();

    if (webAppInitializerClasses != null) {
      for (Class<?> waiClass : webAppInitializerClasses) {
        // Be defensive: Some servlet containers provide us with invalid classes,
        // no matter what @HandlesTypes says...
        if (!waiClass.isInterface() && !Modifier.isAbstract(waiClass.getModifiers()) &&
            WebApplicationInitializer.class.isAssignableFrom(waiClass)) {
          try {
            initializers.add((WebApplicationInitializer) accessibleConstructor(waiClass).newInstance());
          } catch (Throwable ex) {
            throw new ServletException("Failed to instantiate WebApplicationInitializer class", ex);
          }
        }
      }
    }

    if (initializers.isEmpty()) {
      servletContext.log("No My WebApplicationInitializer types detected on classpath");
      return;
    }

    servletContext.log(initializers.size() + " My WebApplicationInitializers detected on classpath");
    for (WebApplicationInitializer initializer : initializers) {
      initializer.onStartup(servletContext);
    }

  }


  public static <T> Constructor<T> accessibleConstructor(Class<T> clazz, Class<?>... parameterTypes)
      throws NoSuchMethodException {

    Constructor<T> ctor = clazz.getDeclaredConstructor(parameterTypes);
    makeAccessible(ctor);
    return ctor;
  }

  public static void makeAccessible(Constructor<?> ctor) {
    if ((!Modifier.isPublic(ctor.getModifiers()) ||
        !Modifier.isPublic(ctor.getDeclaringClass().getModifiers())) && !ctor.isAccessible()) {
      ctor.setAccessible(true);
    }
  }
}
