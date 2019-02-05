
package com.kh021j.travelwithpleasurehub.tickets.parser.belavia.model;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class FlightInfo {

    private String price;
    private String currency;
    private String departureDateTime;
    private String arrivalDateTime;
    private String duration;
    private String origin;
    private String destination;

}
