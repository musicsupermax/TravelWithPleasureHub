package com.kh021j.travelwithpleasurehub.tickets.parser.belavia.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class SearchRoutes {

    @JsonProperty
    private String origin;
    @JsonProperty
    private String destination;
    @JsonProperty
    private String departing;
    @JsonProperty
    private int direction;

}
