package com.kh021j.travelwithpleasurehub.tickets.apiparser.model.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestModel {
    private String country;
    private String currency;
    private String locale;
    private String originPlace;
    private String destinationPlace;
    private String outboundDate;
    private int adults;
    private String cabinClass;
    private int children;
    private int infants;
}

