package spring.guides.dubbo.service;

/**
 * 演示服务。
 *
 * @author dannong
 * @since 2017年04月05日 11:43
 */
public interface DemoService {

  /**
   * 与人打招呼。
   *
   * @param name 用户名
   * @return 友好地示意
   */
  String sayHello(String name);

}
