package com.epam.postrest.integration;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.epam.postrest.client.PostUser;
import com.epam.postrest.domain.User;

@Component
@Primary
@Profile("integration")
public class PostUserClientMock implements PostUser {

    public User postUser(User user) {
        return user;
    }

}
