package com.kh021j.travelwithpleasurehub.propertyrent.controller;

import com.kh021j.travelwithpleasurehub.propertyrent.model.Application;
import com.kh021j.travelwithpleasurehub.propertyrent.repository.ApplicationRepository;
import com.kh021j.travelwithpleasurehub.propertyrent.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping
    public @ResponseBody Iterable<Application> getAllApplications(){
        return applicationService.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody Application getApplicationById(@PathVariable Integer id) {
        return applicationService.findById(id);
    }

    @PostMapping
    public @ResponseBody Application addApplication(@RequestBody Application application){
        return applicationService.add(application);
    }

    @PutMapping
    public @ResponseBody Application updateApplication(@RequestBody Application application) {
        return applicationService.update(application);
    }

    @DeleteMapping
    public @ResponseBody void deleteApplication(@RequestBody Application application) {
        applicationService.delete(application);
    }

}
