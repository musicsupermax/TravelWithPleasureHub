package com.kh021j.travelwithpleasurehub.tickets.apiparser.model.response;

import com.kh021j.travelwithpleasurehub.tickets.parser.belavia.model.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "flight_data")
public class FlightData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "departure_airport")
    private String departureAirport;

    @Column(name = "arrival_airport")
    private String arrivalAirport;

    @Column(name = "query_date")
    private LocalDate queryDate;

    @Column(name = "cabin_type")
    private String cabinType;

    @Column(name = "company")
    private String company;

    @Column(name = "company_code")
    private String companyCode;

    @Column(name = "image_company")
    private String imageCompany;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;

    @Column(name = "adults")
    private Integer adults;

    @Column(name = "children")
    private Integer children;

    @Column(name = "infants")
    private Integer infants;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "flight_data_flight",
            joinColumns = @JoinColumn(name = "flight_data_id"),
            inverseJoinColumns = @JoinColumn(name = "flight_id"))
    private List<Flight> flights;
}
