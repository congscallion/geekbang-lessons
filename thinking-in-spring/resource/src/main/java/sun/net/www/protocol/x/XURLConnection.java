package sun.net.www.protocol.x;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import org.springframework.core.io.ClassPathResource;

/**
 * X {@link URLConnection} 实现
 *
 * @author wangcymy@gmail.com(wangcong) 2020/12/28 14:57
 */
public class XURLConnection extends URLConnection {

  private final ClassPathResource classPathResource;


  /**
   * Constructs a URL connection to the specified URL. A connection to
   * the object referenced by the URL is not created.
   *
   * @param url the specified URL.
   */
  protected XURLConnection(URL url) {
    super(url);

    // URL = x:///META-INF/default.properties
    this.classPathResource = new ClassPathResource(url.getPath());
  }

  @Override
  public void connect() throws IOException {

  }

  @Override
  public InputStream getInputStream() throws IOException {
    return classPathResource.getInputStream();
  }
}
