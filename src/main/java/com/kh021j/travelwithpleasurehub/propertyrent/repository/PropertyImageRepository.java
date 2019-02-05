package com.kh021j.travelwithpleasurehub.propertyrent.repository;

import com.kh021j.travelwithpleasurehub.propertyrent.model.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PropertyImageRepository extends JpaRepository<PropertyImage, Integer> {

    Optional<Iterable<PropertyImage>> findByPropertyId(Integer propertyId);


    @Query(value = "select * from property_image where property_id = ?1 limit 1", nativeQuery = true)
    PropertyImage findFirstByPropertyId(Integer propertyId);

}
