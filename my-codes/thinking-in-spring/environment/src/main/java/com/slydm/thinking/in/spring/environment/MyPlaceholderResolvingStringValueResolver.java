package com.slydm.thinking.in.spring.environment;

import java.util.Properties;
import org.springframework.beans.BeansException;
import org.springframework.lang.Nullable;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.PropertyPlaceholderHelper.PlaceholderResolver;
import org.springframework.util.StringUtils;
import org.springframework.util.StringValueResolver;

/**
 *
 * 本例主要是演示在 spring 3.0 之前， 内部是如何处理点位符。
 *
 * @author wangcymy@gmail.com(wangcong) 2021/1/28 14:14
 */
public class MyPlaceholderResolvingStringValueResolver implements StringValueResolver {

  private final PropertyPlaceholderHelper helper;
  private final PlaceholderResolver resolver;

  public MyPlaceholderResolvingStringValueResolver(Properties props) {
    this.helper = new PropertyPlaceholderHelper(
        "${", "}", ":", false);
    this.resolver = new PropertyPlaceholderConfigurerResolver(props);
  }

  @Override
  @Nullable
  public String resolveStringValue(String strVal) throws BeansException {
    String resolved = this.helper.replacePlaceholders(strVal, this.resolver);
    return (!StringUtils.hasText(resolved) ? null : resolved);
  }


  private final class PropertyPlaceholderConfigurerResolver implements PlaceholderResolver {

    private final Properties props;

    private PropertyPlaceholderConfigurerResolver(Properties props) {
      this.props = props;
    }

    @Override
    @Nullable
    public String resolvePlaceholder(String placeholderName) {

      return new PropertyPlaceholderUtils().resolvePlaceholder(placeholderName,
          this.props, 1);
    }
  }
}
