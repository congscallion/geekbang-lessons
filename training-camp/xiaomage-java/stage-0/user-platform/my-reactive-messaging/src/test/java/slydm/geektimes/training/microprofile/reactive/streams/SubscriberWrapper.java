package slydm.geektimes.training.microprofile.reactive.streams;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/28 下午9:26
 */
public class SubscriberWrapper<T> implements Subscriber<T> {

  private Subscriber subscriber;
  private DefaultSubscription subscription;

  public SubscriberWrapper(Subscriber subscriber, DefaultSubscription subscription) {
    this.subscription = subscription;
    this.subscriber = subscriber;
  }

  @Override
  public void onSubscribe(Subscription s) {
    subscriber.onSubscribe(s);
  }

  public void onNext(Object o) {
    subscriber.onNext(o);
  }

  @Override
  public void onError(Throwable t) {
    subscriber.onError(t);
  }

  @Override
  public void onComplete() {
    subscriber.onComplete();
  }

  public DefaultSubscription getSubscription() {
    return subscription;
  }

}
