package slydm.geektimes.training.configuration.microprofile.config.converter;

/**
 * {@link String} to {@link Short}
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/31 18:07
 */
public class ShortConverter extends AbstractConverter<Short> {

  @Override
  protected Short doConvert(String value) {
    return Short.valueOf(value);
  }
}
