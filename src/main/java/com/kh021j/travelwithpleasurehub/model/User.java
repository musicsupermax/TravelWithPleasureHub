package com.kh021j.travelwithpleasurehub.model;


import com.kh021j.travelwithpleasurehub.model.enumiration.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String email;

    private String firstName;

    private String secondName;

    private String location;

    private String additionalInfo;

    private String phoneNumber;

    private String pathToPhoto;

    @Nullable
    private boolean status;

    @Enumerated(EnumType.STRING)
    private UserRole role;

}
