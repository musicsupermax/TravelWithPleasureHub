package com.kh021j.travelwithpleasurehub.tickets.apiparser.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "flight")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "duration")
    private String duration;

    @Column(name = "departure_time")
    private ZonedDateTime departureTime;

    @Column(name = "arrival_time")
    private ZonedDateTime arrivalTime;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "flight_ticket",
            joinColumns = @JoinColumn(name = "flight_id"),
            inverseJoinColumns = @JoinColumn(name = "ticket_id"))
    private List<Ticket> tickets;

}
