package slydm.geektimes.training.microprofile.reactive.streams;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/28 下午9:26
 */
public class DefaultSubscriber<T> implements Subscriber<T> {

  private Subscription subscription;

  /**
   * 已处理数据量
   */
  private int processedCount = 0;

  @Override
  public void onSubscribe(Subscription s) {
    this.subscription = s;
  }

  @Override
  public void onNext(T t) {

    if (++processedCount >= 3) {
      subscription.cancel();
    }

    System.out.println("收到数据：" + t);
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
