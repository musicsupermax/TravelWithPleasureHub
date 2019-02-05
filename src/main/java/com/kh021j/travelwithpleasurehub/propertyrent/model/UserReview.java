package com.kh021j.travelwithpleasurehub.propertyrent.model;

import com.kh021j.travelwithpleasurehub.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String reviewText;

    private Integer rate;

    private Integer madeByUserId;

    private LocalDate dateRated;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
