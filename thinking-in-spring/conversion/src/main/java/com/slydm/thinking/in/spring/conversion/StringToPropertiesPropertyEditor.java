package com.slydm.thinking.in.spring.conversion;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

/**
 * 自定义 {@link PropertyEditor} 实现
 *
 * <p>
 * conversion {@link String} to {@link java.util.Properties}
 *
 * @author wangcymy@gmail.com(wangcong) 2021/1/8 17:02
 */
public class StringToPropertiesPropertyEditor extends PropertyEditorSupport implements
    PropertyEditor {


  // 1.实现　setAsText　方法
  @Override
  public void setAsText(String text) throws IllegalArgumentException {

    //2. 将 String 类型转换为 Properties类型
    Properties prop = new Properties();
    try {
      prop.load(new StringReader(text));
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }

    //3. 临时存储
    setValue(prop);
  }
}
