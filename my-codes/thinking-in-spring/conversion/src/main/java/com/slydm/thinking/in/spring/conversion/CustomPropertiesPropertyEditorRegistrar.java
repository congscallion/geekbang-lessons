package com.slydm.thinking.in.spring.conversion;

import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import java.util.Properties;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/1/8 18:13
 */
public class CustomPropertiesPropertyEditorRegistrar implements PropertyEditorRegistrar {

  @Override
  public void registerCustomEditors(PropertyEditorRegistry registry) {

    registry
        .registerCustomEditor(null, "context", new StringToPropertiesPropertyEditor());

  }
}
