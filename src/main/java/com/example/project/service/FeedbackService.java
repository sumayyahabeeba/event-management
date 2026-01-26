package com.example.project.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.project.model.Employee;
import com.example.project.model.Event;
import com.example.project.model.Feedback;
import com.example.project.model.Registration;
import com.example.project.model.RegistrationStatus;
import com.example.project.repository.EmployeeJpaRepository;
import com.example.project.repository.EventRepository;
import com.example.project.repository.FeedbackJpaRepository;
import com.example.project.repository.RegistrationJpaRepository;

@Service
public class FeedbackService {

    private final FeedbackJpaRepository feedbackRepository;
    private final RegistrationJpaRepository registrationRepository;
    private final EmployeeJpaRepository employeeRepository;
    private final EventRepository eventRepository;

    public FeedbackService(FeedbackJpaRepository feedbackRepository,
                           RegistrationJpaRepository registrationRepository,
                           EmployeeJpaRepository employeeRepository,
                           EventRepository eventRepository) {
        this.feedbackRepository = feedbackRepository;
        this.registrationRepository = registrationRepository;
        this.employeeRepository = employeeRepository;
        this.eventRepository = eventRepository;
    }

    public Feedback addFeedback(Long employeeId, Long eventId, Feedback feedbackRequest) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Employee not found"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Event not found"));

        // Check registration
        Registration registration = registrationRepository
                .findByEmployeeIdAndEventId(employeeId, eventId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Employee is not registered for this event"));

        // Check attendance
        if (registration.getStatus() != RegistrationStatus.ATTENDED) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Only attendees can submit feedback");
        }

        // Duplicate feedback check
        feedbackRepository.findByEmployeeIdAndEventId(employeeId, eventId)
                .ifPresent(f -> {
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT,
                            "Feedback already submitted for this event");
                });

        Feedback feedback = new Feedback();
        feedback.setEmployee(employee);
        feedback.setEvent(event);
        feedback.setRating(feedbackRequest.getRating());
        feedback.setComment(feedbackRequest.getComment());

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
