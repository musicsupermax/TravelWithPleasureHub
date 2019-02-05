package com.kh021j.travelwithpleasurehub.propertyrent.service;


import com.kh021j.travelwithpleasurehub.propertyrent.model.PropertyType;
import com.kh021j.travelwithpleasurehub.propertyrent.repository.PropertyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyTypeService {

    @Autowired
    private PropertyTypeRepository propertyTypeRepository;


    public Iterable<PropertyType> findAll(){
        return propertyTypeRepository.findAll();
    }

    public PropertyType findById(Integer id) {
        return propertyTypeRepository.findById(id).orElse(null);
    }

    public PropertyType add(PropertyType propertyType) {
        return propertyTypeRepository.save(propertyType);
    }

    public PropertyType update(PropertyType propertyType) {
        if(propertyTypeRepository.findById(propertyType.getId()).isPresent())
            return propertyTypeRepository.save(propertyType);
        else return null;
    }

    public void delete(PropertyType propertyType) {
        propertyTypeRepository.delete(propertyType);
    }

}
