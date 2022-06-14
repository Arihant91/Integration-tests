package com.epam.postrest.service;


import org.springframework.stereotype.Service;

import com.epam.postrest.client.PostUser;
import com.epam.postrest.domain.User;


@Service
public class UserService {

    private final PostUser postUser;

    public UserService(PostUser postUser) {
        this.postUser = postUser;

    }

    public User postUserRequest(User user) {
        return postUser.postUser(user);
    }


}
