package com.slydm.thinking.in.spring.helper;

import org.springframework.core.ResolvableType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/2/4 18:13
 */
public class ResolvableTypeDemo {

  public static void main(String[] args) {

    ResolvableType resolvableType = ResolvableType.forClass(StringToNumberConverterFactory.class);
    // 这样取不出父类 ConverterFactory<String, Number> 中的泛型类型
    ResolvableType[] generics = resolvableType.getGenerics();
    printf(generics);
    System.out.println("=======================================================");

    resolvableType = ResolvableType.forClass(StringToNumberConverterFactory.class)
        .as(ConverterFactory.class);
    generics = resolvableType.getGenerics();
    printf(generics);
    System.out.println("=======================================================");

    resolvableType = ResolvableType.forClass(StringToNumber.class);
    generics = resolvableType.getGenerics();
    printf(generics);
    System.out.println("=======================================================");

    resolvableType = ResolvableType.forClass(StringToNumber.class).as(Converter.class);
    generics = resolvableType.getGenerics();
    printf(generics);
    System.out.println("=======================================================");


  }

  private static final void printf(ResolvableType[] resolvableTypes) {
    for (ResolvableType generic : resolvableTypes) {
      System.out.println(generic.resolve());
    }
  }


  private static final class StringToNumberConverterFactory implements
      ConverterFactory<String, Number> {

    @Override
    public <T extends Number> Converter<String, T> getConverter(Class<T> targetType) {
      return null;
    }
  }

  private static final class StringToNumber<T extends Number> implements Converter<String, T> {

    @Override
    public T convert(String source) {
      return null;
    }
  }

}
