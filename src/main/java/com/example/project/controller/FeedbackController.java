package com.example.project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.model.Feedback;
import com.example.project.service.FeedbackService;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    // POST /api/feedbacks?employeeId=1&eventId=2
    @PostMapping
    public ResponseEntity<Feedback> addFeedback(@RequestParam Long employeeId,
                                                @RequestParam Long eventId,
                                                @RequestBody Feedback feedback) {
        return ResponseEntity.ok(
                feedbackService.addFeedback(employeeId, eventId, feedback));
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        return ResponseEntity.ok(feedbackService.getAllFeedbacks());
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Feedback>> getByEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(feedbackService.getFeedbacksByEvent(eventId));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Feedback>> getByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(feedbackService.getFeedbacksByEmployee(employeeId));
    }
}
