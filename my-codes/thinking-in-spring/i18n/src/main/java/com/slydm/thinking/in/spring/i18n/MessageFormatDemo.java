package com.slydm.thinking.in.spring.i18n;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * {@link MessageFormat} 示例
 *
 * Note:
 * <p>
 * <li>{@link MessageFormat} 实例一经创建，再设置 {@link Locale} 无效。要创建指定的 Locale MessageFormat
 * 可以使用带 {@link Locale} 参数的构造器方法。
 * <li>{@link MessageFormat#applyPattern(String)} 方法可以重用 {@link MessageFormat} 实例，但新的 pattern
 * 如需指定 {@link Locale} 需要在调用该方法之前才效。
 *
 * @author wangcymy@gmail.com(wangcong) 2020/12/31 9:31
 * @see MessageFormat
 */
public class MessageFormatDemo {

  public static void main(String[] args) {

    int planet = 7;
    String event = "a disturbance in the Force";

    String result = MessageFormat
        .format("At {1,time} on {1,date}, there was {2} on planet {0,number,integer}.",
            planet, new Date(), event);
    System.out.println(result);

    // 带样式的格式化
    result = MessageFormat
        .format("At {1,time,short} on {1,date,full}, there was {2} on planet {0,number,integer}.",
            planet, new Date(), event);
    System.out.println(result);

    // 以构造器的方式创建 format
    MessageFormat messageFormat = new MessageFormat(
        "At {1,time,short} on {1,date,full}, there was {2} on planet {0,number,integer}..",
        Locale.ENGLISH);

    result = messageFormat.format(new Object[]{planet, new Date(), event});
    System.out.println(result);

    // 重用 MessageFormat 实例,  使用新的消息模式
    messageFormat.applyPattern("You are a {0} Engineer");
    result = messageFormat.format(new Object[]{"Java"});
    System.out.println(result);

    // 使用定制的MessageFormat 格式化指定参数
    messageFormat.applyPattern("Christmas on {0,date,full} every year");
    messageFormat.setFormat(0, new SimpleDateFormat("MM/dd/yyyy"));
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.MONTH, 11);
    calendar.set(Calendar.DAY_OF_MONTH, 25);
    result = messageFormat.format(new Object[]{calendar.getTime()});
    System.out.println(result);

    System.out.println("==============================================");

    // Locale is English
    messageFormat.applyPattern("Today is {0,date,full}.");
    result = messageFormat.format(new Object[]{new Date()});
    System.out.println(result);

    messageFormat.setLocale(Locale.CHINESE);
    messageFormat.applyPattern("Today is {0,date,full}.");
    result = messageFormat.format(new Object[]{new Date()});
    System.out.println(result);

    messageFormat.applyPattern("Today is {0,date,full}.");
    messageFormat.setLocale(Locale.ENGLISH);
    result = messageFormat.format(new Object[]{new Date()});
    // locale is chinese
    System.out.println(result);

    messageFormat.applyPattern("Today is {0,date,full}.");
    messageFormat.setLocale(Locale.CHINESE);
    DateFormat instance = DateFormat
        .getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, messageFormat.getLocale());
    messageFormat.setFormat(0, instance);
    result = messageFormat.format(new Object[]{new Date()});
    // locale is chinese
    System.out.println(result);

    messageFormat.applyPattern("Today is {0,date,full}.");
    result = messageFormat.format(new Object[]{new Date()});
    // locale is English
    System.out.println(result);


  }

}
