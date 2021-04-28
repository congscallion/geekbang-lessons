package slydm.geektimes.training.microprofile.reactive.streams;

import java.util.logging.Logger;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * 这个类设计得丰常好，仔得学习
 *
 * @author wangcymy@gmail.com(wangcong) 2021/4/28 下午9:53
 */
public class DecoratingSubscriber<T> implements Subscriber<T> {

  private final Logger logger;

  private Subscriber<T> source;

  private long maxRequest = -1;
  private long processedCount = 0;

  private boolean canceled = false;
  private boolean completed = false;

  public DecoratingSubscriber(Subscriber<T> source) {
    this.source = source;
    this.logger = Logger.getLogger(source.getClass().getName());
  }

  @Override
  public void onSubscribe(Subscription s) {
    source.onSubscribe(s);
  }

  @Override
  public void onNext(T t) {

    assertRequest();

    if (isCompleted()) {
      logger
          .severe("The data subscription was completed, This method should not be invoked again!");
      return;
    }

    if (isCanceled()) { // Indicates that the Subscriber invokes Subscription#cancel() method.
      logger.warning(String.format("The Subscriber has canceled the data subscription," +
          " current data[%s] will be ignored!", t));
      return;
    }

    if (processedCount == maxRequest && maxRequest < Long.MAX_VALUE) {
      onComplete();
      logger.warning(String.format("The number of requests is up to the max threshold[%d]," +
          " the data subscription is completed!", maxRequest));
      return;
    }

    next(t);

  }

  private void next(T t) {
    try {
      source.onNext(t);
    } catch (Throwable e) {
      onError(e);
    } finally {
      processedCount++;
    }
  }


  private void assertRequest() {
    if (maxRequest < 1) {
      throw new IllegalStateException("the number of request must be initialized before " +
          "Subscriber#onNext(Object) method, please set the positive number to " +
          "Subscription#request(int) method on Publisher#subscribe(Subscriber) phase.");
    }
  }


  @Override
  public void onError(Throwable t) {
    source.onError(t);
  }

  @Override
  public void onComplete() {
    source.onComplete();
    completed = true;
  }


  public void setMaxRequest(long maxRequest) {
    this.maxRequest = maxRequest;
  }

  public void cancel() {
    canceled = true;
  }

  public boolean isCompleted() {
    return completed;
  }

  public boolean isCanceled() {
    return canceled;
  }

}
