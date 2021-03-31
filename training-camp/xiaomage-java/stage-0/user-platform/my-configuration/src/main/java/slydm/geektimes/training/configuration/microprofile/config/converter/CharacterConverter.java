package slydm.geektimes.training.configuration.microprofile.config.converter;

/**
 * {@link String} to {@link Character}
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/31 18:12
 */
public class CharacterConverter extends AbstractConverter<Character> {

  @Override
  protected Character doConvert(String value) {
    if (value == null || value.isEmpty()) {
      return null;
    }
    return Character.valueOf(value.charAt(0));
  }
}
