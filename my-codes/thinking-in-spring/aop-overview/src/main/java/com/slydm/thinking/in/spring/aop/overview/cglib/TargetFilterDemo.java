package com.slydm.thinking.in.spring.aop.overview.cglib;


import java.lang.reflect.Method;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.MethodCallback;
import org.springframework.util.ReflectionUtils.MethodFilter;

/**
 * AOP 目标过滤示例
 *
 * @author wangcymy@gmail.com(wangcong) 2021/2/19 9:19
 */
public class TargetFilterDemo {

  public static void main(String[] args) throws Exception {

    String targetClassName = "com.slydm.thinking.in.spring.aop.overview.cglib.helper.EchoService";

    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    Class<?> targetClass = classLoader.loadClass(targetClassName);

    // 通过方法名，参数列表类型查询方法
    Method targetMethod = ReflectionUtils.findMethod(targetClass, "echo", String.class);
    System.out.println(targetMethod);

    // 通过方法签名、抛出异常等信息查询方法并拦截
    ReflectionUtils.doWithMethods(targetClass, new MethodCallback() {

      @Override
      public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
        System.out.println("仅抛出 NullPointerException 方法为：" + method);

        // 通过反射执行过滤成的方法

      }
    }, new MethodFilter() {
      @Override
      public boolean matches(Method method) {
        Class[] parameterTypes = method.getParameterTypes();
        Class[] exceptionTypes = method.getExceptionTypes();

        return parameterTypes.length == 1
            && (parameterTypes[0]).isArray()
            && exceptionTypes.length == 1
            && NullPointerException.class.equals(exceptionTypes[0]);
      }
    });


  }


}
