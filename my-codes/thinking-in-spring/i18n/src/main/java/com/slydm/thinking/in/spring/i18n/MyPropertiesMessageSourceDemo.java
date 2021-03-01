package com.slydm.thinking.in.spring.i18n;

import java.util.Locale;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/1/6 18:11
 */
public class MyPropertiesMessageSourceDemo {

  public static void main(String[] args) {

    MyPropertiesMessageSource messageSource = new MyPropertiesMessageSource();
    messageSource.setBaseName("i18n");

    String label = messageSource.getMessage("label1", new Object[0], Locale.getDefault());
    System.out.println(label);

    label = messageSource.getMessage("label1", new Object[0], Locale.US);
    System.out.println(label);

    label = messageSource.getMessage("label1", new Object[0], Locale.US);
    System.out.println(label);

    label = messageSource.getMessage("label1", new Object[0], Locale.getDefault());
    System.out.println(label);

    label = messageSource.getMessage("label3", new Object[]{"Tomcat"}, Locale.getDefault());
    System.out.println(label);

    label = messageSource.getMessage("label3", new Object[]{"Tomcat"}, Locale.CANADA);
    System.out.println(label);

  }

}
