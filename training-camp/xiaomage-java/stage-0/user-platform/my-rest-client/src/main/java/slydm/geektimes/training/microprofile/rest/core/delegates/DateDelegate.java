package slydm.geektimes.training.microprofile.rest.core.delegates;

import java.util.Date;
import javax.ws.rs.ext.RuntimeDelegate;
import slydm.geektimes.training.microprofile.rest.util.DateUtil;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class DateDelegate implements RuntimeDelegate.HeaderDelegate<Date> {

   public static final DateDelegate INSTANCE = new DateDelegate();

   @Override
   public Date fromString(String value) {
      if (value == null) {
         throw new IllegalArgumentException("param was null");
      }
      return DateUtil.parseDate(value);
   }

   @Override
   public String toString(Date value) {
      if (value == null) {
         throw new IllegalArgumentException("param was null");
      }
      return DateUtil.formatDate(value);
   }
}
