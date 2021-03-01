package com.slydm.thinking.in.spring.aop.overview.cglib;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;
import net.sf.cglib.beans.BulkBean;
import net.sf.cglib.beans.ImmutableBean;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.core.KeyFactory;
import net.sf.cglib.core.Signature;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.InterfaceMaker;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.Mixin;
import net.sf.cglib.reflect.ConstructorDelegate;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import net.sf.cglib.reflect.MethodDelegate;
import net.sf.cglib.reflect.MulticastDelegate;
import net.sf.cglib.util.ParallelSorter;
import net.sf.cglib.util.StringSwitcher;
import org.objectweb.asm.Type;

/**
 * cglib 常用 api 示例
 *
 * @author wangcymy@gmail.com(wangcong) 2021/2/8 17:36
 * @see <a href="https://www.throwable.club/2018/12/16/cglib-api/</a>
 */
public class CglibCommonApiDemo {

  private static void helper() {

    // 该设置用于输出cglib动态代理产生的类
    System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\class");
    // 该设置用于输出jdk动态代理产生的类
    System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
  }

  public static void main(String[] args) throws Exception {
    // 记录 cglib 创建的字节码类
    helper();

    // 拦截所有方法并返回固定值
    simpleReturnDemo();

    // 根据方法签名拦截部分方法
    methodSignatureDemo();

    // 将对象包装成不可变化类
    immutableBeanDemo();

    // 创建类
    beanGeneratorDemo();

    // Bean 属性复制
    beanCopierDemo();

    //
    BulkBeanDemo();

    // 将 bean转换为 map
    beanMapDemo();

    // 生成复杂的key用于 hashmap,hashset等集合类
    KeyFactoryDemo();

    // Mixin能够让我们将多个接口的多个实现合并到同一个接口的单个实现。
    MixinDemo();

    // 用来模拟一个String到int类型的映射。如果在Java7以后的版本中，类似一个switch块的逻辑。
    StringSwitcherDemo();

    // 接口生成器，底层依赖ASM的相关API。
    InterfaceMakerDemo();

    //
    MethodDelegateDemo();

    //
    MulticastDelegateDemo();

    //
    ConstructorDelegateDemo();

    //
    ParallelSorterDemo();

    //
    FastClassDemo();

  }

  /**
   * FastClass就是对Class对象进行特定的处理，认知上可以理解为索引类，比如通过数组保存method引用，
   * 因此FastClass引出了一个index下标的新概念，比如getIndex(String name, Class[] parameterTypes)就是以前的获取method的方法。
   * 通过数组存储method，constructor等class信息，从而将原先的反射调用，转化为class.index的直接调用以提高效率，从而体现所谓的FastClass。
   */
  private static void FastClassDemo() throws InvocationTargetException {
    FastClass fastClass = FastClass.create(SampleBean.class);
    FastMethod setValue = fastClass.getMethod("setValue", new Class[]{String.class});
    SampleBean myBean = new SampleBean();
    setValue.invoke(myBean, new String[]{"this is value"});

    FastMethod getValue = fastClass.getMethod("getValue", new Class[0]);
    Object invoke = getValue.invoke(myBean, new Object[0]);
    System.out.println(invoke);
    System.out.println(getValue.getIndex());

    FastClass personServiceFastClass = FastClass.create(PersonService.class);
    Signature signature = new Signature("add", Type.INT_TYPE,
        new Type[]{Type.INT_TYPE, Type.INT_TYPE});
    PersonService service = new PersonService();
    int index = personServiceFastClass.getIndex(signature);
    Object add = personServiceFastClass.invoke(index, service, new Object[]{10, 20});
    System.out.println(add);

  }

  /**
   * 并行排序器，能够对多个数组同时进行排序，目前实现的算法有归并排序(mergeSort)和快速排序(quickSort)，
   * 查看源码的时候发现Float和Double类的比较直接用大于或者小于，有可能造成这两个类型的数据排序不准确(应该使用Float或Double的compare方法进行比较)。
   */
  private static void ParallelSorterDemo() {
    Integer[][] value = {{4, 3, 9, 0}, {2, 1, 6, 0}};
//    ParallelSorter.create(value).mergeSort(0); // 合并排序
    ParallelSorter.create(value).quickSort(0); // 快速排序

    System.out.println(Arrays.toString(value[0]));
    System.out.println(Arrays.toString(value[1]));
  }

