package com.slydm.thinking.in.spring.data.binding;

import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

/**
 * {@link DataBinder} 示例
 *
 * <p>
 * {@link DataBinder} 默认特性:
 * <li>忽略未知的属性
 * <li>支持嵌套属性
 * <li>ignoreUnknownFields: 是否忽略未知属性,即数据源存在,绑定对象不存在. 默认 true.
 * <li>autoGrowNestedPaths: 是否支持嵌套路径,比如: company.name. 默认 true.
 * <li>ignoreInvalidFields: 是否忽略无效的字段,比如不支持嵌套路径时却出现了嵌套路径. 默认 false.
 * <li>requiredFields: 必须要绑定的字段,当数据源中不存在时,不会异常但错误信息会设置到 BindingResult 对象中.
 * <li>disallowedFields: 不允许绑定的字段
 * <li>allowedFields: 允许绑定的字段
 *
 * @author wangcymy@gmail.com(wangcong) 2021/1/8 10:58
 */
public class DataBinderDemo {

  public static void main(String[] args) {

//    defaultSpeciality();
    customSpeciality();

  }

  private static void customSpeciality() {

    // 创建数组绑定的目标对象
    User user = new User();
    DataBinder dataBinder = new DataBinder(user, "");

    // 创建数据元
    Map<String, Object> source = new LinkedHashMap<>();
    source.put("id", 10L);
    source.put("name", "wangcong");
    source.put("company.name", "Amazon");
    source.put("age", 20);
    source.put("beanName", "user");
    source.put("city", "BEIJING");

//    Company company = new Company();
//    company.setName("Amazon");
//    source.put("company", company);

    MutablePropertyValues propertyValues = new MutablePropertyValues(source);

    // 不忽略未知属性 -> age 属性在 user 中不存在，这样设置后，绑定会失败
    // NotWritablePropertyException
    // dataBinder.setIgnoreUnknownFields(false);

    // 调整自动增加嵌套路径,即是否支持嵌套路径, 默认 true.  设置 false 后, company.name 将不能被赋值且会异常
    // 不自动扩展嵌套路径时,将 ignoreInvalidFields 设置为 true时, 嵌套路径将被忽略,不会异常.
    // NullValueInNestedPathException
    dataBinder.setAutoGrowNestedPaths(false);

    // 忽略无效的字段(比如不支持嵌套路径时,元数据中存在嵌套路径)
    dataBinder.setIgnoreInvalidFields(true);

    // 设置必须存在的字段, 不会抛出异常, 错误信息通过 getBindingResult() 获取.
    dataBinder.setRequiredFields("id", "name", "city");

    // 设置不允许绑定的属性
//    dataBinder.setAllowedFields("id", "name", "city","company.name");
    dataBinder.setDisallowedFields("beanName");

    // 进行数据绑定
    dataBinder.bind(propertyValues);

    // 查询绑定结果
    System.out.println(user);

    BindingResult bindingResult = dataBinder.getBindingResult();
    if (bindingResult.hasErrors()) {
      System.out.println(bindingResult);
    }

  }


  private static void defaultSpeciality() {
    // 创建数组绑定的目标对象
    User user = new User();
    DataBinder dataBinder = new DataBinder(user, "");

    // 创建数据元
    Map<String, Object> source = new LinkedHashMap<>();
    source.put("id", 10L);
    source.put("name", "wangcong");
    source.put("company.name", "Amazon");
    source.put("age", 20);
    MutablePropertyValues propertyValues = new MutablePropertyValues(source);

    // 进行数据绑定
    dataBinder.bind(propertyValues);

    // 查询绑定结果
    System.out.println(user);
  }

}
