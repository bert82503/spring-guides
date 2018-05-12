package spring.guides.util;

/**
 * 应用工具辅助方法集。
 *
 * @author dannong
 * @since 2017年08月30日
 */
public final class ApplicationUtils {

  private ApplicationUtils() {
    super();
  }

  public static String getVersion() {
    Package pkg = ApplicationUtils.class.getPackage();
    return pkg != null ? pkg.getImplementationVersion() : null;
  }

}
