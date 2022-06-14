package com.epam.postrest.service;


import org.junit.jupiter.api.Assertions;
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


@SpringBootTest(classes = PostRestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
public class ManualMockingIntegrationTest {


    @Autowired
    private FileReader fileReader;
    @Value("classpath:/response/goRest_dummy_user.json")
    private Resource mockJsonFile;
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testPostUserShouldCreateUserObjectAndReturnIt() throws JsonProcessingException {
        // Given
        User expected = fileReader.read(mockJsonFile, User.class);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> request = new HttpEntity<>(expected, headers);

        User actual = this.testRestTemplate.postForObject("http://localhost:" + port + "/postUser", request, User.class);

        Assertions.assertEquals(expected, actual);
        // When

        // Then

    }
}