  private static void ConstructorDelegateDemo() {
    SampleBeanConstructorDelegate constructorDelegate = (SampleBeanConstructorDelegate) ConstructorDelegate
        .create(SampleBean.class, SampleBeanConstructorDelegate.class);
    SampleBean sampleBean = (SampleBean) constructorDelegate.newInstance("abc");
    System.out.println(SampleBean.class.isAssignableFrom(sampleBean.getClass()));
    System.out.println(sampleBean.getValue());
  }

  private static void MulticastDelegateDemo() {
    MulticastDelegate multicastDelegate = MulticastDelegate
        .create(DelegatationProvider.class);
    MulticastBean first = new MulticastBean();
    MulticastBean second = new MulticastBean();
    multicastDelegate = multicastDelegate.add(first);
    multicastDelegate = multicastDelegate.add(second);
    DelegatationProvider provider = (DelegatationProvider) multicastDelegate;
    provider.setValue("Hello world!");
    System.out.println(first.getValue());
    System.out.println(second.getValue());
  }

  private static void MethodDelegateDemo() {
    SampleBean bean = new SampleBean();
    bean.setValue("RESULT");
    BeanDelegate delegate = (BeanDelegate) MethodDelegate.create(bean,
        "getValue", BeanDelegate.class);
    System.out.println(delegate.getValueFromDelegate());
  }

  /**
   * 上述的InterfaceMaker创建的接口中只含有一个方法，签名为double foo(int)。InterfaceMaker与上面介绍的其他类不同，它依赖ASM中的Type类型。
   * 由于接口仅仅只用做在编译时期进行类型检查，因此在一个运行的应用中动态的创建接口没有什么作用。
   * 但是InterfaceMaker可以用来自动生成接口代码，为以后的开发做准备
   */
  private static void InterfaceMakerDemo() {
    Signature signature = new Signature("foo", Type.DOUBLE_TYPE,
        new Type[]{Type.INT_TYPE, Type.FLOAT_TYPE});
    InterfaceMaker interfaceMaker = new InterfaceMaker();
    interfaceMaker.add(signature, new Type[0]);
    Class iface = interfaceMaker.create();
    Method[] methods = iface.getMethods();
    Method method = methods[0];
    System.out.printf("method %s signature: %s %s(%s) %n",
        method.getName(),
        method.getReturnType(),
        method.getName(),
        Arrays.stream(method.getParameterTypes()).map(s -> s.getName())
            .collect(Collectors.joining(","))
    );

    System.out.println(methods[0].toString());
  }

  private static void StringSwitcherDemo() {
    String[] keys = {"ninety", "eighty", "seventy", "sixty", "fifty", "forty", "thirty",
        "twenty", "ten"};
    int[] values = {10, 20, 30, 40, 50, 60, 70, 80, 90};
    StringSwitcher sw = StringSwitcher.create(keys, values, true);
    System.out.println(sw.intValue("ninety"));  // 10
    System.out.println(sw.intValue("forty")); // 60

    reverse(values);
    sw = StringSwitcher.create(keys, values, true);
    System.out.println(sw.intValue("ninety"));  // 90
    System.out.println(sw.intValue("forty")); // 40
  }

  private static void reverse(int[] arr) {
    int n = arr.length - 1;
    for (int j = (n - 1) >> 1; j >= 0; j--) {
      int k = n - j;
      int cj = arr[j];
      int ck = arr[k];
      arr[j] = ck;
      arr[k] = cj;
    }
  }

  private static void MixinDemo() {
    MixinInterface mixin = (MixinInterface) Mixin
        .create(new Class[]{Interface1.class, Interface2.class, MixinInterface.class},
            new Object[]{new Impl1(), new Impl2()});

    System.out.println(mixin.first());
    System.out.println(mixin.second());
  }

