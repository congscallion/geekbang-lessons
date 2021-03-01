package com.slydm.thinking.in.spring.generic;

import java.util.LinkedList;
import java.util.List;

/**
 * Java 泛型 类型擦除
 *
 * <p>
 * Java语言引入了泛型，以在编译时提供更严格的类型检查并支持泛型编程。为了实现泛型，Java编译器将类型擦除应用于：
 * <li>如果类型参数不受限制，则将通用类型中的所有类型参数替换为其边界或{@link Object}。因此，产生的字节码仅包含普通的类，接口和方法。
 * <li>在类型擦除过程中，Java编译器将擦除所有类型参数，如果类型参数是有界的，则将每个参数替换为其第一个边界；如果类型参数是无界的，则将其替换为{@link Object}。
 * <li>必要时插入类型转换，以保持类型安全。
 * <li>生成桥接方法以在扩展的泛型类型中保留多态。
 *
 * <p>当类型参数为无界时: <br>
 * <pre class="code">
 *  static class GenericContainer&lt;T&gt; {
 *     private T obj;
 *     public GenericContainer() {
 *     }
 *     public GenericContainer(T obj) {
 *       this.obj = obj;
 *     }
 *     public T getObj() {
 *       return obj;
 *     }
 *     public void setObj(T obj) {
 *       this.obj = obj;
 *     }
 *   }
 * </pre>
 *
 * 无论通过以下哪种方试声明创建 {@link GenericContainer} 实例
 * <pre class="code">
 *  GenericContainer container1 = new GenericContainer();
 *  GenericContainer&lt;Double&gt; container2 = new GenericContainer&lt;&gt;();
 *  GenericContainer&lt;Double&gt; container3 = new GenericContainer&lt;&gt;();
 * </pre>
 *
 * java 编译器都将使用 {@link java.lang.Object} 替换 {@link GenericContainer}中类型参数 T
 * <pre class="code">
 *  static class GenericContainer {
 *     private Object obj;
 *     public GenericContainer() {
 *     }
 *     public GenericContainer(Object obj) {
 *       this.obj = obj;
 *     }
 *
 *     public Object getObj() {
 *       return obj;
 *     }
 *
 *     public void setObj(Object obj) {
 *       this.obj = obj;
 *     }
 *   }
 * </pre>
 * 但是get(xx)代码会触发编译器插入类型转换代码，以保持类型安全。
 * <pre class="code">
 *    Double result = container2.getObj();
 *    // 由于编译器在编译阶段将类型参数 T 替换成 {@link Object} 了。因此 getObj()方法的返回值是 {@link Object}.
 *    // 所以编译后，上面的代码实际上等价于
 *    Object temp = container2.getObj();
 *    Double result = (Double) temp;
 *    // 即
 *    Double result = (Double)container2.getObj();
 *  </pre>
 *
 *
 * <p>
 * 当类型参数为有界时: <br>
 * <pre class="code">
 *   static class GenericContainer2&lt;T extends Comparable&lt;T&gt;&gt; {
 *     private T obj;
 *     public GenericContainer2() {
 *     }
 *     public GenericContainer2(T obj) {
 *       this.obj = obj;
 *     }
 *     public T getObj() {
 *       return obj;
 *     }
 *     public void setObj(T obj) {
 *       this.obj = obj;
 *     }
 *   }
 * </pre>
 * java 编译器将使用第一个边界即 {@link Comparable} 替换 {@link GenericContainer}中类型参数 T
 * <pre class="code">
 *   static class GenericContainer2{
 *     private Comparable obj;
 *     public GenericContainer2() {
 *     }
 *     public GenericContainer2(Comparable obj) {
 *       this.obj = obj;
 *     }
 *     public Comparable getObj() {
 *       return obj;
 *     }
 *     public void setObj(Comparable obj) {
 *       this.obj = obj;
 *     }
 *   }
 * </pre>
 *
 * @author wangcymy@gmail.com(wangcong) 2021/1/11 10:04
 * @see GenericContainer
 */
