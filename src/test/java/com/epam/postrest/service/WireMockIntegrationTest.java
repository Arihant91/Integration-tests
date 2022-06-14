package com.epam.postrest.service;


import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import com.epam.postrest.PostRestApplication;
import com.epam.postrest.converter.FileReader;
import com.epam.postrest.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

@ActiveProfiles("wiremock")
@SpringBootTest(classes = PostRestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WireMockIntegrationTest {

    private static WireMockServer wireMockServer;
    @Autowired
    private FileReader fileReader;
    @Value("classpath:/response/goRest_dummy_user.json")
    private Resource mockJsonFile;
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeAll
    public static void init() {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig()
            .port(8089));


        WireMock.configureFor(8089);
        wireMockServer.start();
    }

    @AfterAll
    public static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void testPostUserShouldCreateUserObjectAndReturnIt() throws JsonProcessingException {
        // Given
        User expected = fileReader.read(mockJsonFile, User.class);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> request = new HttpEntity<>(expected, headers);
        stubFor(WireMock.post(urlPathEqualTo("/public/v2/users"))
            .willReturn(okJson("{\"name\":\"nev\",\"email\":\"email@2m.com\",\"gender\":\"female\",\"status\":\"active\"}")));

        // When
        User actual = this.testRestTemplate.postForObject("http://localhost:" + port + "/postUser", request, User.class);
        Assertions.assertEquals(expected, actual);
        // Then

    }
}