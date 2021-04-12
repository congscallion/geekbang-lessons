package slydm.geektimes.training.microprofile.rest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Variant;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/7 18:06
 */
public class ClientInvocation implements Invocation {

  protected ClientRequestHeaders headers;
  private Map<String, Object> properties = new HashMap<>();

  protected Client client;
  protected URI uri;

  protected String method;

  protected Object entity;

  protected Type entityGenericType;

  protected Class<?> entityClass;

  protected Annotation[] entityAnnotations;

  public ClientInvocation(Client client, URI uri, ClientRequestHeaders headers) {
    this.uri = uri;
    this.client = client;
    this.headers = headers;
  }

  protected ClientInvocation(final ClientInvocation clientInvocation) {
    this.client = clientInvocation.client;
    this.headers = new ClientRequestHeaders();
    this.headers.headers.putAll(clientInvocation.headers.headers);
    this.method = clientInvocation.method;
    this.entity = clientInvocation.entity;
    this.entityGenericType = clientInvocation.entityGenericType;
    this.entityClass = clientInvocation.entityClass;
    this.entityAnnotations = clientInvocation.entityAnnotations;
    this.uri = clientInvocation.uri;
  }

  @Override
  public Invocation property(String name, Object value) {
    properties.put(name, value);
    return this;
  }

  @Override
  public Response invoke() {
    ClientResponse response = (ClientResponse) ((MyClientImpl) client).httpEngine().invoke(this);
    return response;
  }

  @Override
  public <T> T invoke(Class<T> responseType) {
    return null;
  }

  @Override
  public <T> T invoke(GenericType<T> responseType) {
    return null;
  }

  @Override
  public Future<Response> submit() {
    return null;
  }

  @Override
  public <T> Future<T> submit(Class<T> responseType) {
    return null;
  }

  @Override
  public <T> Future<T> submit(GenericType<T> responseType) {
    return null;
  }

  @Override
  public <T> Future<T> submit(InvocationCallback<T> callback) {
    return null;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public void setEntity(Entity<?> entity) {
    if (entity == null) {
      this.entity = null;
      this.entityAnnotations = null;
      this.entityClass = null;
      this.entityGenericType = null;
    } else {
      Object ent = entity.getEntity();
      setEntityObject(ent);
      this.entityAnnotations = entity.getAnnotations();
      Variant v = entity.getVariant();
      headers.setMediaType(v.getMediaType());
      headers.setLanguage(v.getLanguage());
      headers.header("Content-Encoding", null);
      headers.header("Content-Encoding", v.getEncoding());
    }
  }

  public void setEntityObject(Object ent) {
    if (ent instanceof GenericEntity) {
      GenericEntity<?> genericEntity = (GenericEntity<?>) ent;
      entityClass = genericEntity.getRawType();
      entityGenericType = genericEntity.getType();
      this.entity = genericEntity.getEntity();
    } else {
      if (ent == null) {
        this.entity = null;
        this.entityClass = null;
        this.entityGenericType = null;
      } else {
        this.entity = ent;
        this.entityClass = ent.getClass();
        this.entityGenericType = ent.getClass();
      }
    }
  }


  public URI getUri() {
    return uri;
  }

  public String getMethod() {
    return method;
  }

  public Object getEntity() {
    return entity;
  }

  public ClientRequestHeaders getHeaders() {
    return this.headers;
  }

  /**
   * 把 http body 写入 OutputStream
   *
   */
  public void writeRequestBody(OutputStream outputStream) {

    byte[] contents;
    try {

      if (this.getEntity() instanceof String) {
        String stringEntity = (String) getEntity();
        contents = stringEntity.getBytes();
      }

      MediaType mediaType = headers.getMediaType();
      if (mediaType.equals(MediaType.APPLICATION_FORM_URLENCODED)
          || mediaType.equals(MediaType.MULTIPART_FORM_DATA)
          || mediaType.equals(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
          || mediaType.equals(MediaType.MULTIPART_FORM_DATA_TYPE)
      ) {

        // submit form， TODO 不支持 文件
        contents = submitFrom();
      } else {
        ObjectMapper objectMapper = new ObjectMapper();
        contents = objectMapper.writeValueAsBytes(this.entity);
      }

      outputStream.write(contents);
      outputStream.flush();
    } catch (IOException e) {
      throw new IllegalStateException("serialized fail.");
    }
  }

  /**
   * 将数据写成 name=value&name2=value2 的格式
   */
  private byte[] submitFrom() {

    if (!MultivaluedMap.class.isAssignableFrom(entityClass)) {
      throw new IllegalArgumentException("提交表单，entity 只支持 javax.ws.rs.core.MultivaluedMap 类型");
    }

    MultivaluedMap<String, String> formData = (MultivaluedMap<String, String>) this.entity;
    String charset = headers.getMediaType().getParameters().get(MediaType.CHARSET_PARAMETER);
    if (charset == null) {
      charset = StandardCharsets.UTF_8.name();
    }

    try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(baos, charset)) {

      boolean first = true;
      for (Map.Entry<String, List<String>> entry : formData.entrySet()) {
        String encodedName = URLEncoder.encode(entry.getKey(), charset);

        for (String value : entry.getValue()) {
          if (first) {
            first = false;
          } else {
            writer.write("&");
          }
          value = URLEncoder.encode(value, charset);

          writer.write(encodedName);
          writer.write("=");
          writer.write(value);
        }
        writer.flush();
      }

      return baos.toByteArray();
    } catch (IOException ex) {
      throw new IllegalStateException(ex);
    }
  }

}
