package slydm.geektimes.training.configuration.microprofile.config.converter;

/**
 * {@link String} to {@link Integer}
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/31 18:11
 */
public class IntegerConverter extends AbstractConverter<Integer> {

  @Override
  protected Integer doConvert(String value) {
    return Integer.valueOf(value);
  }
}
