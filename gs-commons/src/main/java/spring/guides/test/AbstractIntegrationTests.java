package spring.guides.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Spring Boot应用集成测试抽象基类。
 *
 * @author dannong
 * @since 2017年02月25日 09:04
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=9080"})
public abstract class AbstractIntegrationTests {

    private static final String URL_LOCALHOST = "http://localhost";

    @Value("${local.management.port}")
//    @LocalManagementPort
    private int managementPort;

    @Autowired
    protected TestRestTemplate testRestTemplate;


    protected String toEndpointUrl(String path) {
        return toUrl(this.managementPort, path);
    }

    private static String toUrl(int servicePort, String path) {
        return URL_LOCALHOST + ':' + servicePort + path;
    }

}
