package spring.guides.hello.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 问候控制器。
 *
 * <p>2. Create a simple web application (Web应用程序).
 *
 * @since 2017年03月05日
 */
@RestController("homeController")
@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class HomeController {

  private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

  public HomeController() {
    logger.info("create {}", this);
  }

  @RequestMapping(path = "/")
  public String index() {
    return "Greetings from Spring Boot!";
  }

}
