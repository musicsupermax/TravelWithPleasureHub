package com.kh021j.travelwithpleasurehub.controller;

import com.kh021j.travelwithpleasurehub.service.MeetingService;
import com.kh021j.travelwithpleasurehub.service.dto.MeetingDTO;
import com.kh021j.travelwithpleasurehub.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@CrossOrigin
@RequestMapping("/api/meetings")
public class MeetingController {

    private final Logger log = LoggerFactory.getLogger(MeetingController.class);

    private final MeetingService meetingService;

    @PostMapping
    public ResponseEntity<MeetingDTO> createMeeting(@ModelAttribute MeetingDTO meetingDTO) throws URISyntaxException, IOException {
        log.debug("REST request to save Meeting : {}", meetingDTO);
        MeetingDTO result = meetingService.save(meetingDTO);
        if (result != null) {
            return ResponseEntity.created(new URI("/api/meetings/" + result.getId())).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/request-for-meeting/", params = {"meetingId", "userId"})
    public ResponseEntity<MeetingDTO> sendRequestForMeeting(@RequestParam String meetingId, @RequestParam String userId) {
        log.debug("REST request to send request for Meeting with id : {} ,and wishing user id : {} ", meetingId, userId);
        MeetingDTO result = meetingService.sendRequestForMeeting(Integer.parseInt(meetingId), Integer.parseInt(userId));
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/confirm-meeting/", params = {"meetingId", "wishingUserId"})
    public ResponseEntity<MeetingDTO> confirmUserForMeeting
            (@RequestParam(value = "meetingId") String meetingId, @RequestParam(value = "wishingUserId") String wishingUserId) {
        log.debug("REST request to send request for Meeting with id : {} ,and wishing user id : {} ",
                meetingId, wishingUserId);
        MeetingDTO result = meetingService.confirmUserForMeeting(Integer.parseInt(meetingId), Integer.parseInt(wishingUserId));
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/reject/", params = {"meetingId", "wishingUserId"})
    public ResponseEntity<MeetingDTO> rejectUserForMeeting
            (@RequestParam(value = "meetingId") String meetingId, @RequestParam(value = "wishingUserId") String wishingUserId) {
        log.debug("REST request to reject request for Meeting with id : {} ,and wishing user id : {} ",
                meetingId, wishingUserId);
        MeetingDTO result = meetingService.rejectRequestForMeeting(Integer.parseInt(meetingId), Integer.parseInt(wishingUserId));
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().build();
    }


    @PutMapping
    public ResponseEntity<MeetingDTO> updateMeeting(@ModelAttribute MeetingDTO meetingDTO) throws IOException {
        log.debug("REST request to update Meeting : {}", meetingDTO);
        MeetingDTO result = meetingService.update(meetingDTO);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMeeting(@PathVariable Integer id) {
        log.debug("REST request to remove Meeting with id : {}", id);
        if (meetingService.deleteById(id)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetingDTO> findMeetingById(@PathVariable Integer id) {
        log.debug("REST request to get Meeting by id : {}", id);
        Optional<MeetingDTO> meetingDTO = meetingService.findById(id);
        return meetingDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<MeetingDTO> findAllMeetings() {
        log.debug("REST request to get Meetings : {}");
        return meetingService.findAll();
    }

    @GetMapping(params = "owner")
    public List<MeetingDTO> findMeetingByOwner(@RequestParam String owner) {
        log.debug("REST request to get Meetings with owner : {}", owner);
        return meetingService.findAllByOwnerId(Integer.parseInt(owner));
    }

    @GetMapping(params = "confirmedHistoryByUser")
    public List<MeetingDTO> findConfirmedHistoryByUser(@RequestParam String confirmedHistoryByUser) {
        log.debug("REST request to get confirmed Meetings with user's id  : {} ", confirmedHistoryByUser);
        return meetingService.findConfirmedHistoryOfMeetingsByUserId(Integer.parseInt(confirmedHistoryByUser));
    }

    @GetMapping(params = "wishingHistoryByUser")
    public List<MeetingDTO> findWishingHistoryByUser(@RequestParam String wishingHistoryByUser) {
        log.debug("REST request to get wishing Meetings with user's id  : {} ", wishingHistoryByUser);
        return meetingService.findWishingHistoryOfMeetingsByUserId(Integer.parseInt(wishingHistoryByUser));
    }

    @GetMapping(params = {"headerFilter", "locationFilter", "timeFilter"})
    public List<MeetingDTO> findMeetingsByFilter(@RequestParam String headerFilter,
                                                 @RequestParam String locationFilter,
                                                 @RequestParam String timeFilter) {
        log.debug("REST request to get Meetings with header : {} ,location : {} ,time : {} ", headerFilter, locationFilter, timeFilter);
        return meetingService.findAllByFilter(headerFilter, locationFilter, timeFilter);
    }

    @GetMapping(value = "wishing-users/{id}")
    public List<UserDTO> findAllWishingUsersInMeeting(@PathVariable Integer id) {
        log.debug("REST request to get wishing users in Meetings id : {} ", id);
        return meetingService.findAllWishingUsersInMeeting(id);
    }

    @GetMapping(value = "confirmed-users/{id}")
    public List<UserDTO> findAllConfirmedUsersInMeeting(@PathVariable Integer id) {
        log.debug("REST request to get confirmed users in Meetings id : {} ", id);
        return meetingService.findAllConfirmedUsersInMeeting(id);
    }

}