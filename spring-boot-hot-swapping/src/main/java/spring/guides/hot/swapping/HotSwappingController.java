package spring.guides.hot.swapping;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 热插拔技术控制器。
 *
 * @author dannong
 * @since 2017年03月09日 17:30
 */
@RestController("hotSwappingController")
@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class HotSwappingController {

    private static final String TEMPLATE = "changes in the method signatures: %s, age is %d!";

    @RequestMapping("/")
    public String index() {
        return "Welcome to 'Hot swapping'!";
    }

    @RequestMapping("/hotSwap")
    public String changeMethodSignature(
            @RequestParam(value = "name", defaultValue = "changeMethodSignature") String name,
            @RequestParam(value = "age", defaultValue = "-1") int age) {
        return String.format(TEMPLATE, name, age);
//        return "Welcome to 'Hot swapping'!";
    }

}
