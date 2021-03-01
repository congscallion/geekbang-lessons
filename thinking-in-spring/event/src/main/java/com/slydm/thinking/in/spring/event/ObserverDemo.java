package com.slydm.thinking.in.spring.event;

import java.util.Observable;
import java.util.Observer;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/1/13 8:43
 */
public class ObserverDemo {

  public static void main(String[] args) {

    Observable observable = new EventObservable();
    observable.addObserver(new EventObserver());
    observable.notifyObservers("hello,world!");

  }


  static class EventObservable extends Observable {

    @Override
    public void notifyObservers(Object arg) {

      super.setChanged();
      super.notifyObservers(arg);
    }
  }


  static class EventObserver implements Observer {

    @Override
    public void update(Observable o, Object event) {
      System.out.println("收到事件: " + event);
    }
  }

}
