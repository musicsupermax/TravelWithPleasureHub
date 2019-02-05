package com.kh021j.travelwithpleasurehub.propertyrent.service;


import com.kh021j.travelwithpleasurehub.controller.enumeration.SortType;
import com.kh021j.travelwithpleasurehub.propertyrent.model.UserReview;
import com.kh021j.travelwithpleasurehub.propertyrent.repository.UserReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
public class UserReviewService {

    @Autowired
    private UserReviewRepository userReviewRepository;


    public Iterable<UserReview> findAll(){
        return userReviewRepository.findAll();
    }

    public UserReview findById(Integer id) {
        return userReviewRepository.findById(id).orElse(null);
    }

    public Iterable<UserReview> findByUserId(Integer userId) {
        return userReviewRepository.findByUserId(userId).orElse(null);
    }

    public Iterable<UserReview> findAllByOrderByDateRated(String sortByDateRated) {
        switch (SortType.valueOf(sortByDateRated.toUpperCase())) {
            case ASC:
                return userReviewRepository.findAllByOrderByDateRatedAsc().orElse(null);
            case DESC:
                return userReviewRepository.findAllByOrderByDateRatedDesc().orElse(null);
            default:
                return userReviewRepository.findAll();
        }
    }

    public UserReview add(UserReview userReview){
        return userReviewRepository.save(userReview);
    }

    public UserReview update(UserReview userReview) {
        if(userReviewRepository.findById(userReview.getId()).isPresent())
            return userReviewRepository.save(userReview);
        else return null;
    }

    public void delete(UserReview userReview) {
        userReviewRepository.delete(userReview);
    }
}
