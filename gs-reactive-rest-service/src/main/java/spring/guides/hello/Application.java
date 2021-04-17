package spring.guides.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Although you can to package this service as a traditional WAR file for deployment to an external application server,
 * the simpler approach demonstrated below creates a standalone application.
 * You package everything in a single, executable JAR file, driven by a good old Java main() method.
 * Along the way, you use Reactive Spring’s support for embedding the Netty server as the HTTP runtime,
 * instead of deploying to an external instance.
 *
 * @author guangyi
 * @since 2021-04-18
 */
@SpringBootApplication
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        GreetingWebClient webClient = new GreetingWebClient();
        logger.info(webClient.getResult());
    }
}
