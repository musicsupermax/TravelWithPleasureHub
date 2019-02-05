package com.kh021j.travelwithpleasurehub.controller;

import com.kh021j.travelwithpleasurehub.service.MeetingFeedbackService;
import com.kh021j.travelwithpleasurehub.service.dto.MeetingFeedbackDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/api/meeting-feedbacks")
public class MeetingFeedbackController {

    private final Logger log = LoggerFactory.getLogger(MeetingController.class);

    private final MeetingFeedbackService meetingFeedbackService;

    @PostMapping
    public ResponseEntity<MeetingFeedbackDTO> createMeetingFeedback(@RequestBody MeetingFeedbackDTO meetingFeedbackDTO) throws URISyntaxException {
        log.debug("REST request to save MeetingFeedback : {}", meetingFeedbackDTO);
        MeetingFeedbackDTO result = meetingFeedbackService.save(meetingFeedbackDTO);
        if (result != null) {
            return ResponseEntity.created(new URI("/api/meeting-feedbacks/" + result.getId())).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping
    public ResponseEntity<MeetingFeedbackDTO> updateMeetingFeedback(@RequestBody MeetingFeedbackDTO meetingFeedbackDTO) {
        log.debug("REST request to update MeetingFeedback : {}", meetingFeedbackDTO);
        MeetingFeedbackDTO result = meetingFeedbackService.update(meetingFeedbackDTO);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMeetingFeedback(@PathVariable Integer id) {
        log.debug("REST request to remove MeetingFeedback with id : {}", id);
        if (meetingFeedbackService.deleteById(id)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetingFeedbackDTO> findMeetingFeedbackById(@PathVariable Integer id) {
        log.debug("REST request to get Meeting by id : {}", id);
        Optional<MeetingFeedbackDTO> meetingFeedbackDTO = meetingFeedbackService.findById(id);
        return meetingFeedbackDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<MeetingFeedbackDTO> findAllMeetingFeedbacks() {
        log.debug("REST request to get MeetingFeedbacks");
        return meetingFeedbackService.findAll();
    }

    @GetMapping(params = "type")
    public List<MeetingFeedbackDTO> findAllMeetingFeedbacksByFeedbackType(@RequestParam String type) {
        log.debug("REST request to get MeetingFeedbacks by type  : {}", type);
        return meetingFeedbackService.findAllByFeedbackType(type);
    }

    @GetMapping("/meeting/{id}")
    public List<MeetingFeedbackDTO> findAllMeetingFeedbacksByMeetingId(@PathVariable Integer id) {
        log.debug("REST request to get MeetingFeedbacks by meeting id  : {}", id);
        return meetingFeedbackService.findAllByMeeting(id);
    }


}
