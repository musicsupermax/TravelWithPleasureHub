package com.kh021j.travelwithpleasurehub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh021j.travelwithpleasurehub.propertyrent.controller.PropertyReviewController;
import com.kh021j.travelwithpleasurehub.propertyrent.model.Property;
import com.kh021j.travelwithpleasurehub.propertyrent.model.PropertyReview;
import com.kh021j.travelwithpleasurehub.propertyrent.repository.PropertyReviewRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PropertyReviewController.class)
public class PropertyReviewControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PropertyReviewRepository propertyReviewRepository;

    private final String PROPERTY_REVIEW_PATH = "/api/propertyReview";

    private Property property = Property.builder()
            .id(1)
            .title("Flat1")
            .locality("locality")
            .address("Address")
            .description("new description")
            .price(320)
            .build();

    private PropertyReview propertyReview = PropertyReview.builder()
            .id(2)
            .property(property)
            .reviewText("review text")
            .dateRated(LocalDate.now())
            .rate(12)
            .build();


    @Before
    public void setup() throws Exception {
        when(propertyReviewRepository.findAll())
                .thenReturn(new ArrayList<>(Collections.singletonList(propertyReview)));
        when(propertyReviewRepository.save(propertyReview)).thenReturn(propertyReview);
        when(propertyReviewRepository.findById(propertyReview.getId())).thenReturn(Optional.of(propertyReview));
        when(propertyReviewRepository.findAllByOrderByDateRatedAsc()).thenReturn(Optional.of(Collections.singletonList(propertyReview)));
        when(propertyReviewRepository.findByPropertyId(property.getId())).thenReturn(Optional.of(Collections.singletonList(propertyReview)));
    }

    @Test
    public void add() throws Exception {
        String contactsRequestBody = objectMapper.writeValueAsString(propertyReview);
        mockMvc.perform(post(PROPERTY_REVIEW_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(contactsRequestBody))
                .andExpect(content().string(contactsRequestBody));
    }

    @Test
    public void update() throws Exception {
        String contactsRequestBody = objectMapper.writeValueAsString(propertyReview);

        mockMvc.perform(put(PROPERTY_REVIEW_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(contactsRequestBody))
                .andExpect(content().string(contactsRequestBody));
    }

    @Test
    public void delete() throws Exception {
        String contactsRequestBody = objectMapper.writeValueAsString(propertyReview);

        mockMvc.perform(MockMvcRequestBuilders.delete(PROPERTY_REVIEW_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(contactsRequestBody));

    }

    @Test
    public void getById() throws Exception {
        String contactsRequestBody = objectMapper.writeValueAsString(propertyReview);
        mockMvc.perform(get(PROPERTY_REVIEW_PATH + "/" + propertyReview.getId()))
                .andExpect(content().string(contactsRequestBody));
    }

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(get(PROPERTY_REVIEW_PATH)
                .accept(APPLICATION_JSON))
                .andExpect(content().string(objectMapper
                        .writeValueAsString(Collections.singletonList(propertyReview))));
    }

    @Test
    public void getAllByPropertyId() throws Exception {
        mockMvc.perform(get(PROPERTY_REVIEW_PATH)
                .accept(APPLICATION_JSON)
                .param("propertyId", String.valueOf(property.getId())))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(Collections.singletonList(propertyReview))));
    }

    @Test
    public void getAllByDateAndSort() throws Exception {
        mockMvc.perform(get(PROPERTY_REVIEW_PATH)
                .accept(APPLICATION_JSON)
                .param("sortByDateRated", "asc"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(Collections.singletonList(propertyReview))));
    }

}
