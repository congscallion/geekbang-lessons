package slydm.geektimes.training.microprofile.reactive.streams;

import java.util.ArrayList;
import java.util.List;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/28 下午9:26
 */
public class DefaultPublisher<T> implements Publisher<T> {

  private List<Subscriber> subscriberList = new ArrayList<>();

  @Override
  public void subscribe(Subscriber<? super T> s) {

    DefaultSubscription subscription = new DefaultSubscription(s);
    s.onSubscribe(subscription);
    subscriberList.add(new SubscriberWrapper(s, subscription));
  }


  /**
   * 发布数据
   *
   * @param data
   */
  public void publish(T data) {

    for (Subscriber subscriber : subscriberList) {

      SubscriberWrapper subscriberWrapper = (SubscriberWrapper) subscriber;

      DefaultSubscription subscription = subscriberWrapper.getSubscription();
      if (subscription.isCancel()) {
        continue;
      }

      subscriber.onNext(data);
    }
  }


  public void error(Throwable error) {
    // 广播
    subscriberList.forEach(subscriber -> {
      subscriber.onError(error);
    });
  }

  public void complete() {
    // 广播
    subscriberList.forEach(subscriber -> {
      subscriber.onComplete();
    });
  }

  public static void main(String[] args) {

    DefaultPublisher publisher = new DefaultPublisher();

    DefaultSubscriber subscriber = new DefaultSubscriber();
    publisher.subscribe(subscriber);

    for (int i = 1; i <= 5; i++) {
      publisher.publish(i);
    }
  }


}
