package com.kh021j.travelwithpleasurehub.service;

import com.kh021j.travelwithpleasurehub.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    List<User> findUserByName(String username);
    User create (User user);
    User update(User user);
    User getUser(int id);
    User getUser(String email);
    void blockUser(int id);
    Optional<User> getById(Integer id);
}
