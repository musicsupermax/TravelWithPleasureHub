package com.kh021j.travelwithpleasurehub.tickets.parser.belavia.сontroller;

import com.kh021j.travelwithpleasurehub.tickets.parser.belavia.model.BelaviaJson;
import com.kh021j.travelwithpleasurehub.tickets.parser.belavia.model.FlightInfo;
import com.kh021j.travelwithpleasurehub.tickets.parser.belavia.service.FlightInfoService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/flights")
public class FlightController {

    private FlightInfoService flightInfoService;

    @ResponseBody
    @PostMapping
    public FlightInfo getFlightInfo(@RequestBody BelaviaJson belaviaJson) throws IOException {
        return flightInfoService.getFlightInfo(belaviaJson);
    }
}
