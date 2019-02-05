package com.kh021j.travelwithpleasurehub.propertyrent.controller;

import com.kh021j.travelwithpleasurehub.propertyrent.model.PropertyReview;
import com.kh021j.travelwithpleasurehub.propertyrent.service.PropertyReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/property-reviews")
public class PropertyReviewController {

    @Autowired
    private PropertyReviewService propertyReviewService;

    @GetMapping
    public @ResponseBody Iterable<PropertyReview> getAllPropertyReviews(){
        return propertyReviewService.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody PropertyReview getPropertyReviewById(@PathVariable Integer id) {
        return propertyReviewService.findById(id);
    }

    @GetMapping(params = "propertyId")
    public @ResponseBody Iterable<PropertyReview> getPropertyReviewsByPropertyId(@RequestParam Integer propertyId) {
        return propertyReviewService.findByPropertyId(propertyId);
    }

    @GetMapping(params = "sortByDateRated")
    public @ResponseBody Iterable<PropertyReview> getPropertyReviewsSortedByDateRated(@RequestParam String sortByDateRated) {
        return propertyReviewService.findAllByOrderByDateRated(sortByDateRated);
    }

    @PostMapping
    public @ResponseBody PropertyReview addPropertyReview(@RequestBody PropertyReview propertyReview){
        return propertyReviewService.add(propertyReview);
    }

    @PutMapping
    public @ResponseBody PropertyReview updatePropertyReview(@RequestBody PropertyReview propertyReview) {
        return propertyReviewService.update(propertyReview);
    }

    @DeleteMapping
    public @ResponseBody void deletePropertyReview(@RequestBody PropertyReview propertyReview) {
        propertyReviewService.delete(propertyReview);
    }

}
