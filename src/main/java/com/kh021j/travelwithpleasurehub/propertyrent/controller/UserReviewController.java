package com.kh021j.travelwithpleasurehub.propertyrent.controller;

import com.kh021j.travelwithpleasurehub.controller.enumeration.SortType;
import com.kh021j.travelwithpleasurehub.propertyrent.model.UserReview;
import com.kh021j.travelwithpleasurehub.propertyrent.repository.UserReviewRepository;
import com.kh021j.travelwithpleasurehub.propertyrent.service.UserReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/user-reviews")
public class UserReviewController {

    @Autowired
    private UserReviewService userReviewService;

    @GetMapping
    public @ResponseBody Iterable<UserReview> getAllUserReviews(){
        return userReviewService.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody UserReview getUserReviewById(@PathVariable Integer id) {
        return userReviewService.findById(id);
    }

    @GetMapping(params = "userId")
    public @ResponseBody Iterable<UserReview> getUserReviewsByUserId(@RequestParam Integer userId) {
        return userReviewService.findByUserId(userId);
    }

    @GetMapping(params = "sortByDateRated")
    public @ResponseBody Iterable<UserReview> getUserReviewsSortedByDateRated(@RequestParam String sortByDateRated) {
        return userReviewService.findAllByOrderByDateRated(sortByDateRated);
    }

    @PostMapping
    public @ResponseBody UserReview addUserReview(@RequestBody UserReview userReview){
        return userReviewService.add(userReview);
    }

    @PutMapping
    public @ResponseBody UserReview updateUserReview(@RequestBody UserReview userReview) {
        return userReviewService.update(userReview);
    }

    @DeleteMapping
    public @ResponseBody void deleteUserReview(@RequestBody UserReview userReview) {
        userReviewService.delete(userReview);
    }

}
