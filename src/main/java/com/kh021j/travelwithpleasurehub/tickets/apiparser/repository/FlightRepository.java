package com.kh021j.travelwithpleasurehub.tickets.apiparser.repository;

import com.kh021j.travelwithpleasurehub.tickets.apiparser.model.response.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
}