public class TypeErasureDemo {

  /**
   * 此时， 类型参数 T 是无界的， 因此编译时将使用 Object 替换 T。
   */
  public static <T> int count(T[] arr, T elem) {
    int count = 0;
    for (T e : arr) {
      if (e.equals(elem)) {
        ++count;
      }
    }
    return count;
  }
// 上面的方法经过编译器类型擦除后， 就是本法方法。
//  public static int count(Object[] arr, Object elem) {
//    int count = 0;
//    for (Object e : arr) {
//      if (e.equals(elem)) {
//        ++count;
//      }
//    }
//    return count;
//  }


  /**
   * 此时， 类型参数 T 是有界的， 因此编译器使用第一个有界参数 Number 替换 T。
   */
  public static <T extends Number> int count(T[] arr, T elem) {
    int count = 0;
    for (T e : arr) {
      if (e.equals(elem)) {
        ++count;
      }
    }
    return count;
  }
// 上面的方法经过编译器类型擦除后， 就是本法方法。
//  public static int count(Number[] arr, Number elem) {
//    int count = 0;
//    for (Number e : arr) {
//      if (e.equals(elem)) {
//        ++count;
//      }
//    }
//    return count;
//  }


  public static void main(String[] args) {

    GenericContainer container = new GenericContainer();
    container.setObj("obj");
    Object obj = container.getObj();
    System.out.println(obj);

    GenericContainer<Double> container1 = new GenericContainer<>();
    container1.setObj(0.2D);
    Double result = container1.getObj();
    System.out.println(result);

    GenericContainer<Integer> container2 = new GenericContainer(1);
    container2.setObj(2);
    Integer result2 = container2.getObj();
    System.out.println(result2);

    /**
     *
     * 泛型欺骗
     *
     * 由于 GenericContainer 中的类型参数 T 是无界的。即 使用　Object 替换。
     *
     * 所以 container3.setObj(0.2D); 是可以的。 因为都是使用 Object 对象接收参数值。
     *  Object result3 = container3.getObj(); 不会插入类型转换代码。
     *
     * 这种泛型欺骗实际上影响不大，不会产生运行时异常。
     *
     */
    GenericContainer container3 = container2;
    container3.setObj(0.2D);
    Object result3 = container3.getObj();
    System.out.println(result3);

    /**
     * 以下这个泛型欺骗示例，编译可以通过，但是会产生运行时异常 {@link ClassCastException}.
     * 即： java.lang.String cannot be cast to java.lang.Integer
     * 原因是 Integer integer = list.get(2); 会触发编译器插入类型转换代码。
     * Object temp = list.get(2);
     * Integer integer = (Integer) temp;
     *
     * =>
     * Integer integer = (Integer) list.get(2);
     *
     */
    List<Integer> list = new LinkedList<>();
    list.add(1);
    list.add(2);
    List list2 = list;
    list2.add("abc");
    System.out.println(list.size()); //3

    // 但是会产生运行时异常 {@link ClassCastException}. 即： java.lang.String cannot be cast to java.lang.Integer
    // 原因是这行代码会触发编译器插入类型转换代码。
    // Object temp = list.get(2); // 实际类型是 String
    // Integer integer = (Integer) temp;
    //=>
    // Integer integer = (Integer) list.get(2);
    // Integer integer = list.get(2);
    // System.out.println(integer);

  }

  /**
   * 无界类型参数
   */
  static class GenericContainer<T> {

    private T obj;

    public GenericContainer() {
    }


    public GenericContainer(T obj) {
      this.obj = obj;
    }

    public T getObj() {
      return obj;
    }

    public void setObj(T obj) {
      this.obj = obj;
    }
  }

  /**
   * 有界类型参数
   */
  static class GenericContainer2<T extends Comparable<T>> {

    private T obj;

    public GenericContainer2() {
    }

    public GenericContainer2(T obj) {
      this.obj = obj;
    }

    public T getObj() {
      return obj;
    }

    public void setObj(T obj) {
      this.obj = obj;
    }

  }


}
