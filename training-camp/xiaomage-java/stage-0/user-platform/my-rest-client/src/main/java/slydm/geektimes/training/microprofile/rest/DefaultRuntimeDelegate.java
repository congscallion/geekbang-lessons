package slydm.geektimes.training.microprofile.rest;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Link.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Variant.VariantListBuilder;
import javax.ws.rs.ext.RuntimeDelegate;
import slydm.geektimes.training.microprofile.rest.client.MyUriBuilderImpl;
import slydm.geektimes.training.microprofile.rest.core.delegates.CacheControlDelegate;
import slydm.geektimes.training.microprofile.rest.core.delegates.CookieHeaderDelegate;
import slydm.geektimes.training.microprofile.rest.core.delegates.DateDelegate;
import slydm.geektimes.training.microprofile.rest.core.delegates.EntityTagDelegate;
import slydm.geektimes.training.microprofile.rest.core.delegates.LocaleDelegate;
import slydm.geektimes.training.microprofile.rest.core.delegates.MediaTypeHeaderDelegate;
import slydm.geektimes.training.microprofile.rest.core.delegates.NewCookieHeaderDelegate;
import slydm.geektimes.training.microprofile.rest.core.delegates.UriHeaderDelegate;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/9 16:11
 */
public class DefaultRuntimeDelegate extends RuntimeDelegate {

  protected Map<Class<?>, HeaderDelegate> headerDelegates = new HashMap<>(16);

  public DefaultRuntimeDelegate() {
    init();
  }

  private void init() {
    headerDelegates.put(MediaType.class, MediaTypeHeaderDelegate.INSTANCE);
    headerDelegates.put(NewCookie.class, NewCookieHeaderDelegate.INSTANCE);
    headerDelegates.put(Cookie.class, CookieHeaderDelegate.INSTANCE);
    headerDelegates.put(URI.class, UriHeaderDelegate.INSTANCE);
    headerDelegates.put(EntityTag.class, EntityTagDelegate.INSTANCE);
    headerDelegates.put(CacheControl.class, CacheControlDelegate.INSTANCE);
    headerDelegates.put(Locale.class, LocaleDelegate.INSTANCE);
    headerDelegates.put(Date.class, DateDelegate.INSTANCE);

  }


  @Override
  public UriBuilder createUriBuilder() {
    return new MyUriBuilderImpl();
  }

  @Override
  public ResponseBuilder createResponseBuilder() {
    return null;
  }

  @Override
  public VariantListBuilder createVariantListBuilder() {
    return null;
  }

  @Override
  public <T> T createEndpoint(Application application, Class<T> endpointType)
      throws IllegalArgumentException, UnsupportedOperationException {
    return null;
  }

  @Override
  public <T> HeaderDelegate<T> createHeaderDelegate(Class<T> tClass) throws IllegalArgumentException {

    return headerDelegates.get(tClass);
  }

  @Override
  public Builder createLinkBuilder() {
    return null;
  }
}
