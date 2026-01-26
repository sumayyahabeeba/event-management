package com.example.project.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.example.project.model.Feedback;
import com.example.project.model.Registration;
import com.example.project.model.RegistrationStatus;
import com.example.project.repository.FeedbackJpaRepository;
import com.example.project.repository.RegistrationJpaRepository;

@Service
public class FeedbackService {

    private final FeedbackJpaRepository feedbackRepository;
    private final RegistrationJpaRepository registrationJpaRepository;

    public FeedbackService(FeedbackJpaRepository feedbackRepository, RegistrationJpaRepository registrationJpaRepository) {
        this.feedbackRepository = feedbackRepository;
        this.registrationJpaRepository = registrationJpaRepository;
    }

    public Feedback addFeedback(Feedback feedback) {
        // Validate that the employee is registered for the event
        Registration registration = registrationJpaRepository
                .findByEmployeeIdAndEventId(feedback.getEmployeeId(), feedback.getEventId())
                .orElseThrow(() -> new RuntimeException("Employee is not registered for this event"));
        if(registration.getStatus() != RegistrationStatus.ATTENDED) {
            throw new RuntimeException("Feedback allowed only after attending the event");
        }
        feedbackRepository.findByEmployeeIdAndEventId(feedback.getEmployeeId(), feedback.getEventId()).ifPresent(f -> {throw new RuntimeException("Feedback already submitted for this event");});
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
