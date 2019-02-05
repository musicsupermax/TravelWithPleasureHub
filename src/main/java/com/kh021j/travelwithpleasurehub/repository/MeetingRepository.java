package com.kh021j.travelwithpleasurehub.repository;

import com.kh021j.travelwithpleasurehub.model.Meeting;
import com.kh021j.travelwithpleasurehub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer> {

    Meeting findById(long id);

    List<Meeting> findAllByConfirmedUsersContaining(User user);

    List<Meeting> findAllByWishingUsersContaining(User user);

    List<Meeting> findAllByOwner(User user);

    @Query(value = "SELECT DISTINCT * FROM meeting " +
            "WHERE (LOWER(header) LIKE LOWER(CONCAT('%', CASE WHEN ((:headerFilter IS NULL) OR (:headerFilter = 'undefined')) THEN '' ELSE :headerFilter END, '%')) " +
            "AND LOWER(location) LIKE LOWER(CONCAT('%', CASE WHEN ((:locationFilter IS NULL) OR (:locationFilter = 'undefined')) THEN '' ELSE :locationFilter END, '%')) " +
            "AND CASE WHEN ((:timeFilter IS NULL) OR (:timeFilter = 'undefined')) THEN '' ELSE :timeFilter END > " +
            "CONCAT(extract (YEAR from date_time),'-', " +
            "(CASE WHEN (extract(MONTH from date_time) < 10) THEN CONCAT('0',extract(MONTH from date_time)) ELSE CONCAT(extract(MONTH from date_time)) END) ,'-', " +
            "(CASE WHEN (extract(DAY from date_time) < 10) THEN CONCAT('0',extract(DAY from date_time)) ELSE CONCAT(extract(DAY from date_time)) END) ,' ', " +
            "(CASE WHEN (extract(HOUR from date_time) < 10) THEN CONCAT('0',extract(HOUR from date_time)) ELSE CONCAT(extract(HOUR from date_time)) END) ,':', " +
            "(CASE WHEN (extract(MINUTE from date_time) < 10) THEN CONCAT('0',extract(MINUTE from date_time)) ELSE CONCAT(extract(MINUTE from date_time)) END)))",
            nativeQuery = true)
    List<Meeting> findAllByFilter(@Param("headerFilter") String headerFilter,
                                  @Param("locationFilter") String locationFilter,
                                  @Param("timeFilter") String timeFilter);
}
