package com.example.project.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.example.project.entity.Feedback;
import com.example.project.repository.FeedbackRepository;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public Feedback addFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public List<Feedback> getFeedbacksByEvent(Long eventId) {
        return feedbackRepository.findByEventId(eventId);
    }

    public List<Feedback> getFeedbacksByEmployee(Long employeeId) {
        return feedbackRepository.findByEmployeeId(employeeId);
    }
}

