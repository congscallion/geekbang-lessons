package slydm.geektimes.training.configuration.microprofile.config.source;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Map;
import org.junit.Test;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/1 16:07
 */
public class DefaultResourceConfigSourceTest {

  DefaultResourceConfigSource configSource = new DefaultResourceConfigSource();

  @Test
  public void testGetName() {
    assertEquals("Default Config File", configSource.getName());
  }

  @Test
  public void testGetValue() {
    assertEquals("v1", configSource.getValue("m1"));
    assertEquals("v2", configSource.getValue("m2"));
    assertEquals("v3", configSource.getValue("m3"));
  }

  @Test
  public void testGetOrdinal() {
    assertEquals(100, configSource.getOrdinal());
  }

  @Test
  public void testGetPropertyNames() {
    assertThat(configSource.getPropertyNames(), hasItems("m1", "m2", "m3"));
  }

  @Test
  public void testGetProperties() {
    Map<String, String> properties = configSource.getProperties();
    assertThat(properties.keySet().size(), is(3));
    assertThat(properties.keySet(), hasItems("m1", "m2", "m3"));
    assertThat(properties.values().size(), is(3));
    assertThat(properties.values(), hasItems("v1", "v2", "v3"));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testNotAllowedUpdate() {
    Map<String, String> properties = configSource.getProperties();
    properties.put("k3", "V3");
  }


}
