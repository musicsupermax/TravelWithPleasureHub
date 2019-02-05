package com.kh021j.travelwithpleasurehub.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MeetingFeedbackDTO {

    private Integer id;

    private String text;

    private String feedbackType;

    private Integer meetingId;

    private Integer ownerId;

}
