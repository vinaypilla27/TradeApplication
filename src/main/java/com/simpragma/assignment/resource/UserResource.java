package com.simpragma.assignment.resource;

import java.util.List;

import com.simpragma.assignment.model.User;
import com.simpragma.assignment.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;

    @Autowired
    public UserResource(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        LOGGER.info("Received a request to add a new user");
        User savedUser = userService.addUser(user);
        LOGGER.info("Successfully added a new user with id : {}", savedUser.getId());
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> users() {
        LOGGER.info("Received a request to fetch all the users");
        return ResponseEntity.ok(userService.getUsers());
    }

}
