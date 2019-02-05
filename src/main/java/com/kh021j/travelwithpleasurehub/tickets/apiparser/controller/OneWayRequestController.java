package com.kh021j.travelwithpleasurehub.tickets.apiparser.controller;

import com.kh021j.travelwithpleasurehub.tickets.apiparser.model.request.RequestModel;
import com.kh021j.travelwithpleasurehub.tickets.apiparser.model.response.FlightData;
import com.kh021j.travelwithpleasurehub.tickets.apiparser.model.response.FlightDataResponse;
import com.kh021j.travelwithpleasurehub.tickets.apiparser.service.FlightDataService;
import com.kh021j.travelwithpleasurehub.tickets.apiparser.service.OneWayOptionRequestService;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping(path = "/api/flights/one-way")
public class OneWayRequestController {

    @Autowired
    OneWayOptionRequestService requestService;

    @Autowired
    private FlightDataService flightDataService;

    private final Logger logger = LoggerFactory.getLogger(OneWayRequestController.class);

    @PostMapping(produces = "application/json")
    @ResponseBody
    public ResponseEntity<Set<FlightDataResponse>> createRequest(@RequestBody RequestModel requestModel) throws UnirestException, JSONException, ParseException {
        logger.info(requestModel.getCabinClass());
        return ResponseEntity.ok(flightDataService.getFlightDataResponse(flightDataService.getResponse(requestModel)));
    }
}
