package spring.guides.dubbo.service;

/**
 * Dubbo XML 服务。
 *
 * @author dannong
 * @since 2017年04月05日 22:33
 */
public interface DubboXmlService {

  /**
   * 与人打招呼。
   *
   * @param name 用户名
   * @return 友好地示意
   */
  String sayHello(String name);

}
