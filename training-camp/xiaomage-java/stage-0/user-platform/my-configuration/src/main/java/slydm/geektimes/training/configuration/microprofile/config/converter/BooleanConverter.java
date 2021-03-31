package slydm.geektimes.training.configuration.microprofile.config.converter;

/**
 * {@link String} to {@link Boolean}
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/31 18:14
 */
public class BooleanConverter extends AbstractConverter<Boolean> {

  @Override
  protected Boolean doConvert(String value) {

    if ("true".equalsIgnoreCase(value) ||
        "yes".equalsIgnoreCase(value) ||
        "y".equalsIgnoreCase(value) ||
        "on".equalsIgnoreCase(value) ||
        "1".equalsIgnoreCase(value)) {
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }
}
