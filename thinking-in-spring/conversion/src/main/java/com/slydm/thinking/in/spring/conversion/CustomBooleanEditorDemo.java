package com.slydm.thinking.in.spring.conversion;

import org.springframework.beans.propertyeditors.CustomBooleanEditor;

/**
 * {@link CustomBooleanEditor} 示例
 *
 * @author wangcymy@gmail.com(wangcong) 2021/1/8 17:33
 */
public class CustomBooleanEditorDemo {

  public static void main(String[] args) {

    CustomBooleanEditor editor = new CustomBooleanEditor(true);
    editor.setAsText("1");

    System.out.println(editor.getValue());


  }

}
