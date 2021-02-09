package com.udemy.microservices.service;

import com.udemy.microservices.model.User;
import com.udemy.microservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User addUser(User user){
        return userRepository.save(user);

    }

    public Optional<User> getUser(Long userID){
        return userRepository.findById(userID);
    }


    public void deleteById(Long userID) {
        userRepository.deleteById(userID);
    }
}
