package spring.guides.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import spring.guides.service.UserService;

/**
 * 演示控制器。
 *
 * @author dannong
 * @since 2017年04月05日 22:43
 */
@RestController("demoController")
@RequestMapping(path = "/demo",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DemoController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/say")
    public String say(String name) {
        return userService.sayHello(name);
    }

}
