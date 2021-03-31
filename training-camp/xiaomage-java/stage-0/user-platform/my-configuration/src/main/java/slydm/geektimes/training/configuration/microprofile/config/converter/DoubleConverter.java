package slydm.geektimes.training.configuration.microprofile.config.converter;

/**
 * {@link String} to {@link Double}
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/31 18:11
 */
public class DoubleConverter extends AbstractConverter<Double> {

  @Override
  protected Double doConvert(String value) {
    return Double.valueOf(value);
  }
}
