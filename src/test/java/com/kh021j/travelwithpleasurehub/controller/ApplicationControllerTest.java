package com.kh021j.travelwithpleasurehub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh021j.travelwithpleasurehub.model.enumiration.UserRole;
import com.kh021j.travelwithpleasurehub.propertyrent.controller.ApplicationController;
import com.kh021j.travelwithpleasurehub.propertyrent.model.Application;
import com.kh021j.travelwithpleasurehub.propertyrent.model.Property;
import com.kh021j.travelwithpleasurehub.propertyrent.model.PropertyType;
import com.kh021j.travelwithpleasurehub.model.User;
import com.kh021j.travelwithpleasurehub.propertyrent.repository.ApplicationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest(ApplicationController.class)
public class ApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ApplicationRepository applicationRepository;

    private final String APPLICATION_PATH = "/api/application";

    User user = User.builder()
            .id(21)
            .username("username")
            .email("email")
            .additionalInfo("ad info")
            .password("easypassword")
            .secondName("sName")
            .firstName("fName")
            .phoneNumber("phone number")
            .location("Ukraine")
            .role(UserRole.ROLE_USER)
            .pathToPhoto("path")
            .build();

    Property property = Property.builder()
            .id(20)
            .price(123)
            .address("address")
            .description("descr")
            .locality("locality")
            .title("title")
            .owner(user)
            .propertyType(PropertyType.builder().id(16).title("new").build())
            .build();

    Application application = Application.builder()
            .id(19)
            .rentSince(LocalDate.now().minusMonths(3))
            .rentUntil(LocalDate.now())
            .isApproved(false)
            .applicationText("text")
            .property(property)
            .user(user)
            .build();

    @Before
    public void setup() throws Exception {
        when(applicationRepository.findAll())
                .thenReturn(new ArrayList<>(Collections.singletonList(application)));
        when(applicationRepository.save(application)).thenReturn(application);
        when(applicationRepository.findById(application.getId())).thenReturn(Optional.of(application));
    }


    @Test
    public void add() throws Exception {
        String contactsRequestBody = objectMapper.writeValueAsString(application);
        mockMvc.perform(post(APPLICATION_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(contactsRequestBody))
                .andExpect(content().string(contactsRequestBody));
    }

    @Test
    public void update() throws Exception {
        String contactsRequestBody = objectMapper.writeValueAsString(application);

        mockMvc.perform(put(APPLICATION_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(contactsRequestBody))
                .andExpect(content().string(contactsRequestBody));
    }

    @Test
    public void delete() throws Exception {
        String contactsRequestBody = objectMapper.writeValueAsString(application);

        mockMvc.perform(MockMvcRequestBuilders.delete(APPLICATION_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(contactsRequestBody));

    }

    @Test
    public void getById() throws Exception {
        String contactsRequestBody = objectMapper.writeValueAsString(application);
        mockMvc.perform(get(APPLICATION_PATH + "/" + application.getId()))
                .andExpect(content().string(contactsRequestBody));
    }

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(get(APPLICATION_PATH)
                .accept(APPLICATION_JSON))
                .andExpect(content().string(objectMapper
                        .writeValueAsString(Collections.singletonList(application))));
    }

}
