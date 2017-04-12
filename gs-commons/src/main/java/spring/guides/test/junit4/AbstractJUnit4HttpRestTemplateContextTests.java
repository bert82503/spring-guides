package spring.guides.test.junit4;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

/**
 * 基于{@link TestRestTemplate}和HTTP方式的API测试抽象基类。
 *
 * @author dannong
 * @since 2017年02月25日 09:04
 */
public abstract class AbstractJUnit4HttpRestTemplateContextTests
        extends AbstractJUnit4IntegrationContextTests {

    /**
     * 基于HTTP请求方式
     */
    @Autowired
    private TestRestTemplate restTemplate;


    // URL
    protected String toRequestUrl(String path) {
        return path;
    }

    // HTTP Request
    protected <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType,
                                              Object... urlVariables) throws RestClientException {
        return this.restTemplate.getForEntity(url, responseType, urlVariables);
    }

    // Assert
    protected void assertResponseEntity(
            ResponseEntity<?> entity, HttpStatus statusCode) {
        assertThat(entity.getStatusCode()).isEqualTo(statusCode);
    }

}
