package com.kh021j.travelwithpleasurehub.propertyrent.service;

import com.kh021j.travelwithpleasurehub.controller.enumeration.SortType;
import com.kh021j.travelwithpleasurehub.propertyrent.model.PropertyReview;
import com.kh021j.travelwithpleasurehub.propertyrent.repository.PropertyReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PropertyReviewService {

    @Autowired
    private PropertyReviewRepository propertyReviewRepository;


    public Iterable<PropertyReview> findAll(){
        return propertyReviewRepository.findAll();
    }

    public PropertyReview findById(Integer id) {
        return propertyReviewRepository.findById(id).orElse(null);
    }

    public Iterable<PropertyReview> findByPropertyId(Integer propertyId) {
        return propertyReviewRepository.findByPropertyId(propertyId).orElse(new ArrayList<>());
    }

    public Iterable<PropertyReview> findAllByOrderByDateRated(String sortByDateRated) {
        switch (SortType.valueOf(sortByDateRated.toUpperCase())) {
            case ASC:
                return propertyReviewRepository.findAllByOrderByDateRatedAsc().orElse(new ArrayList<>());
            case DESC:
                return propertyReviewRepository.findAllByOrderByDateRatedDesc().orElse(new ArrayList<>());
            default:
                return propertyReviewRepository.findAll();
        }
    }

    public PropertyReview add(PropertyReview propertyReview){
        return propertyReviewRepository.save(propertyReview);
    }

    public PropertyReview update(PropertyReview propertyReview) {
        if(propertyReviewRepository.findById(propertyReview.getId()).isPresent())
            return propertyReviewRepository.save(propertyReview);
        else return null;
    }

    public void delete(PropertyReview propertyReview) {
        propertyReviewRepository.delete(propertyReview);
    }

}
