package spring.guides.dubbo.controller;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import spring.guides.dubbo.service.DubboAnnotationService;

/**
 * 演示控制器。
 *
 * @author dannong
 * @since 2017年10月04日
 */
@RestController("annotationDemoController")
@RequestMapping(path = "/demo",
    method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
@SuppressWarnings("web-api")
public class AnnotationDemoController {

  @Resource
  private DubboAnnotationService dubboAnnotationService;

  @RequestMapping(path = "/say")
  public String say(String name) {
    return dubboAnnotationService.sayHello(name);
  }

}
