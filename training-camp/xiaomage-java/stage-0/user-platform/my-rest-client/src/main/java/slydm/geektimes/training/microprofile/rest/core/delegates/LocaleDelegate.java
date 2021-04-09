package slydm.geektimes.training.microprofile.rest.core.delegates;

import java.util.Locale;
import javax.ws.rs.ext.RuntimeDelegate;
import slydm.geektimes.training.microprofile.rest.util.LocaleHelper;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class LocaleDelegate implements RuntimeDelegate.HeaderDelegate<Locale> {

   public static final LocaleDelegate INSTANCE = new LocaleDelegate();

   public Locale fromString(String value) throws IllegalArgumentException {
      if (value == null) {
         throw new IllegalArgumentException("Locale value is null");
      }
      return LocaleHelper.extractLocale(value);
   }

   public String toString(Locale value) {
      if (value == null) {
         throw new IllegalArgumentException("param was null");
      }
      return LocaleHelper.toLanguageString(value);
   }

}
