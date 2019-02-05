
package com.kh021j.travelwithpleasurehub.tickets.parser.belavia.service;

import com.kh021j.travelwithpleasurehub.tickets.parser.belavia.model.BelaviaJson;
import com.kh021j.travelwithpleasurehub.tickets.parser.belavia.model.FlightInfo;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FlightInfoService {

    public FlightInfo getFlightInfo(BelaviaJson belaviaJson) throws IOException {
        String jsonQuery = new JsonConverter().objectToJson(belaviaJson);
        JsonService jsonService = new JsonService();
        String jsonBelavia = jsonService.getJsonResponse(jsonQuery);
        return new FlightInfo(jsonService.getMinPriceFromResponse(jsonBelavia),
                belaviaJson.getCurrency(), jsonService.getDepartureDateTime(jsonBelavia),
                jsonService.getArrivalDateTime(jsonBelavia), jsonService.getDuration(jsonBelavia),
                belaviaJson.getSearchRoutes().get(0).getOrigin(),
                belaviaJson.getSearchRoutes().get(0).getDestination());
    }
}
