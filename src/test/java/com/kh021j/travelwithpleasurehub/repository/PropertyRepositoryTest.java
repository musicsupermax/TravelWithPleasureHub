package com.kh021j.travelwithpleasurehub.repository;

import com.kh021j.travelwithpleasurehub.propertyrent.model.Property;
import com.kh021j.travelwithpleasurehub.propertyrent.repository.PropertyRepository;
import com.kh021j.travelwithpleasurehub.propertyrent.repository.PropertyTypeRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class PropertyRepositoryTest extends RepositoryBaseDomain {
    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyTypeRepository propertyTypeRepository;


    @Before
    @Transactional
    public void innerSetup() {
        Property property = Property.builder()
                .title("Flat2")
                .propertyType(propertyTypeRepository.findAll().get(0))
                .owner(userRepository.findAll().get(0))
                .locality("locality2")
                .address("Adress2")
                .description("description2")
                .price(510)
                .build();
        propertyRepository.save(property);
    }

    @Test
    @Transactional
    public void addProperty() {
        assertThat(propertyRepository.findAll().size()).isEqualTo(2);
        Property property = Property.builder()
                .title("Flat3")
                .propertyType(propertyTypeRepository.findAll().get(0))
                .owner(userRepository.findAll().get(0))
                .locality("locality3")
                .address("Adress3")
                .description("description3")
                .price(710)
                .build();
        propertyRepository.save(property);
        assertThat(propertyRepository.findAll().size()).isEqualTo(3);
    }

    @Test
    @Transactional
    public void updateProperty() {
        Assertions.assertThat(propertyRepository.findAll().size()).isEqualTo(2);
        Property property = propertyRepository.findAll().get(0);
        propertyRepository.save(property.toBuilder().
                price(345)
                .build());
        Assertions.assertThat(propertyRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @Transactional
    public void deleteProperty() {
        assertThat(propertyRepository.findAll().size()).isEqualTo(2);
        propertyRepository.delete(propertyRepository.findAll().get(0));
        assertThat(propertyRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void findPropertyById() {
        Assertions.assertThat(propertyRepository.findById(
                propertyRepository.findAll().get(0).getId()).get())
                .isNotNull();

    }

    @Test
    @Transactional
    public void findByPriceLessThanEqual() {
        assertThat(propertyRepository.findByPriceLessThanEqual(515).isPresent()).isTrue();
    }

    @Test
    @Transactional
    public void findByLocality() {
        assertThat(propertyRepository.findByLocality("locality").isPresent()).isTrue();
    }

    @Test
    @Transactional
    public void findByAddress() {
        assertThat(propertyRepository.findByAddressContaining("Address").isPresent()).isTrue();
    }

    @Test
    @Transactional
    public void findByAvailableInChosenPeriod() {
        assertThat(propertyRepository.findByAvailabilityInPeriod(
                LocalDate.of(2012,11,12),
                LocalDate.now())
                .isPresent()).isTrue();
    }


    @Test
    @Transactional
    public void findAllByOrderByPriceAsc(){
        assertThat(propertyRepository.findAllByOrderByPriceAsc().get().iterator().next().getPrice()).isEqualTo(320);
    }

    @Test
    @Transactional
    public void findAllByOrderByPriceDesc(){
        assertThat(propertyRepository.findAllByOrderByPriceAsc().get().iterator().next().getPrice()).isEqualTo(510);
    }

    @Test
    @Transactional
    public void findAllProperties() {
        assertThat(propertyRepository.findAll().size()).isEqualTo(2);
    }
}

