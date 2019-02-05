package com.kh021j.travelwithpleasurehub.propertyrent.controller;


import com.kh021j.travelwithpleasurehub.propertyrent.model.PropertyAvailability;
import com.kh021j.travelwithpleasurehub.propertyrent.service.PropertyAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/property-availability")
@CrossOrigin
public class PropertyAvailabilityController {

    @Autowired
    private PropertyAvailabilityService propertyAvailabilityService;


    @PostMapping
    public @ResponseBody PropertyAvailability addPropertyAvailability(@RequestParam String checkIn,
                                                                      @RequestParam String checkOut,
                                                                      @RequestParam Integer propertyId,
                                                                      @RequestParam Integer currentUserId
    ) {
        return propertyAvailabilityService.save(checkIn, checkOut, propertyId, currentUserId);
    }

    @PutMapping
    public @ResponseBody PropertyAvailability updatePropertyAvailability(
            @RequestBody PropertyAvailability propertyAvailability) {
        return propertyAvailabilityService.update(propertyAvailability);
    }

    @GetMapping
    public @ResponseBody Iterable<PropertyAvailability> getPropertyAvailabilityByUserIdAndPropertyId(@RequestParam Integer userId,
                                                                                                     @RequestParam Integer propertyId){
        return propertyAvailabilityService.findByUserIdAndPropertyId(userId, propertyId);
    }

    @GetMapping("/property/{id}")
    public @ResponseBody Iterable<PropertyAvailability> getPropertyAvailabilityByPropertyId(@PathVariable Integer id){
        return propertyAvailabilityService.findByPropertyId(id);
    }

    @GetMapping("/user/{id}")
    public @ResponseBody Iterable<PropertyAvailability> getPropertyAvailabilityByUserId(@PathVariable Integer id){
        return propertyAvailabilityService.findByUserId(id);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody void getPropertyAvailabilityById(@PathVariable Integer id){
        propertyAvailabilityService.delete(propertyAvailabilityService.findById(id));
    }

}
