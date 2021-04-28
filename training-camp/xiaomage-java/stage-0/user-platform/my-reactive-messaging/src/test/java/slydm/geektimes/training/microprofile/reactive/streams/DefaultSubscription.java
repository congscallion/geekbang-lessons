package slydm.geektimes.training.microprofile.reactive.streams;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/28 下午9:26
 */
public class DefaultSubscription implements Subscription {

  private Subscriber subscriber;

  private boolean cancel = false;

  public DefaultSubscription(Subscriber subscriber) {
    this.subscriber = subscriber;
  }

  @Override
  public void request(long n) {

  }

  @Override
  public void cancel() {
    this.cancel = true;
  }

  public boolean isCancel() {
    return cancel;
  }
}
