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
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path= {"greetings","hello"},method= {RequestMethod.GET})
    public String greetings() {
        return "Hello World";
    }


    @RequestMapping(path="user",method=RequestMethod.POST)
    public ResponseEntity<Void> addUser(@RequestBody User user) {
        try {
            User createdUser=userService.addUser(user);
            HttpHeaders headers= new HttpHeaders();
            headers.add("id", createdUser.getUserid().toString());
            return new ResponseEntity<>(headers,HttpStatus.CREATED);

        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }

    }

    @RequestMapping(path="user/{id}",method=RequestMethod.GET)
    public ResponseEntity<User> get(@PathVariable("id" )String id) throws UserNotFoundException{
        try {
            Optional<User> user= userService.getUser(Long.valueOf(id));
            return new ResponseEntity<User>(user.get(),HttpStatus.OK);
        } catch(Exception e)
        {
            throw new UserNotFoundException("User Not Found for id :" +id);
        }
    }


    @RequestMapping(path="user/{id}",method=RequestMethod.PUT)
    public ResponseEntity<User>update(@RequestBody User updatedUser,@PathVariable("id")String id){
        try {
            Optional<User> user = userService.getUser(Long.valueOf(id));
            if(user.isPresent()){
                User olduser = user.get();
                olduser.setEmail(updatedUser.getEmail());
                olduser.setFirstname(updatedUser.getFirstname());
                olduser.setLastname(updatedUser.getLastname());
                User updatedNewUser=userService.addUser(olduser);

                return new ResponseEntity<>(updatedNewUser,HttpStatus.OK);
            }else{
                throw new UserNotFoundException("User Not Found for id :" +id);
            }
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(path="user/{id}",method=RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") String id){
        try {
            userService.deleteById(Long.valueOf(id));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