  /**
   * 通过生成类来处理多值键，以便在诸如Map和集合之类的东西中使用。equals和hashCode方法的代码遵循Joshua Bloch在《Effective Java》中列出的规则
   *
   * 什么叫multi-valued keys?
   * 就是有多个键的组合，一起作为一个Key。
   * 比如[a b c]是一个组合，一起作为一个key，[2 3]也可以是作为一个key。
   *
   * KeyFactory就是用来生成这样一组Key的，通过两组的equals，hashCode等方法判断是否为同一组key的场景。
   * 为了描述Key的组合，需要定义一个接口，仅提供一个方法，叫做newInstance()，且返回值为Object，这个是使用KeyFactory的要求。
   */
  private static void KeyFactoryDemo() {
    KeyFactoryInterface keyFactory = (KeyFactoryInterface) KeyFactory
        .create(KeyFactoryInterface.class);
    Object key1 = keyFactory.newInstance("key1");
    HashMap<Object, String> map = new HashMap<>();
    map.put(key1, "abc");
    System.out.println(map.get(key1)); // abc
    System.out.println(map.containsKey(keyFactory.newInstance("key1"))); // true
    System.out.println(map.containsKey(
        ((KeyFactoryInterface) KeyFactory.create(KeyFactoryInterface.class))
            .newInstance("key1"))); // true

    A a = new A();
    B b = new B();
    map.put(((KeyFactoryInterface2) KeyFactory.create(KeyFactoryInterface2.class))
        .newInstance(a, b), "a-b");
    System.out
        .println(map.get(((KeyFactoryInterface2) KeyFactory.create(KeyFactoryInterface2.class))
            .newInstance(a, b))); // a-b
    System.out
        .println(map.get(((KeyFactoryInterface2) KeyFactory.create(KeyFactoryInterface2.class))
            .newInstance(new A(), new B()))); // null, 创建key的对象变化, key 随着变化
  }

  /**
   * BeanMap类实现了JDK的java.util.Map接口，将一个JavaBean对象中的所有属性转换为一个String-To-Object的Map实例。
   */
  private static void beanMapDemo() {
    A a = new A();
    a.setA("a");
    a.setB(1);
    a.setC(2d);
    a.setD(new Date());
    a.setIds(Arrays.asList(1, 2, 3));
    BeanMap beanMap = BeanMap.create(a);
    System.out.println(beanMap);
    a.setA("a v2");
    beanMap.put("b", 2);
    System.out.println(beanMap.get("a"));
    System.out.println(beanMap.get("b"));
    System.out.println(beanMap);
  }

  /**
   * 相比于BeanCopier，BulkBean创建时候依赖于确定的目标类型，Setter和Getter方法名称列表以及参数类型，
   * 它将copy的动作拆分为getPropertyValues()和setPropertyValues()两个方法，允许自定义处理属性。
   */
  private static void BulkBeanDemo() {
    BulkBean bulkBean = BulkBean.create(SampleBean.class,
        new String[]{"getValue"}, new String[]{"setValue"},
        new Class[]{String.class});
    SampleBean bean = new SampleBean();
    bean.setValue("RESULT");
    Object[] propertyValues = bulkBean.getPropertyValues(bean);
    System.out.println(propertyValues);

    bulkBean.setPropertyValues(bean, new Object[]{"NEW_RESULT"});
    System.out.println(bean.getValue());
  }


  /**
   * JavaBean属性拷贝器，提供从一个JavaBean实例中拷贝属性到另一个JavaBean实例中.
   * 注意类型必须完全匹配,属性才能拷贝成功(原始类型和其包装类不属于相同类型)。
   * 它还提供了一个net.sf.cglib.core.Converter转换器回调接口让使用者控制拷贝的过程。
   * 注意，BeanCopier内部使用了缓存和基于ASM动态生成BeanCopier的子类实现的转换方法中直接使用实例的Getter和Setter方法，
   * 拷贝速度极快(BeanCopier属性拷贝比直接的Setter、Getter稍慢，稍慢的原因在于首次需要动态生成BeanCopier的子类，
   * 一旦子类生成完成之后就和直接的Setter、Getter效率一致，但是效率远远高于其他使用反射的工具类库)。
   */
  private static void beanCopierDemo() {
    BeanCopier copier = BeanCopier.create(A.class, B.class, false);
    A a = new A();
    a.setA("a");
    a.setB(1);
    a.setC(2d);
    a.setD(new Date());
    a.setIds(Arrays.asList(1, 2, 3));
    HashMap<String, String> entryMap = new HashMap<>();
    entryMap.put("abc", "def");
    a.setEntryMap(entryMap);
    B b = new B();
    copier.copy(a, b, null);
    System.out.println(b.getA());
    System.out.println(b.getB());
    System.out.println(b.getC());
    System.out.println(b.getD());
    System.out.println(b.getIds());
    System.out.println(b.getEntryMap());

  }

