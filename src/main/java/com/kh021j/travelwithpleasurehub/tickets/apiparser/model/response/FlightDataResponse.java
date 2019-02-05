package com.kh021j.travelwithpleasurehub.tickets.apiparser.model.response;

import com.kh021j.travelwithpleasurehub.tickets.parser.belavia.model.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightDataResponse implements Comparable<FlightDataResponse> {

    private String departureAirport;
    private String arrivalAirport;
    private LocalDate queryDate;
    private String cabinType;
    private String company;
    private String imageCompany;
    private Currency currency;
    private Integer adults;
    private Integer children;
    private Integer infants;
    private String duration;
    private String departureDate;
    private String departureTime;
    private String arrivalDate;
    private String arrivalTime;
    private String linkForBuying;
    private Double price;

    @Override
    public int compareTo(FlightDataResponse o) {
        if((this.price - o.getPrice()) > 0){
            return -1;
        } else if ((this.price - o.getPrice()) < 0){
            return 1;
        } else {
            return 0;
        }
    }
}
