package com.slydm.thinking.in.spring.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/1/11 18:13
 */
public class GenericAPIDemo {

  public static void main(String[] args) {

    // 原生类型 primitive types : int long float
    Class intClass = int.class;
    System.out.println(intClass);

    // 数组类型 array types : int[],Object[]
    Class objectArrayClass = Object[].class;
    System.out.println(objectArrayClass);

    // 原始类型 raw types : java.lang.String
    Class rawClass = String.class;
    System.out.println(rawClass);

    // 泛型参数类型 parameterized type
    ParameterizedType parameterizedType = (ParameterizedType) ArrayList.class
        .getGenericSuperclass();
    System.out.println(parameterizedType);

    // <E>
    Type[] typeVariables = parameterizedType.getActualTypeArguments();

    Stream.of(typeVariables)
        .map(TypeVariable.class::cast) // Type -> TypeVariable
        .forEach(System.out::println);

  }

}
