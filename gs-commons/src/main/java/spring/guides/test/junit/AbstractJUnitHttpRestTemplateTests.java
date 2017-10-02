package spring.guides.test.junit;

import javax.annotation.Resource;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 基于 {@link TestRestTemplate} 和 HTTP 方式的 API 集成测试抽象基类。
 *
 * @author dannong
 * @since 2017年02月25日 09:04
 */
public abstract class AbstractJUnitHttpRestTemplateTests
    extends AbstractJUnitIntegrationTests {

  /**
   * 基于 HTTP 请求方式
   */
  @Resource
  private TestRestTemplate restTemplate;


  // URL
  protected String toRequestUrl(String path) {
    return path;
  }

  // HTTP Request
  protected <T> ResponseEntity<T> getForEntity(
      String url, Class<T> responseType, Object... urlVariables) throws RestClientException {
    return this.restTemplate.getForEntity(url, responseType, urlVariables);
  }


  // Assert
  public static void assertResponseStatusCode(ResponseEntity<?> entity, HttpStatus statusCode) {
    assertThat(entity.getStatusCode()).isEqualTo(statusCode);
  }

  public static <T> void assertResponseBody(ResponseEntity<T> entity, final T body) {
    assertThat(entity.getBody()).isEqualTo(body);
  }

}
