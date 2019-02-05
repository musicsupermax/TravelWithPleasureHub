package com.kh021j.travelwithpleasurehub.repository;

import com.kh021j.travelwithpleasurehub.propertyrent.model.UserReview;
import com.kh021j.travelwithpleasurehub.propertyrent.repository.UserReviewRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class UserReviewRepositoryTest extends RepositoryBaseDomain {

    @Autowired
    private UserReviewRepository userReviewRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    @Transactional
    public void addUserReview() {
        assertThat(userReviewRepository.findAll().size()).isEqualTo(1);
        UserReview userReview =  UserReview.builder()
                .rate(12)
                .madeByUserId(userRepository.findAll().get(0).getId())
                .reviewText("review text")
                .user(userRepository.findAll().get(0))
                .dateRated(LocalDate.now())
                .build();
        userReviewRepository.save(userReview);
        assertThat(userReviewRepository.findAll().size()).isEqualTo(2);

        userReviewRepository.save(userReview);
        assertThat(userReviewRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @Transactional
    public void updateUserReview() {
        assertThat(userReviewRepository.findAll().size()).isEqualTo(1);
        UserReview userReview = userReviewRepository.findAll().get(0);
        userReviewRepository.save(userReview.toBuilder().
                reviewText("new text!")
                .build());
        assertThat(userReviewRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void findUserReviewById() {
        assertThat(userReviewRepository.findById(
                userReviewRepository.findAll().get(0).getId()).get())
                .isNotNull();
    }

    @Test
    @Transactional
    public void deleteUserReview(){
        assertThat(userReviewRepository.findAll().size()).isEqualTo(1);
        userReviewRepository.delete(userReviewRepository.findAll().get(0));
        assertThat(userReviewRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @Transactional
    public void findAllUserReviews() {
        assertThat(userReviewRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void findPropertyReviewsByPropertyId(){
        assertThat(userReviewRepository.findByUserId(userRepository.findAll().get(0).getId())).isNotEmpty();
    }

    @Test
    @Transactional
    public void findPropertyReviewsByDateRatedAsc(){

        assertThat(userReviewRepository.findAllByOrderByDateRatedAsc()).isNotEmpty();
    }

    @Test
    @Transactional
    public void findPropertyReviewsByDateRatedDesc(){
        assertThat(userReviewRepository.findAllByOrderByDateRatedDesc()).isNotEmpty();
    }
}
