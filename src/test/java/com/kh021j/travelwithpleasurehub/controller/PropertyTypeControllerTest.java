package com.kh021j.travelwithpleasurehub.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh021j.travelwithpleasurehub.propertyrent.controller.PropertyTypeController;
import com.kh021j.travelwithpleasurehub.propertyrent.model.PropertyType;
import com.kh021j.travelwithpleasurehub.propertyrent.repository.PropertyTypeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest(PropertyTypeController.class)
public class PropertyTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PropertyTypeRepository propertyTypeRepository;

    private final String PROPERTY_TYPE_PATH = "/api/propertyType";

    private PropertyType propertyType = PropertyType.builder()
            .id(12)
            .title("title")
            .build();

    @Before
    public void setup() throws Exception {
        when(propertyTypeRepository.findAll())
                .thenReturn(new ArrayList<>(Collections.singletonList(propertyType)));
        when(propertyTypeRepository.save(propertyType)).thenReturn(propertyType);
        when(propertyTypeRepository.findById(propertyType.getId())).thenReturn(Optional.of(propertyType));
    }


    @Test
    public void add() throws Exception {
        String contactsRequestBody = objectMapper.writeValueAsString(propertyType);
        mockMvc.perform(post(PROPERTY_TYPE_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(contactsRequestBody))
                .andExpect(content().string(contactsRequestBody));
    }

    @Test
    public void update() throws Exception {
        String contactsRequestBody = objectMapper.writeValueAsString(propertyType);

        mockMvc.perform(put(PROPERTY_TYPE_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(contactsRequestBody))
                .andExpect(content().string(contactsRequestBody));
    }

    @Test
    public void delete() throws Exception {
        String contactsRequestBody = objectMapper.writeValueAsString(propertyType);

        mockMvc.perform(MockMvcRequestBuilders.delete(PROPERTY_TYPE_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(contactsRequestBody));

    }

    @Test
    public void getById() throws Exception {
        String contactsRequestBody = objectMapper.writeValueAsString(propertyType);
        mockMvc.perform(get(PROPERTY_TYPE_PATH + "/" + propertyType.getId()))
                .andExpect(content().string(contactsRequestBody));
    }

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(get(PROPERTY_TYPE_PATH)
                .accept(APPLICATION_JSON))
                .andExpect(content().string(objectMapper
                        .writeValueAsString(Collections.singletonList(propertyType))));
    }

}
