package com.kh021j.travelwithpleasurehub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh021j.travelwithpleasurehub.propertyrent.controller.UserReviewController;
import com.kh021j.travelwithpleasurehub.model.User;
import com.kh021j.travelwithpleasurehub.propertyrent.model.UserReview;
import com.kh021j.travelwithpleasurehub.propertyrent.repository.UserReviewRepository;
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
@WebMvcTest(UserReviewController.class)
public class UserReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserReviewRepository userReviewRepository;

    private final String USER_REVIEW_PATH = "/api/userReview";

    private User user = User.builder()
            .id(12)
            .firstName("fName")
            .secondName("sName")
            .password("easypassword")
            .additionalInfo("add info")
            .email("smart@gmail.com")
            .username("us name")
            .build();

    private UserReview userReview = UserReview.builder()
            .id(100)
            .rate(12)
            .reviewText("review text")
            .dateRated(LocalDate.now())
            .user(user)
            .madeByUserId(user.getId())
            .build();


    @Before
    public void setup() throws Exception {
        when(userReviewRepository.findAll())
                .thenReturn(new ArrayList<>(Collections.singletonList(userReview)));
        when(userReviewRepository.save(userReview)).thenReturn(userReview);
        when(userReviewRepository.findById(userReview.getId())).thenReturn(Optional.of(userReview));
        when(userReviewRepository.findAllByOrderByDateRatedAsc()).thenReturn(Optional.of(Collections.singletonList(userReview)));
        when(userReviewRepository.findByUserId(user.getId())).thenReturn(Optional.of(Collections.singletonList(userReview)));
    }

    @Test
    public void add() throws Exception {
        String contactsRequestBody = objectMapper.writeValueAsString(userReview);
        mockMvc.perform(post(USER_REVIEW_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(contactsRequestBody))
                .andExpect(content().string(contactsRequestBody));
    }

    @Test
    public void update() throws Exception {
        String contactsRequestBody = objectMapper.writeValueAsString(userReview);

        mockMvc.perform(put(USER_REVIEW_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(contactsRequestBody))
                .andExpect(content().string(contactsRequestBody));
    }

    @Test
    public void delete() throws Exception {
        String contactsRequestBody = objectMapper.writeValueAsString(userReview);

        mockMvc.perform(MockMvcRequestBuilders.delete(USER_REVIEW_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(contactsRequestBody));

    }

    @Test
    public void getById() throws Exception {
        String contactsRequestBody = objectMapper.writeValueAsString(userReview);
        mockMvc.perform(get(USER_REVIEW_PATH + "/" + userReview.getId()))
                .andExpect(content().string(contactsRequestBody));
    }

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(get(USER_REVIEW_PATH)
                .accept(APPLICATION_JSON))
                .andExpect(content().string(objectMapper
                        .writeValueAsString(Collections.singletonList(userReview))));
    }

    @Test
    public void getAllByUserId() throws Exception {
        mockMvc.perform(get(USER_REVIEW_PATH)
                .accept(APPLICATION_JSON)
                .param("propertyId", String.valueOf(user.getId())))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(Collections.singletonList(userReview))));
    }

    @Test
    public void getAllByDateAndSort() throws Exception {
        mockMvc.perform(get(USER_REVIEW_PATH)
                .accept(APPLICATION_JSON)
                .param("sortByDateRated", "asc"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(Collections.singletonList(userReview))));
    }


}
