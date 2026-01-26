package com.example.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.model.Feedback;

public interface FeedbackJpaRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findByEventId(Long eventId);

    List<Feedback> findByEmployeeId(Long employeeId);

    Optional<Feedback> findByEmployeeIdAndEventId(Long employeeId, Long eventId);
}
