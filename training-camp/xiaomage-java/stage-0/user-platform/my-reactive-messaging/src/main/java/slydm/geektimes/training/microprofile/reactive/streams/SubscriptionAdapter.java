package slydm.geektimes.training.microprofile.reactive.streams;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/28 下午9:47
 */
public class SubscriptionAdapter implements Subscription {

  private final DecoratingSubscriber<?> subscriber;

  public SubscriptionAdapter(Subscriber<?> subscriber) {
    this.subscriber = new DecoratingSubscriber(subscriber);
  }

  @Override
  public void request(long n) {

    if (n < 1) {
      throw new IllegalArgumentException(
          "The number of elements to requests must be more than zero!");
    }

    this.subscriber.setMaxRequest(n);
  }

  @Override
  public void cancel() {
    this.subscriber.cancel();
    this.subscriber.onComplete();
  }

  public Subscriber getSubscriber() {
    return subscriber;
  }
}
