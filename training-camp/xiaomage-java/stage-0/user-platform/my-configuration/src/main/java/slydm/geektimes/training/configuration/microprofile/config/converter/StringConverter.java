package slydm.geektimes.training.configuration.microprofile.config.converter;

/**
 * {@link String} to {@link String}
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/31 18:06
 */
public class StringConverter extends AbstractConverter<String> {


  @Override
  protected String doConvert(String value) {
    return value;
  }
}