  /**
   * 动态创建一个类，并为类添加 name 属性以及自动创建 getter setter 方法。
   */
  private static void beanGeneratorDemo()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    BeanGenerator beanGenerator = new BeanGenerator();
    beanGenerator.addProperty("name", String.class);
    Object myBean = beanGenerator.create();
    Method setter = myBean.getClass().getMethod("setName", String.class);
    setter.invoke(myBean, "some string value set by a cglib");
    Method getter = myBean.getClass().getMethod("getName");
    System.out.println(getter.invoke(myBean));
  }

  private static void immutableBeanDemo() {
    SampleBean bean = new SampleBean();
    bean.setValue("abc");
    SampleBean immutableBean = (SampleBean) ImmutableBean.create(bean);
    System.out.println(immutableBean.getValue());
    bean.setValue("def");
    System.out.println(immutableBean.getValue());
    try {
      immutableBean.setValue("abc"); // exception
    } catch (IllegalStateException ex) {
      System.err.println("err: " + ex.getMessage());
    }
  }

  private static void methodSignatureDemo() {
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(PersonService.class);
    enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
      if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
        return "Hello Tom!";
      } else {
        return proxy.invokeSuper(obj, args);
      }
    });
    PersonService proxy = (PersonService) enhancer.create();
    System.out.println(proxy.sayHello());

    int add = proxy.add(1, 2);
    System.out.println(add);

    int i = proxy.hashCode();
    System.out.println(i);

  }

  /**
   * 拦截所有方法并返回固定值。 当该返回值与方法声明的返回值无法转换时，将产生 ClassCastException
   */
  private static void simpleReturnDemo() {
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(PersonService.class);
    enhancer.setCallback((FixedValue) () -> "hello by cglib");
    PersonService personService = (PersonService) enhancer.create();
    String msg = personService.sayHello();
    System.out.println(msg);
    try {
      // add 方法返回值为int, Callback 返回固定值: "hello by cglib", 不能转换成int, error.
      int add = personService.add(1, 2);
      System.out.println(add);
    } catch (ClassCastException ex) {
      System.err.println("err: " + ex.getMessage());
    }

  }

  public static class PersonService {

    public String sayHello() {
      return "hello by sayHello";
    }

    public int add(int a, int b) {
      return a + b;
    }
  }

  public static class SampleBean {

    private String value;

    public SampleBean() {
    }

    public SampleBean(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }
  }

  public static class A {

    private String a;
    private int b;
    private double c;
    private Date d;
    private List<Integer> ids;
    private Map<String, String> entryMap;

    public String getA() {
      return a;
    }

    public void setA(String a) {
      this.a = a;
    }

    public int getB() {
      return b;
    }

    public void setB(int b) {
      this.b = b;
    }

    public double getC() {
      return c;
    }

    public void setC(double c) {
      this.c = c;
    }

    public Date getD() {
      return d;
    }

    public void setD(Date d) {
      this.d = d;
    }

    public List<Integer> getIds() {
      return ids;
    }

    public void setIds(List<Integer> ids) {
      this.ids = ids;
    }

    public Map<String, String> getEntryMap() {
      return entryMap;
    }

    public void setEntryMap(Map<String, String> entryMap) {
      this.entryMap = entryMap;
    }
  }

  public static class B {

    private String a;
    private int b;
    private double c;
    private Date d;
    private List<Integer> ids;
    private Map<String, String> entryMap;

    public String getA() {
      return a;
    }

    public void setA(String a) {
      this.a = a;
    }

    public int getB() {
      return b;
    }

    public void setB(int b) {
      this.b = b;
    }

    public double getC() {
      return c;
    }

    public void setC(double c) {
      this.c = c;
    }

    public Date getD() {
      return d;
    }

    public void setD(Date d) {
      this.d = d;
    }

    public List<Integer> getIds() {
      return ids;
    }

    public void setIds(List<Integer> ids) {
      this.ids = ids;
    }

    public Map<String, String> getEntryMap() {
      return entryMap;
    }

    public void setEntryMap(Map<String, String> entryMap) {
      this.entryMap = entryMap;
    }
  }

  public static interface KeyFactoryInterface {

    Object newInstance(String arg1);
  }

  public static interface KeyFactoryInterface2 {

    Object newInstance(Object arg1, Object arg2);
  }

  public static interface MixinInterface extends Interface1, Interface2 {

  }

  public static interface Interface1 {

    String first();
  }

  public static class Impl1 implements Interface1 {

    @Override
    public String first() {
      return "first";
    }
  }

  public static interface Interface2 {

    String second();
  }


  public static class Impl2 implements Interface2 {

    @Override
    public String second() {
      return "second";
    }
  }

  public static interface BeanDelegate {

    String getValueFromDelegate();
  }

  public static interface DelegatationProvider {

    void setValue(String value);
  }

  public static class MulticastBean implements DelegatationProvider {

    private String value;

    @Override
    public void setValue(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  public static interface SampleBeanConstructorDelegate {

    Object newInstance(String value);
  }

}
