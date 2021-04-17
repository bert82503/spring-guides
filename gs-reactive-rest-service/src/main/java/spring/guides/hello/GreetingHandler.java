package spring.guides.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * In the Spring Reactive approach, we use a handler to handle the request and create a response.
 *
 * @author guangyi
 * @since 2021-04-18
 */
@Component
public class GreetingHandler {

    private static final Logger logger = LoggerFactory.getLogger(GreetingHandler.class);

    @NonNull
    public Mono<ServerResponse> hello(ServerRequest request) {
        logger.debug("request:{}", request);

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue("Hello, Spring!"));
    }
}
