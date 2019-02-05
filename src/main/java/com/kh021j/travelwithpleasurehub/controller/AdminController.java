package com.kh021j.travelwithpleasurehub.controller;

import com.kh021j.travelwithpleasurehub.model.User;
import com.kh021j.travelwithpleasurehub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ${JDEEK} on ${11.11.2018}.
 */
@RestController
@RequestMapping("/api/admin/clients")
@CrossOrigin("*")
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/clients",method = RequestMethod.GET)
    public List<User> getUsers(){return userService.findAll();}

    @RequestMapping(value = "/admin/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void blockUser(@PathVariable("id") int userID){
        userService.blockUser(userID);
    }
}
