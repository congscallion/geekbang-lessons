package com.slydm.thinking.in.spring.conversion;

/**
 * {@link java.beans.PropertyEditor} 示例
 *
 * @author wangcymy@gmail.com(wangcong) 2021/1/8 17:00
 * @see StringToPropertiesPropertyEditor
 */
public class PropertyEditorDemo {

  public static void main(String[] args) {

    String text = "name=wangcong";

    StringToPropertiesPropertyEditor propertyEditor = new StringToPropertiesPropertyEditor();

    propertyEditor.setAsText(text);

    Object value = propertyEditor.getValue();
    System.out.println(value);

    System.out.println(propertyEditor.getAsText());

  }


}
