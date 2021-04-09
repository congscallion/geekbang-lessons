package slydm.geektimes.training.microprofile.rest.core.delegates;

import javax.ws.rs.core.Link;
import javax.ws.rs.ext.RuntimeDelegate;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class LinkDelegate implements RuntimeDelegate.HeaderDelegate<Link> {

   // TODO impl

   @Override
   public Link fromString(String value) {
      return null;
   }

   @Override
   public String toString(Link value) {
      return null;
   }
}
