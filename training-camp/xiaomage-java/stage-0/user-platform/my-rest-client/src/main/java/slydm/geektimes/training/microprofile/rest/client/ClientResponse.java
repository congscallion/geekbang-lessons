package slydm.geektimes.training.microprofile.rest.client;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Link.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import slydm.geektimes.training.microprofile.rest.spi.HttpResponseCodes;

/**
 * @author 72089101@vivo.com(wangcong) 2021/4/8 17:27
 */
public class ClientResponse extends Response {

  protected Object entity;
  protected byte[] bufferedEntity;
  protected int status = HttpResponseCodes.SC_OK;
  protected volatile boolean isClosed;
  protected String reason = "Unknown Code";
  protected MultivaluedMap<String, Object> metadata = new MultivaluedHashMap<>();
  private MediaType mediaType;
  private Locale locale;


  @Override
  public int getStatus() {
    return 0;
  }

  @Override
  public StatusType getStatusInfo() {
    StatusType statusType = Status.fromStatusCode(status);
    if (statusType == null) {
      statusType = new StatusType() {
        @Override
        public int getStatusCode() {
          return status;
        }

        @Override
        public Status.Family getFamily() {
          return Status.Family.familyOf(status);
        }

        @Override
        public String getReasonPhrase() {
          return reason;
        }
      };
    }
    return statusType;
  }

  @Override
  public Object getEntity() {
    abortIfClosed();
    return entity;
  }

  @Override
  public <T> T readEntity(Class<T> entityType) {
    return readEntity(entityType, null, null);
  }

  @Override
  public <T> T readEntity(GenericType<T> entityType) {
    return readEntity((Class<T>) entityType.getRawType(), entityType.getType(), null);
  }

  @Override
  public <T> T readEntity(Class<T> entityType, Annotation[] annotations) {
    return readEntity(entityType, null, annotations);
  }

  @Override
  public <T> T readEntity(GenericType<T> entityType, Annotation[] annotations) {
    return readEntity((Class<T>) entityType.getRawType(), entityType.getType(), annotations);
  }

  protected <T> T readEntity(Class<T> type, Type genericType, Annotation[] annotations) {

    // TODO 待实现

    return (T) entity;
  }

  @Override
  public boolean hasEntity() {
    abortIfClosed();
    return entity != null;
  }

  @Override
  public boolean bufferEntity() {
    abortIfClosed();
    return bufferedEntity != null;
  }

  public void abortIfClosed() {
    if (bufferedEntity == null) {
      if (isClosed()) {
        throw new IllegalStateException("Response is closed.");
      }
    }
  }

  public boolean isClosed() {
    return isClosed;
  }


  @Override
  public void close() {
    isClosed = true;
  }

  @Override
  public MediaType getMediaType() {
    return this.mediaType;
  }

  @Override
  public Locale getLanguage() {
    return this.locale;
  }

  @Override
  public int getLength() {
    return 0;
  }

  @Override
  public Set<String> getAllowedMethods() {
    return null;
  }

  @Override
  public Map<String, NewCookie> getCookies() {
    return null;
  }

  @Override
  public EntityTag getEntityTag() {
    return null;
  }

  @Override
  public Date getDate() {
    return null;
  }

  @Override
  public Date getLastModified() {
    return null;
  }

  @Override
  public URI getLocation() {
    return null;
  }

  @Override
  public Set<Link> getLinks() {
    return null;
  }

  @Override
  public boolean hasLink(String relation) {
    return false;
  }

  @Override
  public Link getLink(String relation) {
    return null;
  }

  @Override
  public Builder getLinkBuilder(String relation) {
    return null;
  }

  @Override
  public MultivaluedMap<String, Object> getMetadata() {
    return null;
  }

  @Override
  public MultivaluedMap<String, String> getStringHeaders() {
    return null;
  }

  @Override
  public String getHeaderString(String name) {
    return null;
  }
}
