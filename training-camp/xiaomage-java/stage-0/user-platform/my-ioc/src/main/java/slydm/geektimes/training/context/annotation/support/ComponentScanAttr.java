package slydm.geektimes.training.context.annotation.support;

import slydm.geektimes.training.context.annotation.ComponentScan;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/24 16:35
 * @see ComponentScan
 */
public class ComponentScanAttr {

  /**
   * 扫描基础包路径
   */
  private String basePackage;

  /**
   * 排除不需要扫描的路径
   */
  private String[] excludePackages;


  public ComponentScanAttr(String basePackage, String[] excludePackages) {
    this.basePackage = basePackage;
    this.excludePackages = excludePackages;
  }

  public String getBasePackage() {
    return basePackage;
  }

  public void setBasePackage(String basePackage) {
    this.basePackage = basePackage;
  }

  public String[] getExcludePackages() {
    return excludePackages;
  }

  public void setExcludePackages(String[] excludePackages) {
    this.excludePackages = excludePackages;
  }
}
