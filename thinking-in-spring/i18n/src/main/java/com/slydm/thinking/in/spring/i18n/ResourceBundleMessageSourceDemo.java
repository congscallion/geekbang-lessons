package com.slydm.thinking.in.spring.i18n;

import java.util.Locale;
import java.util.Properties;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * {@link ResourceBundleMessageSource} 示例
 *
 * @author wangcymy@gmail.com(wangcong) 2021/1/5 18:15
 */
public class ResourceBundleMessageSourceDemo {

  public static void main(String[] args) {

    ResourceBundleMessageSource parent = new ResourceBundleMessageSource();
    parent.setBasename("parent");
    parent.setDefaultEncoding("UTF-8");

    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("MyResources");
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.setParentMessageSource(parent);

    String label = messageSource.getMessage("label1", new Object[]{}, Locale.US);
    System.out.println(label);

    label = messageSource.getMessage("label1", new Object[]{}, Locale.CHINESE);
    System.out.println(label);

    label = messageSource.getMessage("label2", new Object[]{"through"}, Locale.CHINESE);
    System.out.println(label);

    label = messageSource.getMessage("label2", new Object[]{"through"}, Locale.US);
    System.out.println(label);

    label = messageSource.getMessage("label3", new Object[]{"吗?"}, Locale.CHINESE);
    System.out.println(label);

    label = messageSource.getMessage("label3", new Object[]{"吗?"}, Locale.US);
    System.out.println(label);

    // 设置通用信息, 当任何资源文件都找不到文案时，从这里查找。
    Properties properties = new Properties();
    properties.setProperty("label4", "Label 4 is done! for zh");
    messageSource.setCommonMessages(properties);
    String label4 = messageSource.getMessage("label4", new Object[0], Locale.getDefault());
    System.out.println(label4);

    // 从父 messagesource中查找
    label = messageSource.getMessage("label5", new Object[0], Locale.getDefault());
    System.out.println(label);
    label = messageSource.getMessage("label5", new Object[0], Locale.US);
    System.out.println(label);
    label = messageSource.getMessage("label5", new Object[0], Locale.GERMAN);
    System.out.println(label);
  }

}
