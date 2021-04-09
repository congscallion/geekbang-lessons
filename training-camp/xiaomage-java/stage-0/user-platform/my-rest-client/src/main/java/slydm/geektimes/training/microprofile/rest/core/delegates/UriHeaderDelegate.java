package slydm.geektimes.training.microprofile.rest.core.delegates;

import java.net.URI;
import javax.ws.rs.ext.RuntimeDelegate;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class UriHeaderDelegate implements RuntimeDelegate.HeaderDelegate<URI> {

   public static final UriHeaderDelegate INSTANCE = new UriHeaderDelegate();

   public URI fromString(String value) throws IllegalArgumentException {
      if (value == null) {
         throw new IllegalArgumentException("URI value is null");
      }
      return URI.create(value);
   }

   public String toString(URI value) {
      if (value == null) {
         throw new IllegalArgumentException("param was null");
      }
      URI uri = (URI) value;
      return uri.toASCIIString();
   }
}
