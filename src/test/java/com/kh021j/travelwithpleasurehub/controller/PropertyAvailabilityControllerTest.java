package com.kh021j.travelwithpleasurehub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh021j.travelwithpleasurehub.propertyrent.controller.PropertyAvailabilityController;
import com.kh021j.travelwithpleasurehub.propertyrent.model.Property;
import com.kh021j.travelwithpleasurehub.propertyrent.model.PropertyAvailability;
import com.kh021j.travelwithpleasurehub.propertyrent.repository.PropertyAvailabilityRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PropertyAvailabilityController.class)
public class PropertyAvailabilityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PropertyAvailabilityRepository propertyAvailabilityRepository;

    private final String PROPERTY_AVAILABLE_PATH = "/api/propertyAvailability";

    private Property property = Property.builder()
            .id(1212)
            .title("Flat1")
            .locality("locality")
            .address("Address")
            .description("new description")
            .price(320)
            .build();

    private PropertyAvailability propertyAvailability = PropertyAvailability.builder()
            .id(12)
            .property(property)
            .bookedSince(LocalDate.of(2012, 12, 12))
            .bookedUntil(LocalDate.now())
            .build();

    @Before
    public void setup() throws Exception {
        when(propertyAvailabilityRepository.findAll())
                .thenReturn(new ArrayList<>(Collections.singletonList(propertyAvailability)));
        when(propertyAvailabilityRepository.save(propertyAvailability)).thenReturn(propertyAvailability);
        when(propertyAvailabilityRepository.findById(propertyAvailability.getId())).thenReturn(Optional.of(propertyAvailability));
    }


    @Test
    public void add() throws Exception {
        String contactsRequestBody = objectMapper.writeValueAsString(propertyAvailability);
        mockMvc.perform(post(PROPERTY_AVAILABLE_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(contactsRequestBody))
                .andExpect(content().string(contactsRequestBody));
    }

    @Test
    public void update() throws Exception {
        String contactsRequestBody = objectMapper.writeValueAsString(propertyAvailability);

        mockMvc.perform(put(PROPERTY_AVAILABLE_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(contactsRequestBody))
                .andExpect(content().string(contactsRequestBody));
    }

    @Test
    public void delete() throws Exception {
        String contactsRequestBody = objectMapper.writeValueAsString(propertyAvailability);

        mockMvc.perform(MockMvcRequestBuilders.delete(PROPERTY_AVAILABLE_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(contactsRequestBody))
                .andExpect(status().isOk());

    }

    @Test
    public void getById() throws Exception {
        String contactsRequestBody = objectMapper.writeValueAsString(propertyAvailability);
        mockMvc.perform(get(PROPERTY_AVAILABLE_PATH + "/" + propertyAvailability.getId()))
                .andExpect(content().string(contactsRequestBody));
    }

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(get(PROPERTY_AVAILABLE_PATH)
                .accept(APPLICATION_JSON))
                .andExpect(content().string(objectMapper
                        .writeValueAsString(Collections.singletonList(propertyAvailability))));
    }


}
