package slydm.geektimes.training.configuration.microprofile.config.converter;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.eclipse.microprofile.config.spi.Converter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/1 16:13
 */
public class ConvertersTest {


  private Converters converters;

  @Before
  public void init() {
    converters = new Converters();
  }


  @Test
  public void testResolveConvertedType() {
    assertEquals(Byte.class, converters.resolveConvertedType(new ByteConverter()));
    assertEquals(Short.class, converters.resolveConvertedType(new ShortConverter()));
    assertEquals(Integer.class, converters.resolveConvertedType(new IntegerConverter()));
    assertEquals(Long.class, converters.resolveConvertedType(new LongConverter()));
    assertEquals(Float.class, converters.resolveConvertedType(new FloatConverter()));
    assertEquals(Double.class, converters.resolveConvertedType(new DoubleConverter()));
    assertEquals(String.class, converters.resolveConvertedType(new StringConverter()));
  }


  @Test
  public void testConvert() {
    Converter converter = new ByteConverter();
    converters.addConverter(converter);
    List<Converter> getConverts = this.converters.getConverters(Byte.class);
    Byte byteVal = (Byte) getConverts.get(0).convert("12");
    Assert.assertEquals(byteVal.byteValue(), Byte.parseByte("12"));

    converter = new IntegerConverter();
    converters.addConverter(converter);
    getConverts = this.converters.getConverters(Integer.class);
    Integer integerVal = (Integer) getConverts.get(0).convert("12");
    Assert.assertEquals(integerVal.intValue(), Integer.parseInt("12"));

    converter = new FloatConverter();
    converters.addConverter(converter);
    getConverts = this.converters.getConverters(Float.class);
    Float floatVal = (Float) getConverts.get(0).convert("12.2f");
    Assert.assertEquals(floatVal.floatValue(), Float.parseFloat("12.2f"), 0f);

  }


}
