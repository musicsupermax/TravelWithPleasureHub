package com.kh021j.travelwithpleasurehub.model;

import com.kh021j.travelwithpleasurehub.model.enumiration.MeetingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
@Entity
@Table(name = "meeting")
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "header", nullable = false)
    private String header;

    @Column(name = "meeting_type", nullable = false)
    private MeetingType meetingType;

    @Column(name = "content")
    private String content;

    @Column(name = "location", nullable = false)
    private String location;

    @ElementCollection
    @CollectionTable(name = "link", joinColumns = @JoinColumn(name = "meeting_id"))
    private List<String> links;

    @Column(name = "date_time", nullable = false)
    private ZonedDateTime timeOfAction;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToMany
    @JoinTable(name = "confirmed_users",
            joinColumns = @JoinColumn(name = "meeting_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> confirmedUsers;

    @ManyToMany
    @JoinTable(name = "wishing_users",
            joinColumns = @JoinColumn(name = "meeting_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> wishingUsers;
}


