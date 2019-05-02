package spring.guides.dubbo.service;

/**
 * Dubbo 注解服务。
 *
 * @author dannong
 * @since 2017年10月04日
 */
public interface DubboAnnotationService {

  /**
   * 与人打招呼。
   *
   * @param name 用户名
   * @return 友好地示意
   */
  String sayHello(String name);

}
