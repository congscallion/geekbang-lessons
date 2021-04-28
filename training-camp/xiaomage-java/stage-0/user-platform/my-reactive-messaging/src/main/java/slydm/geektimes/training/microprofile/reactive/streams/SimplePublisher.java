package slydm.geektimes.training.microprofile.reactive.streams;

import java.util.LinkedList;
import java.util.List;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/28 下午9:46
 */
public class SimplePublisher<T> implements Publisher<T> {

  private List<Subscriber> subscribers = new LinkedList<>();

  @Override
  public void subscribe(Subscriber<? super T> s) {
    SubscriptionAdapter subscription = new SubscriptionAdapter(s);
    s.onSubscribe(subscription);
    subscribers.add(subscription.getSubscriber());
  }


  public void publish(T data) {
    subscribers.forEach(subscriber -> {
      subscriber.onNext(data);
    });
  }

  public static void main(String[] args) {
    SimplePublisher publisher = new SimplePublisher();

    publisher.subscribe(new BusinessSubscriber(10));

    for (int i = 1; i <= 5; i++) {
      publisher.publish(i);
    }
  }

}
