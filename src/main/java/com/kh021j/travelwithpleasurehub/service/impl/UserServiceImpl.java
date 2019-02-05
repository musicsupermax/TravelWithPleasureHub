package com.kh021j.travelwithpleasurehub.service.impl;

import com.kh021j.travelwithpleasurehub.model.User;
import com.kh021j.travelwithpleasurehub.repository.UserRepository;
import com.kh021j.travelwithpleasurehub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public List<User> findUserByName(String name) {
        return userRepository.findUsersByUsername(name);
    }

    @Transactional
    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        User newUser = getUser(user.getId());
        if (user.getPassword().equals(newUser.getPassword())){
            return userRepository.saveAndFlush(user);
        }
        else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.saveAndFlush(user);
        }
    }

    @Override
    public User getUser(int id) {
        return userRepository.getOne(id);
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void blockUser(int id) {
        User user = this.getUser(id);
        user.setStatus(!user.isStatus());
        this.update(user);
    }

    @Transactional
    @Override
    public Optional<User> getById(Integer id) {
        if (userRepository.existsById(id)){
            return Optional.of(userRepository.getOne(id));
        }
        return Optional.empty();
    }

}
