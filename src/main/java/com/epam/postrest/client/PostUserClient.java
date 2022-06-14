package com.epam.postrest.client;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.epam.postrest.domain.User;

import reactor.core.publisher.Mono;

@Component
public class PostUserClient implements PostUser {

    @Value("${baseUrl}")
    private String baseUrl;

    public User postUser(User user) {

        WebClient client = WebClient.create();
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.post();
        WebClient.RequestBodySpec bodySpec = uriSpec.uri(baseUrl + "/public/v2/users");

        LinkedMultiValueMap map = new LinkedMultiValueMap();
        map.add("name", user.getName());
        map.add("email", user.getEmail());
        map.add("gender", user.getGender());
        map.add("status", user.getStatus());
        WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.body(
            BodyInserters.fromMultipartData(map));

        WebClient.ResponseSpec responseSpec = headersSpec.header(
                HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .headers(h -> h.setBearerAuth("5471f977409ffe3c24e156046c06e370be3e8e76ba99ab2d6f83dad6bd66a522"))
            .acceptCharset(StandardCharsets.UTF_8)
            .retrieve();

        Mono<User> response = headersSpec.exchangeToMono(response1 -> {
            if (response1.statusCode().is2xxSuccessful()) {
                return response1.bodyToMono(User.class);
            } else if (response1.statusCode().is4xxClientError()) {
                return response1.bodyToMono(User.class);
            } else {
                return response1.createException()
                    .flatMap(Mono::error);
            }
        });

        User userResponse = response.block();
        return userResponse;
    }

}
