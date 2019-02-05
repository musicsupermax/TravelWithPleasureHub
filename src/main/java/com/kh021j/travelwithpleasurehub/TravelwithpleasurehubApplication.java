package com.kh021j.travelwithpleasurehub;

import com.kh021j.travelwithpleasurehub.propertyrent.service.ImgurAPIService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TravelwithpleasurehubApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(TravelwithpleasurehubApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(TravelwithpleasurehubApplication.class, args);
        ImgurAPIService.getToken();
    }
}
