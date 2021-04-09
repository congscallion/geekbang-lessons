package slydm.geektimes.training.microprofile.rest.engines;

import java.net.HttpURLConnection;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;

/**
 * http 服务抽象， 可由具体的技术来实现. 比如: {@link HttpURLConnection},  apache HttpClient, google http client.
 *
 * // TODO  暂不实现 https
 *
 * @author wangcymy@gmail.com(wangcong) 2021/4/8 17:23
 */
public interface ClientHttpEngine {

  /**
   * 执行 http请求并得到响应
   */
  Response invoke(Invocation request);

  /**
   * 关闭资源
   */
  void close();

}
