package com.slydm.thinking.in.spring.i18n;

import java.util.Locale;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * {@link ReloadableResourceBundleMessageSource} 示例
 *
 * @author wangcymy@gmail.com(wangcong) 2021/1/6 14:09
 */
public class ReloadableResourceBundleMessageSourceDemo {

  public static void main(String[] args) {

    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("i18n");
    messageSource.setDefaultEncoding("UTF-8");

    String label = messageSource.getMessage("label1", new Object[0], Locale.getDefault());
    System.out.println(label);

  }

}
