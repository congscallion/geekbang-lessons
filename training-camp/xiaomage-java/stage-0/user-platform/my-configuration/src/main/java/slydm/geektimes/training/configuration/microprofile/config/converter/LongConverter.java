package slydm.geektimes.training.configuration.microprofile.config.converter;

/**
 * {@link String} to {@link Short}
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/31 18:08
 */
public class LongConverter extends AbstractConverter<Long> {

  @Override
  protected Long doConvert(String value) {
    return Long.valueOf(value);
  }
}
