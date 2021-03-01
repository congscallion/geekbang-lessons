package com.slydm.thinking.in.spring.data.binding;

import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/1/8 14:16
 */
public class BeanInfoDemo {

  public static void main(String[] args) throws IntrospectionException {

    BeanInfo beanInfo = Introspector.getBeanInfo(User.class);

    PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

    for (PropertyDescriptor pd : propertyDescriptors) {

      System.out.printf("%s,%s,%s,%s,%s%n", pd.getName(),pd.getDisplayName(),pd.getPropertyType(),pd.getReadMethod(),pd.getWriteMethod());
    }

    MethodDescriptor[] methodDescriptors = beanInfo.getMethodDescriptors();
    for (MethodDescriptor md : methodDescriptors) {

      System.out.printf("%s,%s,%s%n", md.getName(),md.getDisplayName(),md.getParameterDescriptors());
    }


  }


}
