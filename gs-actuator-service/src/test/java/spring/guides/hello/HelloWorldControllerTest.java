package spring.guides.hello;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import spring.guides.test.junit.AbstractJUnitHttpRestTemplateTests;

/**
 * API test of {@link HelloWorldController}.
 *
 * @author dannong
 * @since 2017年02月25日 08:25
 */
public class HelloWorldControllerTest extends AbstractJUnitHttpRestTemplateTests {

    @Test
    public void noParam() {
        String url = toRequestUrl("/hello-world");
        ResponseEntity<Map> entity = getForEntity(url, Map.class); // LinkedHashMap

        assertResponseStatusCode(entity, HttpStatus.OK);
        Map<?, ?> response = entity.getBody();
        assertThat(response.get("content")).isEqualTo("Hello, Stranger!");
    }

    /**
     * @see sample.test.SampleTestApplicationWebIntegrationTests
     */
    @Test
    public void param() {
        String url = toRequestUrl("/hello-world?name={name}"); // 参数占位符
        ResponseEntity<Map> entity = getForEntity(url, Map.class, "Edward"); // LinkedHashMap

        assertResponseStatusCode(entity, HttpStatus.OK);
        Map<?, ?> response = entity.getBody();
        assertThat(response.get("content")).isEqualTo("Hello, Edward!");
    }

}
