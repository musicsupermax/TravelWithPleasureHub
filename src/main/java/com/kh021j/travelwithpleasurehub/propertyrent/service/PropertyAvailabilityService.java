package com.kh021j.travelwithpleasurehub.propertyrent.service;


import com.kh021j.travelwithpleasurehub.model.User;
import com.kh021j.travelwithpleasurehub.propertyrent.model.Property;
import com.kh021j.travelwithpleasurehub.propertyrent.model.PropertyAvailability;
import com.kh021j.travelwithpleasurehub.propertyrent.repository.PropertyAvailabilityRepository;
import com.kh021j.travelwithpleasurehub.propertyrent.repository.PropertyRepository;
import com.kh021j.travelwithpleasurehub.repository.UserRepository;
import com.kh021j.travelwithpleasurehub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class PropertyAvailabilityService {

    @Autowired
    private PropertyAvailabilityRepository propertyAvailabilityRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserService userService;


    public Iterable<PropertyAvailability> findAll(){
        return propertyAvailabilityRepository.findAll();
    }

    public PropertyAvailability save(String checkIn, String checkOut, Integer propertyId, Integer currentUserId) {
        LocalDate checkInDate = LocalDate.parse(checkIn);
        LocalDate checkOutDate = LocalDate.parse(checkOut);
        PropertyAvailability propertyAvailability = new PropertyAvailability(
                checkInDate, checkOutDate, propertyRepository.findById(propertyId).orElse(new Property()),
                    userService.getById(currentUserId).orElse(new User()));
        return propertyAvailabilityRepository.save(propertyAvailability);
    }

    public PropertyAvailability update(PropertyAvailability propertyAvailability) {
        return propertyAvailabilityRepository.save(propertyAvailability);
    }

    public void delete(PropertyAvailability propertyAvailability) {
        propertyAvailabilityRepository.delete(propertyAvailability);
    }

    public PropertyAvailability findById(Integer id){
        return propertyAvailabilityRepository.findById(id).orElse(new PropertyAvailability());
    }

    public Iterable<PropertyAvailability> findByPropertyId(Integer id){
        return propertyAvailabilityRepository.findByPropertyId(id).orElse(new ArrayList<>());
    }

    public Iterable<PropertyAvailability> findByUserId(Integer id){
        return propertyAvailabilityRepository.findByUserId(id).orElse(new ArrayList<>());
    }

    public Iterable<PropertyAvailability> findByUserIdAndPropertyId(Integer userId, Integer propertyId){
        return propertyAvailabilityRepository.findByUserIdAndPropertyId(userId, propertyId).orElse(new ArrayList<>());
    }

}
