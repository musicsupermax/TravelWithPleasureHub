package com.kh021j.travelwithpleasurehub.repository;

import com.kh021j.travelwithpleasurehub.propertyrent.model.Application;
import com.kh021j.travelwithpleasurehub.propertyrent.repository.ApplicationRepository;
import com.kh021j.travelwithpleasurehub.propertyrent.repository.PropertyRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationRepositoryTest extends RepositoryBaseDomain {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;


    @Test
    @Transactional
    public void addApplication() {
        assertThat(applicationRepository.findAll().size()).isEqualTo(1);
       Application application =  Application.builder()
                .rentSince(LocalDate.now())
                .rentUntil(LocalDate.now())
                .user(userRepository.findAll().get(0))
                .property(propertyRepository.findAll().get(0))
                .build();
        applicationRepository.save(application);
        assertThat(applicationRepository.findAll().size()).isEqualTo(2);

        applicationRepository.save(application);
        assertThat(applicationRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @Transactional
    public void updateApplication() {
        assertThat(applicationRepository.findAll().size()).isEqualTo(1);
        Application application = applicationRepository.findAll().get(0);
        applicationRepository.save(application.toBuilder().
                applicationText("new text!")
                .build());
        assertThat(applicationRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void findApplicationById() {
        assertThat(applicationRepository.findById(
                applicationRepository.findAll().get(0).getId()).get())
                .isNotNull();

    }

    @Test
    @Transactional
    public void deleteApplication(){
        assertThat(applicationRepository.findAll().size()).isEqualTo(1);
        applicationRepository.delete(applicationRepository.findAll().get(0));
        assertThat(applicationRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @Transactional
    public void findAllApplications() {
        assertThat(applicationRepository.findAll().size()).isEqualTo(1);
    }

}
