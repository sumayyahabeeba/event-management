package com.example.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.entity.Feedback;

public interface FeedbackJpaRepository
        extends JpaRepository<Feedback, Long>, FeedbackRepository {

    List<Feedback> findByEventId(Long eventId);

    List<Feedback> findByEmployeeId(Long employeeId);
}

