package com.chris.common.caller;

import com.chris.common.handler.CommonErrorCode;
import com.chris.common.handler.CommonException;
import com.chris.common.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.timeout.TimeoutException;
import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.predicate.ErrorConverter;
import io.vertx.ext.web.client.predicate.ResponsePredicate;
import io.vertx.ext.web.codec.BodyCodec;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class VertxCaller implements Caller{

    private static final int defaultTimeout = 3;
    private static final int defaultRetryTimes = 2;
    private static final int defaultRetryDelay = 1;

    private static final String INTERNAL_AUTH = "Internal";

    private final WebClient webClient;

    private final HttpServletRequest incomingRequest;

    @Autowired
    public VertxCaller(WebClient webClient) {
        this.webClient = webClient;
        this.incomingRequest = getIncomingRequest();
    }

    private HttpServletRequest getIncomingRequest() {
        RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
        if (attribs instanceof NativeWebRequest) {
            return (HttpServletRequest) ((NativeWebRequest) attribs).getNativeRequest();
        }
        return null;
    }


    @Override
    public <V> Mono<V> getMono(String uri, Class<V> responseType) {
        return null;
    }

    @Override
    public <V> Mono<V> getMono(String uri, Class<V> responseType, String... pathParams) {
        return getMono(uri, responseType, null, null, pathParams);
    }

    @Override
    public <V> Mono<V> getMono(String uri, Class<V> responseType, HttpHeaders headers, String... pathParams) {
        return null;
    }

    @Override
    public <V> Mono<V> getMono(String uri, Class<V> responseType, Map<String, String> queryParams, String... pathParams) {
        return null;
    }

    @Override
    public <V> Mono<V> getMono(String uri, Class<V> responseType, HttpHeaders headers) {
        return null;
    }

    @Override
    public <V> Mono<V> getMono(String uri, Class<V> responseType, Map<String, String> queryParams) {
        return getMono(uri, responseType, null, queryParams, null);
    }

    @Override
    public <V> Mono<V> getMono(String uri, Class<V> responseType, HttpHeaders headers, Map<String, String> queryParams) {
        return null;
    }

    @Override
    public <V> Mono<V> getMono(String uri, Class<V> responseType, Map<String, String> headers, Map<String, String> queryParams) {
        return null;
    }

    private  <V> Mono<V> getMono(String uri, Class<V> responseType, Map<String, String> headers, Map<String, String> queryParams, String... pathParams) {
        String absUri = pathParams != null ? setPathParams(uri, pathParams).toString() : uri;

        MultiMap multiMap = MultiMap.caseInsensitiveMultiMap();
        if (headers != null)
            multiMap.addAll(headers);

        HttpRequest<V> request;
        HttpRequest<Buffer> requestBuffer = webClient.getAbs(absUri);
        if(queryParams != null){
            request = setQueryParams(requestBuffer, queryParams)
                    .putHeaders(multiMap)
                    .timeout(defaultTimeout * 1000L)
                    .expect(responsePredicate())
                    .as(bodyCodec(responseType));
        }
        else {
            request = webClient.getAbs(absUri)
                    .putHeaders(multiMap)
                    .timeout(defaultTimeout * 1000L)
                    .expect(responsePredicate())
                    .as(bodyCodec(responseType));
        }

        String token = incomingRequest != null ? incomingRequest.getHeader("Authorization") : INTERNAL_AUTH;
        log.info("token: {}", token);
        request.putHeader("Authorization", token);

        return Mono.fromFuture(request.send().flatMap(this::responseToBody).toCompletionStage().toCompletableFuture())
                .retryWhen(configRetry(HttpMethod.GET, uri, defaultRetryTimes, defaultRetryDelay))
                .doOnError(throwable -> log.error(throwable.getLocalizedMessage()));
    }


    @Override
    public <V> Flux<V> getFlux(HttpMethod method, String uri, Class<V> responseType) {
        return null;
    }

    @Override
    public <V> Flux<V> getFlux(HttpMethod method, String uri, Class<V> responseType, String... pathParams) {
        return null;
    }

    @Override
    public <V> Flux<V> getFlux(HttpMethod method, String uri, Class<V> responseType, Map<String, String> headers, String... pathParams) {
        return null;
    }

    @Override
    public <T, V> Mono<V> requestToMono(HttpMethod method, MultiValueMap<String, String> headers, String uri, T requestBody, Class<T> requestType, Class<V> responseType, String... pathParams) {
        return null;
    }

    @Override
    public <T, V> Mono<V> requestToMono(HttpMethod method, String uri, T requestBody, Class<T> requestType, Class<V> responseType) {
        return requestToMono(method, uri, null, requestBody, requestType, responseType,null,null);
    }

    @Override
    public <T, V> Mono<V> requestToMono(HttpMethod method, String uri, T requestBody, Class<T> requestType, Class<V> responseType, Map<String, String> queryParams) {
        return null;
    }

    @Override
    public <T, V> Mono<V> requestToMono(HttpMethod method, String uri, List<T> requestBody, Class<T> requestType, Class<V> responseType, Map<String, String> queryParams) {
        return null;
    }

    @Override
    public <T, V> Mono<V> requestToMono(HttpMethod method, String uri, T requestBody, Class<T> requestType, Class<V> responseType, String... pathParams) {
        return null;
    }

    @Override
    public <T, V> Mono<V> requestToMono(HttpMethod method, String uri, String bearerToken, T requestBody, Class<T> requestType, Class<V> responseType) {
        return null;
    }

    @Override
    public <T, V> Mono<V> requestToMono(HttpMethod method, HttpHeaders headers, String uri, T requestBody, Class<T> requestType, Class<V> responseType, String... pathParams) {
        return null;
    }

    @Override
    public <V> Mono<V> requestToMono(HttpMethod method, String uri, String bearerToken, Class<V> responseType, String... pathParams) {
        return null;
    }

    @Override
    public <T, V> Mono<V> requestToMono(HttpMethod method, String uri, String bearerToken, T requestBody, Class<T> requestType, Class<V> responseType, String... pathParams) {
        return null;
    }

    private <T, V> Mono<V> requestToMono(HttpMethod method, String uri, MultiValueMap<String, String> headers,  T requestBody, Class<T> requestType, Class<V> responseType, Map<String, String> queryParams, String... pathParams) {
        String absUri = pathParams != null ? setPathParams(uri, pathParams).toString() : uri;

        MultiMap multiMap = MultiMap.caseInsensitiveMultiMap();
        multiMap.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        if (headers != null){
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                if (!entry.getKey().equals(HttpHeaders.CONTENT_TYPE)) {
                    multiMap.add(entry.getKey(), entry.getValue());
                }
            }
        }


        HttpRequest<V> request;
        HttpRequest<Buffer> requestBuffer = webClient.requestAbs(method,absUri);
        if(queryParams != null){
            request = setQueryParams(requestBuffer, queryParams)
                    .putHeaders(multiMap)
                    .timeout(defaultTimeout * 1000L)
                    .expect(responsePredicate())
                    .as(bodyCodec(responseType));
        }
        else {
            request = webClient.requestAbs(method,absUri)
                    .putHeaders(multiMap)
                    .timeout(defaultTimeout * 1000L)
                    .expect(responsePredicate())
                    .as(bodyCodec(responseType));
        }

        String token = incomingRequest != null ? incomingRequest.getHeader("Authorization") : INTERNAL_AUTH;
        log.info("token: {}", token);
        request.putHeader("Authorization", token);

//        return Mono.just(request.sendBuffer(convertToBuffer(requestBody, requestType))
//                .flatMap(this::responseToBody).result())
//                .retryWhen(configRetry(method, uri, defaultRetryTimes, defaultRetryDelay))
//                .doOnError(throwable -> log.error(throwable.getLocalizedMessage()));
        return Mono.fromFuture(request.sendBuffer(convertToBuffer(requestBody, requestType))
                        .flatMap(this::responseToBody).toCompletionStage().toCompletableFuture())
                .retryWhen(configRetry(method, uri, defaultRetryTimes, defaultRetryDelay))
                .doOnError(throwable -> log.error(throwable.getLocalizedMessage()));
    }

    @Override
    public <V> Flux<V> requestToFlux(HttpMethod method, String uri, String bearerToken, Class<V> responseType, String... pathParams) {
        return null;
    }

    @Override
    public <T, V> Flux<V> requestToFlux(HttpMethod method, String uri, String bearerToken, List<T> requestBody, Class<V> responseType) {
        return null;
    }

    private static URI setPathParams(String template, String... pathParams) {
        UriBuilder builder = UriComponentsBuilder.fromUriString(template);
        URI uri = builder.build(pathParams);
        return uri;
    }

    private HttpRequest<Buffer> setQueryParams(HttpRequest<Buffer> request, Map<String, String> queryParams) {
        if (queryParams != null && queryParams.size() > 0) {
            queryParams.forEach((key, value) -> {
                if (key != null && !key.trim().equals(""))
                    if (value != null && !value.trim().equals(""))
                        request.addQueryParam(key, value);
            });
        }
        return request;
    }



    private ResponsePredicate responsePredicate() {
        ErrorConverter errorConverter = ErrorConverter.createFullBody(responsePredicateResult ->
                convertToMyException(responsePredicateResult.response()));
        return ResponsePredicate.create(ResponsePredicate.status(200, 399), errorConverter);
    }

    private <V> BodyCodec<V> bodyCodec(Class<V> responseType) {
        return responseType.equals(String.class) ? (BodyCodec<V>) BodyCodec.string() : BodyCodec.json(responseType);
    }

    private <V> Future<V> responseToBody(HttpResponse<V> response) {
        return Future.succeededFuture(response.body());
    }

