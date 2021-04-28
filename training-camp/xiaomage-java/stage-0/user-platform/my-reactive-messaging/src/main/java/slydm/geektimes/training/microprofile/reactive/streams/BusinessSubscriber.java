package slydm.geektimes.training.microprofile.reactive.streams;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/28 下午9:43
 */
public class BusinessSubscriber<T> implements Subscriber<T> {

  private Subscription subscription;

  private int processedCount = 0;
  private final long maxRequest;

  public BusinessSubscriber(long maxRequest) {
    this.maxRequest = maxRequest;
  }

  @Override
  public void onSubscribe(Subscription s) {
    this.subscription = s;
    this.subscription.request(maxRequest);
  }

  @Override
  public void onNext(T t) {
    System.out.println("收到数据：" + t);

    // 模拟背压
    if (++processedCount >= 3) {
      subscription.cancel();
    }
  }

  @Override
  public void onError(Throwable t) {
    System.out.println("遇到异常：" + t);
  }

  @Override
  public void onComplete() {
    System.out.println("收到数据完成");
  }
}
