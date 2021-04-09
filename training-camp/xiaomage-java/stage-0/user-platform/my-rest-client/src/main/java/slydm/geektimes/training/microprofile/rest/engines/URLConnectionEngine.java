package slydm.geektimes.training.microprofile.rest.engines;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.Map;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import slydm.geektimes.training.microprofile.rest.client.ClientInvocation;
import slydm.geektimes.training.microprofile.rest.client.ClientResponse;

/**
 * {@link ClientHttpEngine} based on {@link java.net.HttpURLConnection}
 *
 * @author wangcymy@gmail.com(wangcong) 2021/4/9 9:20
 */
public class URLConnectionEngine implements ClientHttpEngine {

  protected Integer readTimeout;
  protected Integer connectTimeout;
  protected String proxyHost;
  protected Integer proxyPort;


  @Override
  public Response invoke(Invocation inv) {

    ClientInvocation request = (ClientInvocation) inv;
    final HttpURLConnection connection;

    final int status;
    try {

      connection = createConnection(request);

      executeRequest(request, connection);

      status = connection.getResponseCode();
    } catch (IOException e) {
      throw new ProcessingException("Unable to invoke request: " + e.toString());
    }

    //Creating response with stream content
    ClientResponse response = new ClientResponse();

    //Setting attributes
    response.setStatus(status);
    response.setHeaders(getHeaders(connection));
    response.setConnection(connection);

    return response;
  }

  @Override
  public void close() {

  }


  /**
   * 执行 http 请求
   */
  protected void executeRequest(ClientInvocation request, HttpURLConnection connection) {
    connection.setInstanceFollowRedirects(request.getMethod().equals("GET"));
    if (request.getEntity() != null) {
      if (request.getMethod().equals("GET")) {
        throw new ProcessingException("A GET request cannot have a body.");
      }

      try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
        request.writeRequestBody(baos);

        commitHeaders(request, connection);
        connection.setDoOutput(true);

        OutputStream os = connection.getOutputStream();
        os.write(baos.toByteArray());
        os.flush();
        os.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } else // no body
    {
      commitHeaders(request, connection);
    }

  }

  /**
   * 设置 header 值
   */
  protected void commitHeaders(ClientInvocation request, HttpURLConnection connection) {
    MultivaluedMap<String, String> headers = request.getHeaders().asMap();
    for (Map.Entry<String, List<String>> header : headers.entrySet()) {
      List<String> values = header.getValue();
      for (String value : values) {
        connection.addRequestProperty(header.getKey(), value);
      }
    }
  }

  /**
   * 创建 http 连接
   */
  protected HttpURLConnection createConnection(ClientInvocation request) throws IOException {

    Proxy proxy = null;
    if (this.proxyHost != null && this.proxyPort != null) {
      proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(this.proxyHost, this.proxyPort));
    } else {
      proxy = Proxy.NO_PROXY;
    }

    HttpURLConnection connection = (HttpURLConnection) request.getUri().toURL().openConnection(proxy);
    connection.setRequestMethod(request.getMethod());

    if (this.connectTimeout != null) {
      connection.setConnectTimeout(this.connectTimeout);
    }
    if (this.readTimeout != null) {
      connection.setReadTimeout(this.readTimeout);
    }

    return connection;
  }

  protected MultivaluedMap<String, String> getHeaders(final HttpURLConnection connection) {
    MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
    for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
      if (header.getKey() != null) {
        for (String value : header.getValue()) {
          headers.add(header.getKey(), value);
        }
      }
    }
    return headers;
  }
}
