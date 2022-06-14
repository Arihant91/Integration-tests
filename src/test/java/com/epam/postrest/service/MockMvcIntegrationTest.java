package com.epam.postrest.service;


import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.epam.postrest.PostRestApplication;
import com.epam.postrest.converter.FileReader;
import com.epam.postrest.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

@AutoConfigureMockMvc
@SpringBootTest(classes = PostRestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("wiremock")
public class MockMvcIntegrationTest {

    public static final String NAME_NEV_EMAIL_EMAIL_2_M_COM_GENDER_FEMALE_STATUS_ACTIVE =
        "{\"name\":\"nev\",\"email\":\"email@2m.com\",\"gender\":\"female\",\"status\":\"active\"}";
    private static WireMockServer wireMockServer;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FileReader fileReader;
    @Value("classpath:/response/goRest_dummy_user.json")
    private Resource mockJsonFile;
    @LocalServerPort
    private int port;

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
    public void testPostUserShouldCreateUserObjectAndReturnIt() throws Exception {
        // Given
        User expected = fileReader.read(mockJsonFile, User.class);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> request = new HttpEntity<>(expected, headers);
        stubFor(WireMock.post(urlPathEqualTo("/public/v2/users"))
            .willReturn(okJson(NAME_NEV_EMAIL_EMAIL_2_M_COM_GENDER_FEMALE_STATUS_ACTIVE)));
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/postUser")
                .content(new ObjectMapper().writeValueAsString(expected))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(NAME_NEV_EMAIL_EMAIL_2_M_COM_GENDER_FEMALE_STATUS_ACTIVE));


        // Then

    }


}
