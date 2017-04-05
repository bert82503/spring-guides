package spring.guides.hello;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 问候控制器。
 *
 * <p>Create a resource controller (资源控制器).
 *
 * @author dannong
 * @since 2017年01月30日 09:20
 */
@RestController("greetingController") // = @Controller + @ResponseBody
@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GreetingController {

    private static final String TEMPLATE = "Hello, %s!";

    private final AtomicLong counter = new AtomicLong(0L);


    @RequestMapping(path = "/greeting")
    public Greeting greeting(
            @RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(TEMPLATE, name));
    }

}
