package com.kh021j.travelwithpleasurehub.repository;

import com.kh021j.travelwithpleasurehub.propertyrent.model.PropertyType;
import com.kh021j.travelwithpleasurehub.propertyrent.repository.PropertyTypeRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

public class PropertyTypeRepositoryTest extends RepositoryBaseDomain {

    @Autowired
    private PropertyTypeRepository propertyTypeRepository;


    @Test
    @Transactional
    public void addPropertyType() {
        assertThat(propertyTypeRepository.findAll().size()).isEqualTo(1);
        PropertyType propertyType = PropertyType.builder()
                .title("3123-title")
                .build();
        propertyTypeRepository.save(propertyType);
        assertThat(propertyTypeRepository.findAll().size()).isEqualTo(2);

        propertyTypeRepository.save(propertyType);
        assertThat(propertyTypeRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @Transactional
    public void updatePropertyType() {
        assertThat(propertyTypeRepository.findAll().size()).isEqualTo(1);
        PropertyType propertyType = propertyTypeRepository.findAll().get(0);
        propertyTypeRepository.save(propertyType.toBuilder().
                title("new title!")
                .build());
        assertThat(propertyTypeRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void findPropertyTypeById() {
        assertThat(propertyTypeRepository.findById(
                propertyTypeRepository.findAll().get(0).getId()).get())
                .isNotNull();

    }

    @Test
    @Transactional
    public void deletePropertyType() {
        assertThat(propertyTypeRepository.findAll().size()).isEqualTo(1);
        propertyTypeRepository.delete(propertyTypeRepository.findAll().get(0));
        assertThat(propertyTypeRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @Transactional
    public void findAllAPropertyTypes() {
        assertThat(propertyTypeRepository.findAll().size()).isEqualTo(1);
    }

}
