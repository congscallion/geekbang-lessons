package slydm.geektimes.training.configuration.microprofile.config.converter;

/**
 * {@link String} to {@link Byte}
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/31 18:13
 */
public class ByteConverter extends AbstractConverter<Byte> {

  @Override
  protected Byte doConvert(String value) {
    return Byte.valueOf(value);
  }
}
