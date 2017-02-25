package spring.guides.hello;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test of {@link HelloWorldController}.
 *
 * @author dannong
 * @since 2017年02月25日 08:25
 */
public class HelloWorldControllerTests extends AbstractIntegrationTests {

    @Test
    public void noParam() {
        String url = toRequestUrl("/hello-world");
        ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
                url, Map.class); // LinkedHashMap

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<?, ?> response = entity.getBody();
        assertThat(response.get("content")).isEqualTo("Hello, Stranger!");
    }

    /**
     * @see sample.test.SampleTestApplicationWebIntegrationTests
     */
    @Test
    public void param() {
        String url = toRequestUrl("/hello-world?name={name}"); // 参数占位符
        ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
                url, Map.class, "Edward"); // LinkedHashMap

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<?, ?> response = entity.getBody();
        assertThat(response.get("content")).isEqualTo("Hello, Edward!");
    }

}
