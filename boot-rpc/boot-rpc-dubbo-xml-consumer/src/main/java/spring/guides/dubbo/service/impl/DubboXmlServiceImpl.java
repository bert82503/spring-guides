package spring.guides.dubbo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import spring.guides.dubbo.service.DemoService;
import spring.guides.dubbo.service.DubboXmlService;

/**
 * Dubbo XML 服务实现。
 *
 * @author dannong
 * @since 2017年04月05日 22:35
 */
@Service("dubboXmlService")
public class DubboXmlServiceImpl implements DubboXmlService {

  /**
   * obtain proxy object for remote invocation
   */
  @Resource
  private DemoService demoService;


  @Override
  public String sayHello(String name) {
    // execute remote invocation
    return demoService.sayHello(name);
  }

}
