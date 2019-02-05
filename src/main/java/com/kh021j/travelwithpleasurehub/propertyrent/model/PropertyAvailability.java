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
public class PropertyAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate bookedSince;

    private LocalDate bookedUntil;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userTable;

    public PropertyAvailability(LocalDate bookedSince, LocalDate bookedUntil, Property property, User userTable) {
        this.bookedSince = bookedSince;
        this.bookedUntil = bookedUntil;
        this.property = property;
        this.userTable = userTable;
    }
}
