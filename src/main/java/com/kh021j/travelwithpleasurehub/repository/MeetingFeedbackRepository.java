package com.kh021j.travelwithpleasurehub.repository;

import com.kh021j.travelwithpleasurehub.model.Meeting;
import com.kh021j.travelwithpleasurehub.model.MeetingFeedback;
import com.kh021j.travelwithpleasurehub.model.User;
import com.kh021j.travelwithpleasurehub.model.enumiration.FeedbackType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingFeedbackRepository extends JpaRepository<MeetingFeedback, Integer> {
    List<MeetingFeedback> getAllByFeedbackType(FeedbackType feedbackType);

    List<MeetingFeedback> getAllByMeeting(Meeting meeting);

    List<MeetingFeedback> getAllByOwner(User user);
}
