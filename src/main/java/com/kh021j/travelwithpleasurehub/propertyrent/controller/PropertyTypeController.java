package com.kh021j.travelwithpleasurehub.propertyrent.controller;


import com.kh021j.travelwithpleasurehub.propertyrent.model.PropertyType;
import com.kh021j.travelwithpleasurehub.propertyrent.repository.PropertyTypeRepository;
import com.kh021j.travelwithpleasurehub.propertyrent.service.PropertyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/property-types")
public class PropertyTypeController {

    @Autowired
    private PropertyTypeService propertyTypeService;

    @GetMapping
    public @ResponseBody Iterable<PropertyType> getAllPropertyTypes(){
        return propertyTypeService.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody PropertyType getPropertyTypeById(@PathVariable Integer id) {
        return propertyTypeService.findById(id);
    }

    @PostMapping
    public @ResponseBody PropertyType addPropertyType(@RequestBody PropertyType propertyType) {
        return propertyTypeService.add(propertyType);
    }

    @PutMapping
    public @ResponseBody PropertyType updatePropertyType(@RequestBody PropertyType propertyType) {
        return propertyTypeService.update(propertyType);
    }

    @DeleteMapping
    public @ResponseBody void deletePropertyType(@RequestBody PropertyType propertyType) {
        propertyTypeService.delete(propertyType);
    }

}
