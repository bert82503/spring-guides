package spring.guides.hello;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 问候控制器。
 *
 * <p>2. Create a simple web application (Web应用程序).
 *
 * @author dannong
 * @since 2017年03月05日 18:57
 */
@RestController("helloController") // = @Controller + @ResponseBody
@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class HelloController {

  @RequestMapping(path = "/")
  public String index() {
    return "Greetings from Spring Boot!";
  }

}
