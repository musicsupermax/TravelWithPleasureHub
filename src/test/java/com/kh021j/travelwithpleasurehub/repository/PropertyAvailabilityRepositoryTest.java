package com.kh021j.travelwithpleasurehub.repository;

import com.kh021j.travelwithpleasurehub.propertyrent.model.PropertyAvailability;
import com.kh021j.travelwithpleasurehub.propertyrent.repository.PropertyAvailabilityRepository;
import com.kh021j.travelwithpleasurehub.propertyrent.repository.PropertyRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class PropertyAvailabilityRepositoryTest extends RepositoryBaseDomain {
    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private PropertyAvailabilityRepository propertyAvailabilityRepository;

    @Test
    @Transactional
    public void addPropertyAvailability() {
        assertThat(propertyAvailabilityRepository.findAll().size()).isEqualTo(1);
        PropertyAvailability propertyAvailability =  PropertyAvailability.builder()
                .bookedSince(LocalDate.of(2015,5,22))
                .bookedUntil(LocalDate.now().minusMonths(2))
                .property(propertyRepository.findAll().get(0))
                .build();

        propertyAvailabilityRepository.save(propertyAvailability);
        assertThat(propertyAvailabilityRepository.findAll().size()).isEqualTo(2);

        propertyAvailabilityRepository.save(propertyAvailability);
        assertThat(propertyAvailabilityRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @Transactional
    public void updatePropertyAvailability() {
        assertThat(propertyAvailabilityRepository.findAll().size()).isEqualTo(1);
        PropertyAvailability propertyAvailability = propertyAvailabilityRepository.findAll().get(0);
        propertyAvailabilityRepository.save(propertyAvailability.toBuilder()
                .bookedUntil(LocalDate.now().minusMonths(6))
                .build());
        assertThat(propertyAvailabilityRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void findPropertyAvailabilityById() {
        assertThat(propertyAvailabilityRepository.findById(
                propertyAvailabilityRepository.findAll().get(0).getId()).get())
                .isNotNull();

    }

    @Test
    @Transactional
    public void deletePropertyAvailability(){
        assertThat(propertyAvailabilityRepository.findAll().size()).isEqualTo(1);
        propertyAvailabilityRepository.deleteById(propertyAvailabilityRepository.findAll().get(0).getId());
        assertThat(propertyAvailabilityRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @Transactional
    public void findAllPropertyAvailabilities() {
        assertThat(propertyAvailabilityRepository.findAll().size()).isEqualTo(1);
    }
}
