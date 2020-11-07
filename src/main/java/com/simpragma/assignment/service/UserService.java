package com.simpragma.assignment.service;

import java.util.List;

import com.simpragma.assignment.model.User;
import com.simpragma.assignment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User saveUser(final User user) {
        return userRepository.save(user);
    }

}
