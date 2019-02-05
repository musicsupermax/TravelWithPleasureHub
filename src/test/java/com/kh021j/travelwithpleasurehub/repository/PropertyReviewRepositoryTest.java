package com.kh021j.travelwithpleasurehub.repository;

import com.kh021j.travelwithpleasurehub.propertyrent.model.PropertyReview;
import com.kh021j.travelwithpleasurehub.propertyrent.repository.PropertyRepository;
import com.kh021j.travelwithpleasurehub.propertyrent.repository.PropertyReviewRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class PropertyReviewRepositoryTest extends RepositoryBaseDomain {
    @Autowired
    private PropertyReviewRepository propertyReviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Before
    @Transactional
    public void innerSetup(){
        PropertyReview propertyAvailability =  PropertyReview.builder()
                .user(userRepository.findAll().get(0))
                .property(propertyRepository.findAll().get(0))
                .reviewText("other text")
                .dateRated(LocalDate.now().minusMonths(6))
                .rate(122)
                .build();
        propertyReviewRepository.save(propertyAvailability);
    }

    @Test
    @Transactional
    public void addPropertyReview() {
        assertThat(propertyReviewRepository.findAll().size()).isEqualTo(2);
        PropertyReview propertyAvailability =  PropertyReview.builder()
                .user(userRepository.findAll().get(0))
                .property(propertyRepository.findAll().get(0))
                .reviewText("other text3")
                .dateRated(LocalDate.now())
                .rate(122)
                .build();
        propertyReviewRepository.save(propertyAvailability);
        assertThat(propertyReviewRepository.findAll().size()).isEqualTo(3);

        propertyReviewRepository.save(propertyAvailability);
        assertThat(propertyReviewRepository.findAll().size()).isEqualTo(3);
    }

    @Test
    @Transactional
    public void updatePropertyReview() {
        assertThat(propertyReviewRepository.findAll().size()).isEqualTo(2);
        PropertyReview propertyAvailability = propertyReviewRepository.findAll().get(0);
        propertyReviewRepository.save(propertyAvailability.toBuilder()
                .reviewText("other text2")
                .build());
        assertThat(propertyReviewRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @Transactional
    public void findPropertyReviewById() {
        assertThat(propertyReviewRepository.findById(
                propertyReviewRepository.findAll().get(0).getId()).get())
                .isNotNull();

    }

    @Test
    @Transactional
    public void deletePropertyReview(){
        assertThat(propertyReviewRepository.findAll().size()).isEqualTo(2);
        propertyReviewRepository.delete(propertyReviewRepository.findAll().get(0));
        assertThat(propertyReviewRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void findAllPropertyReviews() {
        assertThat(propertyReviewRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @Transactional
    public void findPropertyReviewsByPropertyId(){
        assertThat(propertyReviewRepository.findByPropertyId(propertyRepository.findAll().get(0).getId())).isNotEmpty();
    }

    @Test
    @Transactional
    public void findPropertyReviewsByDateRatedAsc(){

        assertThat(propertyReviewRepository.findAllByOrderByDateRatedAsc()).isNotEmpty();
    }

    @Test
    @Transactional
    public void findPropertyReviewsByDateRatedDesc(){
        assertThat(propertyReviewRepository.findAllByOrderByDateRatedDesc()).isNotEmpty();
    }
}
