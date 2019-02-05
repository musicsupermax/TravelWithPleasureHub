package com.kh021j.travelwithpleasurehub.repository;

import com.kh021j.travelwithpleasurehub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findUsersByUsername(String username);
    User findByEmail(String email);

}
