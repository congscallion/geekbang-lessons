package slydm.geektimes.training.microprofile.rest.core.request;

import javax.ws.rs.core.CacheControl;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/9 17:21
 */
public class ExtendedCacheControl extends CacheControl {

  private boolean _public = false;

  public boolean isPublic() {
    return _public;
  }

  public void setPublic(boolean _public) {
    this._public = _public;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + (_public ? 1231 : 1237);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    ExtendedCacheControl other = (ExtendedCacheControl) obj;
    if (_public != other._public)
      return false;
    return true;
  }
}
