package com.kh021j.travelwithpleasurehub.propertyrent.service;

import com.kh021j.travelwithpleasurehub.propertyrent.model.Application;
import com.kh021j.travelwithpleasurehub.propertyrent.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    public Iterable<Application> findAll(){
        return applicationRepository.findAll();
    }

    public Application findById(Integer id) {
        return applicationRepository.findById(id).orElse(null);
    }

    public Application add(Application application){
        return applicationRepository.save(application);
    }

    public Application update(Application application) {
        if(applicationRepository.findById(application.getId()).isPresent())
            return applicationRepository.save(application);
        else return null;
    }

    public void delete(Application application) {
        applicationRepository.delete(application);
    }

}
