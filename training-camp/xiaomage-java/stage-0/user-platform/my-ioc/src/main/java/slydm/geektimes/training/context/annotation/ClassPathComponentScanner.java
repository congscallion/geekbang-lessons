package slydm.geektimes.training.context.annotation;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import slydm.geektimes.training.util.AntPathMatcher;
import slydm.geektimes.training.core.BeanDefinition;
import slydm.geektimes.training.core.PathMatcher;
import slydm.geektimes.training.util.ClassUtils;
import slydm.geektimes.training.util.StringUtils;

/**
 * 扫描类路径下的
 *
 * @author 72089101@vivo.com(wangcong) 2021/3/19 17:06
 */
public class ClassPathComponentScanner {

  private static final Log logger = LogFactory.getLog(ClassPathComponentScanner.class);

  static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

  private PathMatcher pathMatcher = new AntPathMatcher();

  /**
   * 描述路径
   */
  private String basePackage;

  /**
   * 不需要扫描的路径
   */
  private String[] excludePackages;

  public ClassPathComponentScanner(String basePackage, String[] excludePackages) {
    this.basePackage = basePackage;
    this.excludePackages = excludePackages;
  }


  public Set<Class> scan() throws IOException {

    Set<BeanDefinition> candidates = scanCandidateComponents(basePackage);
    System.out.println(candidates);
    return null;
  }

  private Set<BeanDefinition> scanCandidateComponents(String basePackage) throws IOException {
    Set<BeanDefinition> candidates = new LinkedHashSet<>();

    String packageSearchPath = resolveBasePackage(basePackage);

    URL resource = ClassLoader.getSystemClassLoader().getResource(packageSearchPath);

    File rootFile = new File(resource.getFile());

    Set<File> files = retrieveMatchingFiles(rootFile, DEFAULT_RESOURCE_PATTERN);

    files.stream().forEach(System.out::println);

    return candidates;
  }


  protected String resolveBasePackage(String basePackage) {
    return ClassUtils.convertClassNameToResourcePath(basePackage);
  }

  protected Set<File> retrieveMatchingFiles(File rootDir, String pattern) throws IOException {
    if (!rootDir.exists()) {
      // Silently skip non-existing directories.
      if (logger.isDebugEnabled()) {
        logger.debug("Skipping [" + rootDir.getAbsolutePath() + "] because it does not exist");
      }
      return Collections.emptySet();
    }
    if (!rootDir.isDirectory()) {
      // Complain louder if it exists but is no directory.
      if (logger.isInfoEnabled()) {
        logger.info("Skipping [" + rootDir.getAbsolutePath() + "] because it does not denote a directory");
      }
      return Collections.emptySet();
    }
    if (!rootDir.canRead()) {
      if (logger.isInfoEnabled()) {
        logger.info("Skipping search for matching files underneath directory [" + rootDir.getAbsolutePath() +
            "] because the application is not allowed to read the directory");
      }
      return Collections.emptySet();
    }
    String fullPattern = StringUtils.replace(rootDir.getAbsolutePath(), File.separator, "/");
    if (!pattern.startsWith("/")) {
      fullPattern += "/";
    }
    fullPattern = fullPattern + StringUtils.replace(pattern, File.separator, "/");
    Set<File> result = new LinkedHashSet<>(8);
    doRetrieveMatchingFiles(fullPattern, rootDir, result);
    return result;
  }

  protected void doRetrieveMatchingFiles(String fullPattern, File dir, Set<File> result) throws IOException {
    if (logger.isTraceEnabled()) {
      logger.trace("Searching directory [" + dir.getAbsolutePath() +
          "] for files matching pattern [" + fullPattern + "]");
    }
    for (File content : listDirectory(dir)) {
      String currPath = StringUtils.replace(content.getAbsolutePath(), File.separator, "/");
      if (content.isDirectory() && getPathMatcher().matchStart(fullPattern, currPath + "/")) {
        if (!content.canRead()) {
          if (logger.isDebugEnabled()) {
            logger.debug("Skipping subdirectory [" + dir.getAbsolutePath() +
                "] because the application is not allowed to read the directory");
          }
        } else {
          doRetrieveMatchingFiles(fullPattern, content, result);
        }
      }
      if (getPathMatcher().match(fullPattern, currPath)) {
        result.add(content);
      }
    }
  }

  /**
   * Return the PathMatcher that this resource pattern resolver uses.
   */
  public PathMatcher getPathMatcher() {
    return this.pathMatcher;
  }


  protected File[] listDirectory(File dir) {
    File[] files = dir.listFiles();
    if (files == null) {
      if (logger.isInfoEnabled()) {
        logger.info("Could not retrieve contents of directory [" + dir.getAbsolutePath() + "]");
      }
      return new File[0];
    }
    Arrays.sort(files, Comparator.comparing(File::getName));
    return files;
  }


  public static void main(String[] args) throws IOException {

    String pageName = "slydm.geektimes.training";

    ClassPathComponentScanner scanner = new ClassPathComponentScanner(pageName, new String[]{});

    Set<Class> scan = scanner.scan();

    System.out.println(scan);
  }


}
