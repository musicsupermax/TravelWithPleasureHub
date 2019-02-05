package com.kh021j.travelwithpleasurehub.tickets.apiparser.repository;

import com.kh021j.travelwithpleasurehub.tickets.apiparser.model.response.FlightData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlightDataRepository extends JpaRepository<FlightData, Long> {
    List<FlightData>
    findAllByArrivalAirportAndDepartureAirportAndQueryDateAndAdultsAndChildrenAndInfantsAndCabinType(
            String arrivalAirport, String departureAirport, LocalDate queryDate,
            int adults, int children, int infants, String cabinType);
}
