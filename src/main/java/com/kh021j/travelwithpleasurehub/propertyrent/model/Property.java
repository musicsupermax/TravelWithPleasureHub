package com.kh021j.travelwithpleasurehub.propertyrent.model;


import com.kh021j.travelwithpleasurehub.model.User;
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
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "property_type_id")
    private PropertyType propertyType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    private String locality;

    private String address;

    private Integer price;

    private Double latitude;

    private Double longitude;

    public Property(String title, String description,
                        PropertyType propertyType, User userTable,
                        String locality, String address,
                        Integer price, Double latitude,
                        Double longitude
    ) {
        this.title = title;
        this.description = description;
        this.propertyType = propertyType;
        this.owner = userTable;
        this.locality = locality;
        this.address = address;
        this.price = price;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
