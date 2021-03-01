package com.slydm.thinking.in.spring.i18n;

import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * {@link ResourceBundle} 默认提供两种实现 {@link ListResourceBundle} {@link PropertyResourceBundle}
 *
 * {@link ListResourceBundle} 国际化资源逻辑如下:
 * <br>1. 定义一个子类，继承 {@link ListResourceBundle} 类，实现 getContents 方法。返回 key value 对文案,用于默认文案。
 * <br>2. 使用哪种语言，则需要按 className_lang_country 格式定义与特定语言相关的实现。比如: className_en_US,className_zh_CN.
 * <br>3. ResourceBundle.getBundle("className 类的全限定名", Locale 实例); 获取与特定语言相关的方案实现。
 * <br>4. 调用第三步返回的 ResourceBundle 实例的 get 方法获取与该语言相关的文案。
 * <br>5. 当特定语言相关的文案不存在时，则使用 className 类中的文案。
 *
 * {@link ResourceBundle#getBundle(String baseName, Locale locale)} 方法 查找国际化文案逻辑如下:
 * <br>1. 资源文件名: sourceName = baseName_locale.lang_locale.country
 * <br>2. Class.forName(sourceName) 加载类
 * <br>3. 找不到，从 classpath 下加载 sourceName.properties 文件
 * <br>4. 调用第三步返回的 ResourceBundle 实例的 get 方法获取与该语言相关的文案。
 * <br>5. 取不到，则使用  baseName 类中定义的文案 或者 baseName.properties 定义的文案。
 *
 * @author wangcymy@gmail.com(wangcong) 2021/1/5 16:05
 */
public class ResourceBundleDemo {

  public static void main(String[] args) {

    ResourceBundle bundle = new MyResources();

    System.out.println(bundle.getObject("OkKey")); // OK

    ResourceBundle myResources =
        ResourceBundle.getBundle("com.slydm.thinking.in.spring.i18n.ResourceBundleDemo$MyResources",
            Locale.US);
    System.out.println(myResources.getObject("OkKey")); // OK_en

    myResources =
        ResourceBundle.getBundle("com.slydm.thinking.in.spring.i18n.ResourceBundleDemo$MyResources",
            Locale.CHINESE);
    System.out.println(myResources.getObject("OkKey")); // OK_zh

    myResources =
        ResourceBundle.getBundle("MyResources", Locale.getDefault());
    System.out.println(myResources.getObject("label1"));

    myResources =
        ResourceBundle.getBundle("MyResources", Locale.US);
    System.out.println(myResources.getObject("label1"));

  }

  /**
   * 默认 文案
   */
  public static class MyResources extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
      return new Object[][]{
          {"OkKey", "OK"},
          {"CancelKey", "Cancel"},
      };
    }
  }

  /**
   * en_US 文案
   */
  public static class MyResources_en_US extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
      return new Object[][]{
          {"OkKey", "OK_en"},
          {"CancelKey", "Cancel_en"},
      };
    }
  }

  /**
   * zh_CN 文案
   */
  public static class MyResources_zh_CN extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
      return new Object[][]{
          {"OkKey", "OK_zh"},
          {"CancelKey", "Cancel_zh"},
      };
    }
  }


}
