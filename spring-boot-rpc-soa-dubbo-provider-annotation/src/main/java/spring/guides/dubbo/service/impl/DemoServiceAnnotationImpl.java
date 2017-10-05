package spring.guides.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import spring.guides.dubbo.service.DemoService;

/**
 * 演示服务实现。
 *
 * @author dannong
 * @since 2017年10月04日
 */
@Service( // 发布服务
    interfaceClass = DemoService.class,
    timeout = 1000, retries = 1
)
@SuppressWarnings("service")
public class DemoServiceAnnotationImpl implements DemoService {

  private static final String TEMPLATE = "Hello, %s!";

  @Override
  public String sayHello(String name) {
    return String.format(TEMPLATE, name);
  }

}
