package com.kh021j.travelwithpleasurehub.repository;

import com.kh021j.travelwithpleasurehub.TravelwithpleasurehubApplication;
import com.kh021j.travelwithpleasurehub.model.*;
import com.kh021j.travelwithpleasurehub.model.enumiration.MeetingType;
import com.kh021j.travelwithpleasurehub.propertyrent.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.*;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TravelwithpleasurehubApplication.class)
public abstract class RepositoryBaseDomain {

    @Autowired
    private EntityManager entityManager;

    @Before
    @Transactional
    public void setup() {
        User user = User.builder()
                .firstName("fName")
                .secondName("sName")
                .password("easypassword")
                .additionalInfo("add info")
                .email("smart@gmail.com")
                .username("us name")
                .build();
        entityManager.persist(user);

        PropertyType propertyType = PropertyType.builder()
                .title("title")
                .build();
        entityManager.persist(propertyType);

        Property property = Property.builder()
                .title("Flat1")
                .propertyType(propertyType)
                .owner(user)
                .locality("locality")
                .address("Address")
                .description("new description")
                .price(320)
                .build();
        entityManager.persist(property);

        Application application = Application.builder()
                .applicationText("some text")
                .isApproved(false)
                .property(property)
                .rentSince(LocalDate.of(2012, 12, 12))
                .rentUntil(LocalDate.now())
                .user(user)
                .build();
        entityManager.persist(application);

        PropertyAvailability propertyAvailability = PropertyAvailability.builder()
                .property(property)
                .bookedSince(LocalDate.of(2012, 12, 12))
                .bookedUntil(LocalDate.now())
                .build();
        entityManager.persist(propertyAvailability);

        PropertyReview propertyReview = PropertyReview.builder()
                .property(property)
                .user(user)
                .reviewText("review text")
                .dateRated(LocalDate.now())
                .rate(12)
                .build();
        entityManager.persist(propertyReview);

        UserReview userReview = UserReview.builder()
                .rate(12)
                .madeByUserId(user.getId())
                .reviewText("review text")
                .user(user)
                .dateRated(LocalDate.now())
                .build();
        entityManager.persist(userReview);

        Meeting meeting = Meeting.builder()
                .header("simple")
                .content("asd")
//                .timeOfAction(OffsetDateTime.of(2018, 12, 12, 15, 30, 0, 0, ZoneOffset.UTC))
                .location("dqwdwq")
                .meetingType(MeetingType.CINEMA)
                .links(Arrays.asList("1", "2"))
                .owner(user)
                .build();
        entityManager.persist(meeting);
    }
}
