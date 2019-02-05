package com.kh021j.travelwithpleasurehub.model;

import com.kh021j.travelwithpleasurehub.model.enumiration.FeedbackType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "meeting_feedback")
public class MeetingFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "text")
    private String text;

    @Column(name = "feedback_type")
    private FeedbackType feedbackType;

    @OneToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}
