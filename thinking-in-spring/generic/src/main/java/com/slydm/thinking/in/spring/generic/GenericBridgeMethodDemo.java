package com.slydm.thinking.in.spring.generic;

/**
 * 泛型桥方法示例
 *
 * @author wangcymy@gmail.com(wangcong) 2021/1/11 15:52
 */
public class GenericBridgeMethodDemo {

  public static void main(String[] args) {

    MyNode mn = new MyNode(5);
    Node n = mn;
    n.setData("Hello");
    Integer x = mn.getData();
    System.out.println(x);
  }


}

class Node<T> {

  private T data;
  private Node<T> next;

  public Node() {
  }

  public Node(T data) {
    this.data = data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public T getData() {
    return data;
  }

}


class MyNode extends Node<Integer> {

  private Integer data;
  private Node<Integer> next;

  public MyNode() {
  }

  public MyNode(Integer data) {
    this.data = data;
  }

  @Override
  public Integer getData() {
    return data;
  }
//    编译器生成的桥接方法
//    public Object getData(){
//      return getData();
//    }


  @Override
  public void setData(Integer data) {
    this.data = data;
  }

//   编译器生成的桥接方法
//    public void setData(Object data){
//      setData((Integer) data);
//    }

}