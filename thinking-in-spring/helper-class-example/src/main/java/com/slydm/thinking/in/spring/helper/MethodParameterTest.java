package com.slydm.thinking.in.spring.helper;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import org.springframework.core.MethodParameter;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/2/3 11:01
 */
public class MethodParameterTest {

  public int method(String p1, long p2) {
    return 42;
  }

  @SuppressWarnings("unused")
  private static class NestedClass {

    NestedClass(@Param String s) {
    }
  }

  @SuppressWarnings("unused")
  private class InnerClass {

    public InnerClass(@Param String s, Callable<Integer> i) {
    }
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.PARAMETER)
  private @interface Param {

  }

  @SuppressWarnings("serial")
  private static class StringList extends ArrayList<String> {

  }

  @SuppressWarnings("serial")
  private static class IntegerList extends ArrayList<Integer> {

  }

  public static void main(String[] args) throws Exception {

    Method method = MethodParameterTest.class.getMethod("method", String.class, Long.TYPE);
    MethodParameter methodParameter = MethodParameter.forExecutable(method, 0);
    printMethodParameter(methodParameter);

    Constructor<NestedClass> nestedClassConstructor = NestedClass.class
        .getDeclaredConstructor(String.class);
    MethodParameter nestedClassConstructorParameter = MethodParameter
        .forExecutable(nestedClassConstructor, 0);
    printMethodParameter(nestedClassConstructorParameter);

    Constructor<InnerClass> innerClassConstructor = InnerClass.class
        .getConstructor(MethodParameterTest.class, String.class, Callable.class);
    MethodParameter innerClassConstructorParameterOf0 = MethodParameter
        .forExecutable(innerClassConstructor, 1);
    printMethodParameter(innerClassConstructorParameterOf0);

    MethodParameter innerClassConstructorParameterOf1 = MethodParameter
        .forExecutable(innerClassConstructor, 2);
    printMethodParameter(innerClassConstructorParameterOf1);

    Method listGetMethod = ArrayList.class.getMethod("get", int.class);
    MethodParameter listGetReturnParameter = MethodParameter
        .forExecutable(listGetMethod, -1);
    printMethodParameter(listGetReturnParameter);


  }


  public static void printMethodParameter(MethodParameter parameter) {
    System.out.println();
    System.out.println("===========================start==============================");
    System.out.println("GenericParameterType: " + parameter.getGenericParameterType());
    System.out.println("ParameterType: " + parameter.getParameterType());
    if (parameter.getParameterIndex() >= 0) {
      System.out.println(
          "Parameter.Type: " + parameter.getParameter().getType());
      System.out.println("Parameter.Name: " + parameter.getParameter().getName());
    }
    System.out.println("Member.Name: " + parameter.getMember().getName());
    System.out.println("Member.declaringClass: " + parameter.getMember().getDeclaringClass());
    System.out.println("ParameterIndex: " + parameter.getParameterIndex());
    System.out
        .println("ParameterAnnotations.length: " + parameter.getParameterAnnotations().length);
    System.out.printf("ParameterAnnotations: [");
    Annotation[] parameterAnnotations = parameter.getParameterAnnotations();
    for (Annotation temp : parameterAnnotations) {
      System.out.printf(",%s", temp.toString());
    }
    System.out.printf("]%n");
    System.out.println("@Param: " + parameter.getParameterAnnotation(Param.class));
    System.out.println("===========================end==============================");
  }


}
