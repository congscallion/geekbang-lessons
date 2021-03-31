package slydm.geektimes.training.configuration.microprofile.config.converter;

/**
 * {@link String} to {@link Float}
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/31 18:10
 */
public class FloatConverter extends AbstractConverter<Float> {

  @Override
  protected Float doConvert(String value) {
    return Float.valueOf(value);
  }
}
