package com.udemy.microservices.controller;

import com.udemy.microservices.exception.UserNotFoundException;
import com.udemy.microservices.model.User;
import com.udemy.microservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(name = "usermessageconverter")
public class CustomHttpMessageResolverController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "user",consumes = "text/user")
    public ResponseEntity<Void> addUser(@RequestBody User user){
        try {
            User createdUser=userService.addUser(user);
            HttpHeaders headers= new HttpHeaders();
            headers.add("id", createdUser.getUserid().toString());
            headers.add("name",createdUser.getFirstname()+" "+createdUser.getLastname());
            return new ResponseEntity<>(headers,HttpStatus.CREATED);

        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping(path="user/{id}",produces="text/user")
    public ResponseEntity<User> get(@PathVariable("id" )String id) throws UserNotFoundException {
        try {
            Optional<User> user= userService.getUser(Long.valueOf(id));
            return new ResponseEntity<>(user.get(),HttpStatus.OK);
        } catch(Exception e)
        {
            throw new UserNotFoundException("User Not Found for id :" +id);
        }
    }
}
