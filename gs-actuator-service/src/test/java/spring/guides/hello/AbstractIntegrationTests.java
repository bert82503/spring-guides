package spring.guides.hello;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.boot.actuate.autoconfigure.LocalManagementPort;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 集成测试抽象基类。
 *
 * @author dannong
 * @since 2017年02月25日 09:04
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {HelloWorldConfiguration.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
public abstract class AbstractIntegrationTests {

    private static final String URL_LOCALHOST = "http://localhost";

//    @Value("${local.server.port}")
    @LocalServerPort
    private int port;

//    @Value("${local.management.port}")
    @LocalManagementPort
    private int managementPort;

    @Resource
    protected TestRestTemplate testRestTemplate;


    protected String toRequestUrl(String path) {
        return toUrl(this.port, path);
    }

    protected String toEndpointUrl(String path) {
        return toUrl(this.managementPort, path);
    }


    private static String toUrl(int managementPort, String path) {
        return URL_LOCALHOST + ':' + managementPort + path;
    }

}
