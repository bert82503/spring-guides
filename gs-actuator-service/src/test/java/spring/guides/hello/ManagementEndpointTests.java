package spring.guides.hello;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import spring.guides.test.AbstractIntegrationTests;

/**
 * Test of {@link org.springframework.boot.actuate.endpoint.Endpoint}s.
 *
 * @author dannong
 * @since 2017年02月25日 09:09
 */
public class ManagementEndpointTests extends AbstractIntegrationTests {

    /**
     * {"status":"UP","diskSpace":{"status":"UP","total":120108089344,"free":53955444736,"threshold":10485760}}
     */
    @Test
    public void health() {
        String url = toEndpointUrl("/health");
        ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
                url, Map.class); // LinkedHashMap

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<?, ?> response = entity.getBody();
        assertThat(response.get("status")).isEqualTo("UP");
        assertThat(response.containsKey("diskSpace")).isTrue();
    }

    /**
     * {}
     */
    @Test
    public void info() {
        String url = toEndpointUrl("/info");
        ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
                url, Map.class); // LinkedHashMap

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<?, ?> response = entity.getBody();
        assertThat(response).isNotNull();
        assertThat(response).isEmpty();
    }

}
