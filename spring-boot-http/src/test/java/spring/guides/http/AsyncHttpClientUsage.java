package spring.guides.http;

import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Response;
import org.asynchttpclient.util.HttpConstants.ResponseStatusCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.asynchttpclient.Dsl.asyncHttpClient;

/**
 * <a href="https://github.com/AsyncHttpClient/async-http-client">
 *     Usage of Async Http Client (@AsyncHttpClient on twitter)</a>
 *
 * @author dannong
 * @since 2017年06月23日 22:29
 */
public class AsyncHttpClientUsage {

    private static final Logger logger = LoggerFactory.getLogger(AsyncHttpClientUsage.class);


    // asynchronous (non-blocking) operation/异步非阻塞操作
    /**
     * Note that in this case all the content must be read fully in memory.
     */
    @Test
    public void asyncCompletionHandlerBase()
            throws InterruptedException, ExecutionException, TimeoutException {
        AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();
        ListenableFuture<Response> future = asyncHttpClient
                .prepareGet("http://www.baidu.com")
                .execute();
        Response response = future.get(1L, TimeUnit.SECONDS);

        String body = response.getResponseBody();
        assertThat(body.contains("百度一下，你就知道")).isTrue();
    }

    /**
     * receive and process the response in your handler
     */
    @Test
    public void asyncCompletionHandler() {
        AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();
        asyncHttpClient
                .prepareGet("http://www.baidu.com")
                .execute(new AsyncCompletionHandler<Response>() {
                    // this will also fully read Response in memory before calling onCompleted
                    @Override
                    public Response onCompleted(Response response) throws Exception {
                        // Do something with the Response
                        // ...
                        int statusCode = response.getStatusCode();
                        if (statusCode >= ResponseStatusCodes.MOVED_PERMANENTLY_301) {
                            logger.error("response is fail, response:{}", response);
                        } else if (statusCode >= ResponseStatusCodes.OK_200) {
                            String body = response.getResponseBody();
                            assertThat(body.contains("百度一下，你就知道")).isTrue();
                        }
                        return response;
                    }

                    @Override
                    public void onThrowable(Throwable t) {
                        super.onThrowable(t);
                        // Something wrong happened
                        logger.error("request is error", t);
                    }
                });
    }

    // asynchronous (non-blocking) solution (through Java 8 class CompletableFuture<T>)

    @Test
    public void completableFuture() {
        AsyncHttpClient asyncHttpClient = asyncHttpClient();
        CompletableFuture<Response> promise = asyncHttpClient.prepareGet("http://www.baidu.com")
                .execute()
                .toCompletableFuture()
                .exceptionally(t -> {
                    // Something wrong happened...
                    logger.error("request is error", t);
                    return null;
                })
                .thenApply(response -> {
                    // Do something with the Response
                    int statusCode = response.getStatusCode();
                    if (statusCode >= ResponseStatusCodes.MOVED_PERMANENTLY_301) {
                        logger.error("response is fail, response:{}", response);
                    } else if (statusCode >= ResponseStatusCodes.OK_200) {
                        String body = response.getResponseBody();
                        assertThat(body.contains("百度一下，你就知道")).isTrue();
                    }
                    return response;
                });
        promise.join(); // wait for completion
    }
}
