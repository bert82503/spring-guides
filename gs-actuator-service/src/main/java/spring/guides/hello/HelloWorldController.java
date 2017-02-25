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
 * @author dannong
 * @since 2017年02月24日 20:17
 */
@RestController("helloWorldController")
@RequestMapping(path = "/hello-world", method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class HelloWorldController {

    private static final String TEMPLATE = "Hello, %s!";

    private final AtomicLong counter = new AtomicLong(0L);


    @RequestMapping
    public Greeting sayHello(
            @RequestParam(value = "name", required = false, defaultValue = "Stranger") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(TEMPLATE, name));
    }

}
