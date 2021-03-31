package slydm.geektimes.training.configuration.microprofile.config.converter;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * 公共的类型转换器，用于公共转换逻辑实现
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/31 18:03
 */
public abstract class AbstractConverter<T> implements Converter<T> {

  @Override
  public T convert(String value) throws IllegalArgumentException, NullPointerException {

    if (value == null) {
      throw new NullPointerException("The value must not be null!");
    }
    return doConvert(value);
  }

  /**
   * 具体的类型转换逻辑， 用于将 {@link String} 转换为 {@link T}
   */
  protected abstract T doConvert(String value);

}
