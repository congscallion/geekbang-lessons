package slydm.geektimes.training.configuration.microprofile.config.source;

import static org.hamcrest.CoreMatchers.hasItems;
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
    assertEquals("C:\\ProgramData", configSource.getValue("ProgramData"));
    assertEquals("Windows_NT", configSource.getValue("OS"));
  }


  @Test
  public void testGetPropertyNames() {
    assertThat(configSource.getPropertyNames(), hasItems("NVM_HOME", "JAVA_HOME"));
  }

  @Test
  public void testGetProperties() {
    Map<String, String> properties = configSource.getProperties();
    assertThat(properties.keySet(), hasItems("COMPUTERNAME", "windir"));
    assertThat(properties.values(), hasItems("C:\\ProgramData", "Windows_NT"));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testNotAllowedUpdate() {
    Map<String, String> properties = configSource.getProperties();
    properties.put("k3", "V3");
  }


}
