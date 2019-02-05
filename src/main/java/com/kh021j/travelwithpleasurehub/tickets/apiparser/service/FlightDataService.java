package com.kh021j.travelwithpleasurehub.tickets.apiparser.service;

import com.kh021j.travelwithpleasurehub.tickets.apiparser.model.request.RequestModel;
import com.kh021j.travelwithpleasurehub.tickets.apiparser.model.response.Flight;
import com.kh021j.travelwithpleasurehub.tickets.apiparser.model.response.FlightData;
import com.kh021j.travelwithpleasurehub.tickets.apiparser.model.response.FlightDataResponse;
import com.kh021j.travelwithpleasurehub.tickets.apiparser.model.response.Ticket;
import com.kh021j.travelwithpleasurehub.tickets.apiparser.repository.FlightDataRepository;
import com.kh021j.travelwithpleasurehub.tickets.parser.belavia.model.enums.Currency;
import com.kh021j.travelwithpleasurehub.tickets.parser.belavia.service.FlightInfoService;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FlightDataService {

    private final FlightDataRepository flightDataRepository;

    private final Logger logger = LoggerFactory.getLogger(FlightInfoService.class);

    public List<FlightData> getResponse(RequestModel requestModel) throws JSONException, UnirestException {
        List<FlightData> result = flightDataRepository
                .findAllByArrivalAirportAndDepartureAirportAndQueryDateAndAdultsAndChildrenAndInfantsAndCabinType(
                        requestModel.getDestinationPlace(),
                        requestModel.getOriginPlace(),
                        LocalDate.parse(requestModel.getOutboundDate()),
                        requestModel.getAdults(),
                        requestModel.getChildren(),
                        requestModel.getInfants(),
                        requestModel.getCabinClass());
        if (result != null && !result.isEmpty()) {
            logger.info("To Database");
            return result;
        } else {
            logger.info("To Sky-Scanner");
            return getFlightsData(requestModel);
        }
    }

    private List<FlightData> getMonthTopRoutes() throws UnirestException, JSONException {
        LocalDate localDate = LocalDate.now();
        List<RequestModel> list = getTopRoutes();
        for (RequestModel entity : list) {
            for (int i = 1; i < 31; i++) {
                entity.setOutboundDate(localDate.plusDays(i).toString());
                for (FlightData flightData : getFlightsData(entity)) {
                    flightDataRepository.saveAndFlush(flightData);
                }
            }
        }
        return flightDataRepository.findAll();
    }

    private List<FlightData> getFlightsData(RequestModel entity) throws JSONException, UnirestException {
        OneWayOptionRequestService oneWayOptionRequestService = new OneWayOptionRequestService();
        List<FlightData> flightDataList = new ArrayList<>();
        if (entity != null) {
            JsonNode obj = oneWayOptionRequestService.sendResponseToController(entity);
            int count = 0;
            while ((obj.getObject().getJSONArray("Carriers").length() < 1 || obj.getObject() == null) && count < 5) {
                obj = oneWayOptionRequestService.sendResponseToController(entity);
                count++;
            }
            if (obj.getObject().getJSONArray("Carriers").length() > 0 && obj.getObject() != null) {
                JSONArray carrier = obj.getObject().getJSONArray("Carriers");
                for (int j = 0; j < carrier.length(); j++) {
                    FlightData flightData = FlightData.builder()
                            .companyId(carrier.getJSONObject(j).getInt("Id"))
                            .departureAirport(entity.getOriginPlace())
                            .arrivalAirport(entity.getDestinationPlace())
                            .queryDate(LocalDate.parse(entity.getOutboundDate()))
                            .cabinType(entity.getCabinClass())
                            .currency(Currency.valueOf(obj.getObject().getJSONObject("Query").getString("Currency").toUpperCase()))
                            .company(carrier.getJSONObject(j).getString("Name"))
                            .companyCode(carrier.getJSONObject(j).getString("DisplayCode"))
                            .imageCompany(carrier.getJSONObject(j).getString("ImageUrl"))
                            .adults(entity.getAdults())
                            .children(entity.getChildren())
                            .infants(entity.getInfants())
                            .flights(getFlightsForFlightData(obj, carrier.getJSONObject(j).getString("Id")))
                            .build();
                    if (!flightData.getFlights().isEmpty()) {
                        flightDataList.add(flightData);
                    }
                }
            }
        }
        return flightDataList;
    }

    @Scheduled(cron = "0 0/30 10 * * ?")
    public void reportCurrentTime() throws UnirestException, JSONException {
        getMonthTopRoutes();
    }

    @Scheduled(cron = "0 1 1 * * ?")
    public void deleteOldData() {
        for (FlightData flightData : flightDataRepository.findAll()) {
            if (flightData.getQueryDate().isAfter(LocalDate.now()) ||
                    flightData.getQueryDate().equals(LocalDate.now())) {
                flightDataRepository.deleteById(flightData.getId());
            }
        }
    }

    private List<Flight> getFlightsForFlightData(JsonNode obj, String carrierId) throws JSONException {
        JSONArray legs = obj.getObject().getJSONArray("Legs");
        List<Flight> flights = new ArrayList<>();
        for (int i = 0; i < legs.length(); i++) {
            JSONArray carrierIds = legs.getJSONObject(i).getJSONArray("Carriers");
            for (int j = 0; j < carrierIds.length(); j++) {
                if (carrierIds.get(j).toString().equals(carrierId)) {
                    Flight flight = Flight.builder()
                            .arrivalTime(ZonedDateTime.of(LocalDateTime.parse(legs.getJSONObject(i)
                                    .getString("Arrival")), ZoneId.systemDefault()))
                            .departureTime(ZonedDateTime.of(LocalDateTime.parse(legs.getJSONObject(i)
                                    .getString("Departure")), ZoneId.systemDefault()))
                                    .duration(minutesToHours(legs.getJSONObject(i).getInt("Duration")))
                                    .tickets(getTicketsForFlight(obj, legs.getJSONObject(i).getString("Id")))
                                    .build();
                    if (!flight.getTickets().isEmpty()) {
                        flights.add(flight);
                    }
                }
            }
        }
        return flights;
    }

    private List<Ticket> getTicketsForFlight(JsonNode obj, String legId) throws JSONException {
        JSONArray itineraries = obj.getObject().getJSONArray("Itineraries");
        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < itineraries.length(); i++) {
            if (legId.equals(itineraries.getJSONObject(i).getString("OutboundLegId"))) {
                JSONArray pricingOptions = itineraries.getJSONObject(i).getJSONArray("PricingOptions");
                for (int j = 0; j < pricingOptions.length(); j++) {
                    Ticket ticket = Ticket.builder()
                            .linkForBuying(pricingOptions.getJSONObject(j).getString("DeeplinkUrl"))
                            .price(pricingOptions.getJSONObject(j).getDouble("Price"))
                            .build();
                    tickets.add(ticket);
                }
            }
        }
        return tickets;
    }

    private static List<RequestModel> getTopRoutes() {
        List<RequestModel> list = new ArrayList<>();
        LocalDate localDate = LocalDate.now();

        list.add(new RequestModel("US", "USD", "en-US",
                "ICN", "CJU", localDate.toString(),
                1, "economy", 0, 0));
        list.add(new RequestModel("US", "USD", "en-US",
                "MEL", "SYD", localDate.toString(),
                1, "economy", 0, 0));
        list.add(new RequestModel("US", "USD", "en-US",
                "BOM", "DEL", localDate.toString(),
                1, "economy", 0, 0));
        list.add(new RequestModel("US", "USD", "en-US",
                "DUB", "LCY", localDate.toString(),
                1, "economy", 0, 0));
        list.add(new RequestModel("US", "USD", "en-US",
                "GIG", "GRU", localDate.toString(),
                1, "economy", 0, 0));
        list.add(new RequestModel("US", "USD", "en-US",
                "LAX", "SFO", localDate.toString(),
                1, "economy", 0, 0));
        list.add(new RequestModel("US", "USD", "en-US",
                "SIN", "KUL", localDate.toString(),
                1, "economy", 0, 0));
        list.add(new RequestModel("US", "USD", "en-US",
                "LGA", "YYZ", localDate.toString(),
                1, "economy", 0, 0));
        list.add(new RequestModel("US", "USD", "en-US",
                "AMS", "LHR", localDate.toString(),
                1, "economy", 0, 0));
        list.add(new RequestModel("US", "USD", "en-US",
                "DXB", "KWI", localDate.toString(),
                1, "economy", 0, 0));
        list.add(new RequestModel("US", "USD", "en-US",
                "ORD", "YYZ", localDate.toString(),
                1, "economy", 0, 0));
        list.add(new RequestModel("US", "USD", "en-US",
                "JFK", "LHR", localDate.toString(),
                1, "economy", 0, 0));
        list.add(new RequestModel("US", "USD", "en-US",
                "HKG", "PEK", localDate.toString(),
                1, "economy", 0, 0));
        list.add(new RequestModel("US", "USD", "en-US",
                "BKK", "HKG", localDate.toString(),
                1, "economy", 0, 0));
        list.add(new RequestModel("US", "USD", "en-US",
                "CGK", "SIN", localDate.toString(),
                1, "economy", 0, 0));
        return list;
    }

    private String minutesToHours(int minutes) {
        int hours = minutes / 60;
        return String.format(("%d:%02d"), hours, minutes % 60);
    }

    public Set<FlightDataResponse> getFlightDataResponse(List<FlightData> listFlightData) throws ParseException {
        Set<FlightDataResponse> set = new TreeSet<>();
        if(listFlightData != null || !listFlightData.isEmpty()) {

            for (FlightData flightData : listFlightData) {

               FlightDataResponse flightDataResponse = new FlightDataResponse();

               flightDataResponse.setDepartureAirport(flightData.getDepartureAirport());
               flightDataResponse.setArrivalAirport(flightData.getArrivalAirport());
               flightDataResponse.setQueryDate(flightData.getQueryDate());
               flightDataResponse.setCabinType(flightData.getCabinType());
               flightDataResponse.setCompany(flightData.getCompany());
               flightDataResponse.setImageCompany(flightData.getImageCompany());
               flightDataResponse.setCurrency(flightData.getCurrency());
               flightDataResponse.setAdults(flightData.getAdults());
               flightDataResponse.setChildren(flightData.getChildren());
               flightDataResponse.setInfants(flightData.getInfants());

                for (Flight flight : flightData.getFlights()) {
                    flightDataResponse.setDuration(flight.getDuration());
                    flightDataResponse.setDepartureDate(flight.getDepartureTime().toLocalDate().toString());
                    flightDataResponse.setDepartureTime(flight.getDepartureTime().toLocalTime().toString());
                    flightDataResponse.setArrivalTime(flight.getArrivalTime().toLocalTime().toString());
                    flightDataResponse.setArrivalDate(flight.getArrivalTime().toLocalDate().toString());

                    for (Ticket ticket : flight.getTickets()) {
                        flightDataResponse.setLinkForBuying(ticket.getLinkForBuying());
                        flightDataResponse.setPrice(ticket.getPrice());
                        logger.info("FlightDataResponse " + flightDataResponse);
                        set.add(flightDataResponse);
                    }
                }
            }
        }
        logger.info(" " + set);
        return set;
    }

    private String getDateFromUtc(String dateUtc){
        String[] datetime = dateUtc.split("T");
        return datetime[0];
    }

    private String getTimeFromeUtc(String dateUtc){
        String[] datetime = dateUtc.split("T");
        return datetime[1];
    }
}
