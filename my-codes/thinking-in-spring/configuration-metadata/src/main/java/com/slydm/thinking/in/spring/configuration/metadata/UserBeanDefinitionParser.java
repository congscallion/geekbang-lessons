package com.slydm.thinking.in.spring.configuration.metadata;

import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * "user" 元素的 {@link BeanDefinitionParser} 实现
 *
 * @author wangcymy@gmail.com(wangcong) 2020/12/28 11:31
 */
public class UserBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

  @Override
  protected Class<?> getBeanClass(Element element) {
    return User.class;
  }

  @Override
  protected void doParse(Element element, BeanDefinitionBuilder builder) {
    setPropertyValue("id", element, builder);
    setPropertyValue("name", element, builder);
    setPropertyValue("city", element, builder);
  }

  private void setPropertyValue(String attributeName, Element element,
      BeanDefinitionBuilder builder) {
    String attributeValue = element.getAttribute(attributeName);
    if (StringUtils.hasText(attributeValue)) {

      // -> <property name="" value=""/>
      builder.addPropertyValue(attributeName, attributeValue);

    }
  }
}
