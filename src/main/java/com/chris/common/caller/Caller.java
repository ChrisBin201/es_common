package com.chris.common.caller;

import io.vertx.core.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface Caller {
    <V> Mono<V> getMono(String uri, Class<V> responseType);
    <V> Mono<V> getMono(String uri, Class<V> responseType, String... pathParams);

    <V> Mono<V> getMono(String uri, Class<V> responseType, HttpHeaders headers, String... pathParams);

    <V> Mono<V> getMono(String uri, Class<V> responseType, Map<String, String> headers, String... pathParams);
    <V> Mono<V> getMono(String uri, Class<V> responseType, HttpHeaders headers);

    <V> Mono<V> getMono(String uri, Class<V> responseType, Map<String, String> queryParams);
    <V> Mono<V> getMono(String uri, Class<V> responseType, HttpHeaders headers, Map<String, String> queryParams);
    <V> Mono<V> getMono(String uri, Class<V> responseType, Map<String, String> headers, Map<String, String> queryParams);

    <V> Flux<V> getFlux(HttpMethod method, String uri, Class<V> responseType);

    <V> Flux<V> getFlux(HttpMethod method, String uri, Class<V> responseType, String... pathParams);

    <V> Flux<V> getFlux(HttpMethod method, String uri, Class<V> responseType, Map<String, String> headers, String... pathParams);
    <T, V> Mono<V> requestToMono(HttpMethod method, MultiValueMap<String, String> headers, String uri, T requestBody, Class<T> requestType, Class<V> responseType, String... pathParams);

    <T, V> Mono<V> requestToMono(HttpMethod method, String uri, T requestBody, Class<T> requestType, Class<V> responseType);
    <T, V> Mono<V> requestToMono(HttpMethod method, String uri, T requestBody, Class<T> requestType, Class<V> responseType, Map<String, String> queryParams);
    <T, V> Mono<V> requestToMono(HttpMethod method, String uri, List<T> requestBody, Class<T> requestType, Class<V> responseType, Map<String, String> queryParams);

    <T, V> Mono<V> requestToMono(HttpMethod method, String uri, T requestBody, Class<T> requestType, Class<V> responseType, String... pathParams);

    <T, V> Mono<V> requestToMono(HttpMethod method, String uri, String bearerToken, T requestBody, Class<T> requestType, Class<V> responseType);

    <T, V> Mono<V> requestToMono(HttpMethod method, HttpHeaders headers, String uri, T requestBody, Class<T> requestType, Class<V> responseType, String... pathParams);

    <V> Mono<V> requestToMono(HttpMethod method, String uri, String bearerToken, Class<V> responseType, String... pathParams);

    <T, V> Mono<V> requestToMono(HttpMethod method, String uri, String bearerToken, T requestBody, Class<T> requestType, Class<V> responseType, String... pathParams);

    <V> Flux<V> requestToFlux(HttpMethod method, String uri, String bearerToken, Class<V> responseType, String... pathParams);

    <T, V> Flux<V> requestToFlux(HttpMethod method, String uri, String bearerToken, List<T> requestBody, Class<V> responseType);
}
