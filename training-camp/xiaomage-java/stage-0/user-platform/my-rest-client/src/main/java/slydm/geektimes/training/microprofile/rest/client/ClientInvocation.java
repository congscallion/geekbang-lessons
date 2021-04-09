package slydm.geektimes.training.microprofile.rest.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
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
   */
  public void writeRequestBody(OutputStream outputStream) {

    try {

      if (this.getEntity() instanceof String) {
        String stringEntity = (String) getEntity();
        outputStream.write(stringEntity.getBytes());
        return;
      }

      ObjectOutputStream oos = new ObjectOutputStream(outputStream);
      oos.writeObject(getEntity());
    } catch (IOException e) {
      throw new IllegalStateException("serialized fail.");
    }
  }

}
