package slydm.geektimes.training.configuration.microprofile.config.source;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Map;
import org.junit.Test;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/1 14:13
 */
public class SystemPropertiesConfigSourceTest {

  private SystemPropertiesConfigSource configSource = new SystemPropertiesConfigSource();

  @Test
  public void testGetName() {
    assertEquals("Java System Properties", configSource.getName());
  }

  @Test
  public void testGetOrdinal() {
    assertEquals(400, configSource.getOrdinal());
  }


  @Test
  public void testGetValue() {
    assertEquals("windows", configSource.getValue("sun.desktop"));
    assertEquals("1.8.0_201-b09", configSource.getValue("java.runtime.version"));
  }


  @Test
  public void testGetPropertyNames() {
    assertThat(configSource.getPropertyNames(), hasItems("os.version", "file.encoding"));
  }

  @Test
  public void testGetProperties() {
    Map<String, String> properties = configSource.getProperties();
    assertThat(properties.keySet().size(), is(55));
    assertThat(properties.keySet(), hasItems("os.version", "java.home"));
    assertThat(properties.values().size(), is(55));
    assertThat(properties.values(), hasItems("Windows 10", "CN"));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testNotAllowedUpdate() {
    Map<String, String> properties = configSource.getProperties();
    properties.put("k3", "V3");
  }

}
