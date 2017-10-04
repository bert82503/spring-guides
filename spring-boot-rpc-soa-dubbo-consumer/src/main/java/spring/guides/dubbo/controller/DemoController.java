package spring.guides.dubbo.controller;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import spring.guides.dubbo.service.UserService;

/**
 * 演示控制器。
 *
 * @author dannong
 * @since 2017年04月05日 22:43
 */
@RestController("demoController")
@RequestMapping(path = "/demo",
    method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
@SuppressWarnings("web-api")
public class DemoController {

  @Resource
  private UserService userService;

  @RequestMapping(path = "/say")
  public String say(String name) {
    return userService.sayHello(name);
  }

}
