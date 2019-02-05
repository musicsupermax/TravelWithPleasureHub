package com.kh021j.travelwithpleasurehub.controller;

import com.kh021j.travelwithpleasurehub.model.User;
import com.kh021j.travelwithpleasurehub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@CrossOrigin("*")
public class ClientsController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> getUsers() {
        return userService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void blockUser(@PathVariable("id") int userId) {
        userService.blockUser(userId);
    }


}