//    private URI convertFrom(String template, String... params) {
//        return UriBuilder.fromUri(template).build(params);
//    }

    private <T> Buffer convertToBuffer(T requestBody, Class<T> requestType) {
        if (requestType.equals(String.class))
            return Buffer.buffer((String) requestBody);

        try {
            return Buffer.buffer(JsonUtil.convertObjectToBytes(requestBody));
        } catch (JsonProcessingException e) {
            throw new CommonException(500, CommonErrorCode.INTERNAL_SERVER_ERROR.toString(),
                    "Failed to convert requestBody to buffer with message " + e.getLocalizedMessage());
        }
    }

//    private <T> Buffer convertListToBuffer(List<T> requestBody) {
//        try {
//            return Buffer.buffer(JsonUtil.convertObjectToBytes(requestBody));
//        } catch (JsonProcessingException e) {
//            throw new CommonException(500, GeneralErrorCode.INTERNAL_SERVER_ERROR.toString(),
//                    "Failed to convert requestBody to buffer with message " + e.getLocalizedMessage());
//        }
//    }

    private <V> CommonException convertToMyException(HttpResponse<V> response) {
        V v = response.body();

        String message = String.format("Rest api calling failed with error-code %s and error body: %s", response.statusCode(), v);
        log.error(message);
        if (response.statusCode() == HttpResponseStatus.UNAUTHORIZED.code()) {
            return new CommonException(HttpResponseStatus.OK.code(), CommonErrorCode.UNAUTHORIZED.getCode(), message);
        }
        if (v != null && v.toString() != null && !v.toString().isEmpty()) {
//            if (response.getHeader("content-type") != null && response.getHeader("content-type").equals(MediaType.APPLICATION_JSON_VALUE)) {
//                ErrorDetail errorDetail;
//                try {
//                    errorDetail = response.bodyAsJson(ErrorDetail.class);
//                    if (errorDetail.getHttpStatusCode() == 0) {
//                        return new CommonException(500, CommonErrorCode.INTERNAL_SERVER_ERROR.name(), v.toString());
//                    } else {
//                        return new CommonException(errorDetail.getHttpStatusCode(), errorDetail.getErrorCode(), errorDetail.getDetail());
//                    }
//                } catch (Exception ex) {
//                    log.warn("Something went wrong when cast body to ErrorDetails with message {}", ex.getLocalizedMessage());
//                }
//            }
            CommonException commonException;
            try {
                commonException = response.bodyAsJson(CommonException.class);
                if (commonException.getHttpStatusCode() == 0) {
                    log.error("Response error {}", response.bodyAsJsonObject());
                    return new CommonException(response.statusCode(), CommonErrorCode.INTERNAL_SERVER_ERROR.getCode(), response.bodyAsString());
                }
                if (commonException.getData() == null)
                    return new CommonException(commonException.getHttpStatusCode(), commonException.getErrorCode(), commonException.getMessage());
                else
                    return new CommonException(commonException.getHttpStatusCode(), commonException.getErrorCode(), commonException.getMessage(), commonException.getData());
            } catch (Exception ex) {
                log.warn("Something went wrong when cast body to ErrorDetails with message {}", ex.getLocalizedMessage());
            }
        }
        return new CommonException(response.statusCode(), CommonErrorCode.INTERNAL_SERVER_ERROR.getCode(), response.statusMessage());
    }

    private static Retry configRetry(HttpMethod httpMethod, String uri, int numberOfRetries, int retryDelayInSecond) {
        return Retry.fixedDelay(numberOfRetries, Duration.ofSeconds(retryDelayInSecond))
                .filter(VertxCaller::needToRetry)
                .doAfterRetry(retrySignal -> log.warn("Retry {} request to {} in {}/{} because of {}", httpMethod, uri,
                        retrySignal.totalRetriesInARow() + 1, numberOfRetries, retrySignal.failure().getLocalizedMessage()))
                .onRetryExhaustedThrow(((retryBackoffSpec, retrySignal) -> retrySignal.failure()));
    }

    private static boolean needToRetry(Throwable throwable) {
        if (throwable.getCause() instanceof TimeoutException)
            return true;

        if (throwable.getCause() instanceof java.util.concurrent.TimeoutException)
            return true;

        final String errorMess = throwable.getLocalizedMessage() == null ?
                throwable.getMessage() : throwable.getLocalizedMessage();
        if (errorMess != null) {
            return errorMess.toLowerCase().contains("connection reset by peer")
                    || errorMess.toLowerCase().contains("connection refused")
                    || errorMess.toLowerCase().contains("connection timed out")
                    || errorMess.toLowerCase().contains("connection timeout")
                    || errorMess.toLowerCase().contains("not observe any item or terminal signal within");
        }
        return false;
    }
}
