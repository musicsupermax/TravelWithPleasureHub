package com.kh021j.travelwithpleasurehub.propertyrent.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PropertyImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String imageLink;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    public PropertyImage(String imageLink, Property property) {
        this.imageLink = imageLink;
        this.property = property;
    }
}
