package com.kh021j.travelwithpleasurehub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh021j.travelwithpleasurehub.model.enumiration.UserRole;
import com.kh021j.travelwithpleasurehub.propertyrent.controller.PropertyController;
import com.kh021j.travelwithpleasurehub.propertyrent.model.Property;
import com.kh021j.travelwithpleasurehub.propertyrent.model.PropertyType;
import com.kh021j.travelwithpleasurehub.model.User;
import com.kh021j.travelwithpleasurehub.propertyrent.repository.PropertyRepository;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PropertyController.class)
public class PropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PropertyRepository propertyRepository;

    private final String PROPERTY_PATH = "/api/property";

    private User user = User.builder()
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

    private PropertyType propertyType = PropertyType.builder()
            .title("title").build();


    private Property property1 = Property.builder()
            .id(1)
            .title("Flat1")
            .propertyType(propertyType)
            .owner(user)
            .locality("locality")
            .address("Address")
            .description("new description")
            .price(320)
            .build();

    private Property property2 = Property.builder()
            .id(2)
            .title("Flat2")
            .propertyType(propertyType)
            .owner(user)
            .locality("locality2")
            .address("Address2")
            .description("new description2")
            .price(520)
            .build();

    @Before
    public void setup() {
        when(propertyRepository.findAll())
                .thenReturn(new ArrayList<>(Arrays.asList(property1, property2)));
        when(propertyRepository.findByPriceLessThanEqual(400))
                .thenReturn(Optional.of(new ArrayList<>(Arrays.asList(property1))));
        when(propertyRepository.findByAvailabilityInPeriod(LocalDate.of(2000, 12, 12),
                LocalDate.of(2020, 12, 12)))
                .thenReturn(Optional.of(new ArrayList<>(Arrays.asList(property1, property2))));
        when(propertyRepository.findByLocality("locality"))
                .thenReturn(Optional.of(Collections.singletonList(property1)));
        when(propertyRepository.findByAddressContaining("Address"))
                .thenReturn(Optional.of(Collections.singletonList(property1)));
        when(propertyRepository.findByAvailabilityInPeriodAndSort(LocalDate.of(2000, 12, 12),
                LocalDate.of(2020, 12, 12), "desc"))
                .thenReturn(Optional.of(new ArrayList<>(Arrays.asList(property1, property2))));
        when(propertyRepository.findAllByOrderByPriceAsc())
                .thenReturn(Optional.of(new ArrayList<>(Arrays.asList(property1, property2))));
        when(propertyRepository.save(property1)).thenReturn(property1);
        when(propertyRepository.findById(property1.getId())).thenReturn(Optional.of(property1));
    }

    @Test
    public void add() throws Exception {
        String contactsRequestBody = objectMapper.writeValueAsString(property1);
        mockMvc.perform(post(PROPERTY_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(contactsRequestBody))
                .andExpect(content().string(contactsRequestBody));
    }

    @Test
    public void update() throws Exception {
        String contactsRequestBody = objectMapper.writeValueAsString(property1);

        mockMvc.perform(put(PROPERTY_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(contactsRequestBody))
                .andExpect(content().string(contactsRequestBody));
    }

    @Test
    public void delete() throws Exception {
        String contactsRequestBody = objectMapper.writeValueAsString(property1);

        mockMvc.perform(MockMvcRequestBuilders.delete(PROPERTY_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(contactsRequestBody))
                .andExpect(status().isOk());
    }

    @Test
    public void getById() throws Exception {
        String contactsRequestBody = objectMapper.writeValueAsString(property1);
        mockMvc.perform(get(PROPERTY_PATH + "/" + property1.getId()))
                .andExpect(content().string(contactsRequestBody));
    }

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(get(PROPERTY_PATH)
                .accept(APPLICATION_JSON))
                .andExpect(content().string(objectMapper
                        .writeValueAsString(Arrays.asList(property1, property2))));
    }

    @Test
    public void getAllByPriceLessThan() throws Exception {
        mockMvc.perform(get(PROPERTY_PATH)
                .accept(APPLICATION_JSON)
                .param("price", "400"))
                .andExpect(content().string(objectMapper
                        .writeValueAsString(Optional.of(Arrays.asList(property1)))));
    }

    @Test
    public void getAllByLocality() throws Exception {
        mockMvc.perform(get(PROPERTY_PATH)
                .accept(APPLICATION_JSON)
                .param("locality", "locality"))
                .andExpect(content().string(objectMapper
                        .writeValueAsString(Collections.singletonList(property1))));
    }

    @Test
    public void getAllByAddress() throws Exception {
        mockMvc.perform(get(PROPERTY_PATH)
                .accept(APPLICATION_JSON)
                .param("address", "Address"))
                .andExpect(content().string(objectMapper
                        .writeValueAsString(Arrays.asList(property1))));
    }

    @Test
    public void getAllByDate() throws Exception {
        mockMvc.perform(get(PROPERTY_PATH)
                .accept(APPLICATION_JSON)
                .param("since", "2000-12-12")
                .param("until", "2020-12-12"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(Arrays.asList(property1, property2))));
    }

    @Test
    public void getAllByDateAndSort() throws Exception {
        mockMvc.perform(get(PROPERTY_PATH)
                .accept(APPLICATION_JSON)
                .param("since", "2000-12-12")
                .param("until", "2020-12-12")
                .param("sortByPrice", "desc"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(Arrays.asList(property1, property2))));
    }

    @Test
    public void getPropertiesByPriceSortedBy() throws Exception {
        mockMvc.perform(get(PROPERTY_PATH)
                .accept(APPLICATION_JSON)
                .param("sortByPrice", "asc"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(Arrays.asList(property1, property2))));
    }
}
