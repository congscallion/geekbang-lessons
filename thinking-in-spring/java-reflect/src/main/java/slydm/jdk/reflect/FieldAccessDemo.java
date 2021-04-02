package slydm.jdk.reflect;

import java.lang.reflect.Field;
import slydm.jdk.reflect.dto.Read;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/2 14:41
 */
public class FieldAccessDemo {

  public static void main(String[] args) throws Exception {

    Class<Read> readClass = Read.class;

    Field nameField = readClass.getDeclaredField("name");
    nameField.setAccessible(true);

    Read read = readClass.newInstance();

    nameField.set(read, "r-name");

    System.out.println(read.getName());

  }

}
