package slydm.geektimes.training.configuration.microprofile.config.source;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Map;
import org.junit.Test;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/1 15:04
 */
public class SystemEnvironmentConfigSourceTest {

  private SystemEnvironmentConfigSource configSource = new SystemEnvironmentConfigSource();

  @Test
  public void testGetName() {
    assertEquals("System Environment Variables", configSource.getName());
  }

  @Test
  public void testGetOrdinal() {
    assertEquals(300, configSource.getOrdinal());
  }


  @Test
  public void testGetValue() {
    assertEquals("72089101", configSource.getValue("USERNAME"));
    assertEquals("\\Users\\72089101", configSource.getValue("HOMEPATH"));
  }


  @Test
  public void testGetPropertyNames() {
    assertThat(configSource.getPropertyNames(), hasItems("USERNAME", "HOMEPATH"));
  }

  @Test
  public void testGetProperties() {
    Map<String, String> properties = configSource.getProperties();
    assertThat(properties.keySet().size(), is(55));
    assertThat(properties.keySet(), hasItems("COMPUTERNAME", "windir"));
    assertThat(properties.values().size(), is(55));
    assertThat(properties.values(), hasItems("\\Users\\72089101", "Windows_NT"));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testNotAllowedUpdate() {
    Map<String, String> properties = configSource.getProperties();
    properties.put("k3", "V3");
  }


}
