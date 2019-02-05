package com.kh021j.travelwithpleasurehub.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class MeetingDTO {
    private Integer id;

    private String header;

    private String meetingType;

    private String content;

    private String location;

    private List<String> links;

    private String timeOfAction;

    private Integer ownerId;

    private List<Integer> confirmedUserIds;

    private List<Integer> wishingUserIds;
}
