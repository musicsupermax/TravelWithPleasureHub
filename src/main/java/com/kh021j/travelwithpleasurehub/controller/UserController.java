package com.kh021j.travelwithpleasurehub.controller;

import com.kh021j.travelwithpleasurehub.model.User;
import com.kh021j.travelwithpleasurehub.service.UserService;
import com.kh021j.travelwithpleasurehub.service.dto.UserDTO;
import com.kh021j.travelwithpleasurehub.utils.ConverterUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping(path = "")
    public List<User> getAll() {
        return userService.findAll();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Integer id) {
        Optional<User> user = userService.getById(id);
        return user.map(body -> ResponseEntity.ok(ConverterUserDTO.toUserDTO(body))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/username/{username}")
    @ResponseBody
    public List<UserDTO> getUserByName(@PathVariable String username) {
        List<User> users = userService.findUserByName(username);
        return users.stream()
                .map(ConverterUserDTO::toUserDTO)
                .collect(Collectors.toList());
    }

    @PostMapping(path = "")
    @ResponseBody
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        LOGGER.debug("REST request to save User : {}", userDTO);
        return ConverterUserDTO.toUserDTO(userService.create(ConverterUserDTO.fromUserDTO(userDTO)));
    }

}
