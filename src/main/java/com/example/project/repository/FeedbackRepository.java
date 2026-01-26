package com.example.project.repository;

import java.util.List;

import com.example.project.model.Feedback;

public interface FeedbackRepository {

    Feedback save(Feedback feedback);

    List<Feedback> findAll();

    List<Feedback> findByEventId(Long eventId);

    List<Feedback> findByEmployeeId(Long employeeId);
}
