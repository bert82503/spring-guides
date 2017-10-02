package spring.guides.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 示例控制器及应用入口点。
 *
 * @author dannong
 * @since 2017年02月07日 08:38
 */
@RestController("sampleController") // = @Controller + @ResponseBody
@SpringBootApplication // = @SpringBootConfiguration/@Configuration + @EnableAutoConfiguration + @ComponentScan
@SuppressWarnings("enter-point")
public class SampleController {

  @RequestMapping(path = "/")
  public String home() {
    return "Hello World!";
  }

  public static void main(String[] args) {
    SpringApplication.run(SampleController.class, args);
  }

}
