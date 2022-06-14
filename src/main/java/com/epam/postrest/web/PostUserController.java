package com.epam.postrest.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.postrest.domain.User;
import com.epam.postrest.service.UserService;

@RestController
public class PostUserController {

    private final UserService userService;


    @Autowired
    public PostUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/postUser")
    public User postUser(@RequestBody User user) {
        return userService.postUserRequest(user);
    }


}
