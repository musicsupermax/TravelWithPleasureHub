
package com.kh021j.travelwithpleasurehub.tickets.parser.belavia.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kh021j.travelwithpleasurehub.tickets.parser.belavia.model.enums.PassengerCode;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerQuantities {

    @JsonProperty
    private PassengerCode code;
    @JsonProperty
    private int quantity;

}
