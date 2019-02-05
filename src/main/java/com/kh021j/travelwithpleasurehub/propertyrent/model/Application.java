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
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate rentSince;

    private LocalDate rentUntil;

    private String applicationText;

    private Boolean isApproved;
}
