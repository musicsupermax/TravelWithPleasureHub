package com.kh021j.travelwithpleasurehub.propertyrent.controller;

import com.kh021j.travelwithpleasurehub.propertyrent.model.PropertyImage;
import com.kh021j.travelwithpleasurehub.propertyrent.service.PropertyImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/property-image/property")
@CrossOrigin
public class PropertyImageController {

    @Autowired
    private PropertyImageService propertyImageService;


    @GetMapping("/{id}")
    public @ResponseBody Iterable<PropertyImage> getPropertyImageByPropertyId(@PathVariable Integer id) {
        return propertyImageService.findByPropertyId(id);
    }

    @GetMapping("/first/{id}")
    public @ResponseBody PropertyImage getFirstPropertyImageByPropertyId(@PathVariable Integer id) {
        return propertyImageService.findFirstByPropertyId(id);
    }

}
