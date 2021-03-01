package sun.net.www.protocol.x;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import org.springframework.util.StreamUtils;

/**
 * X Handler 测试示例
 *
 * @author wangcymy@gmail.com(wangcong) 2020/12/28 15:00
 */
public class HandlerTest {

  public static void main(String[] args) throws IOException {

    // 类似于 classpath:/META-INF/default.properties
    URL url = new URL("x:///META-INF/default.properties");

    InputStream inputStream = url.openStream();
    System.out.println(StreamUtils.copyToString(inputStream, Charset.forName("UTF-8")));

    url = new URL(
        "file:///workspace\\thinking-in-spring\\resource\\src\\main\\java\\sun\\net\\www\\protocol\\x\\HandlerTest.java");

    InputStream inputStream1 = url.openStream();
    System.out.println(StreamUtils.copyToString(inputStream1, Charset.forName("UTF-8")));

  }

}
