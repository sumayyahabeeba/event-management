package com.example.project.repository;

import com.example.project.model.Registration;
import com.example.project.model.RegistrationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegistrationJpaRepository extends JpaRepository<Registration, Long> {

    Optional<Registration> findByEmployeeIdAndEventId(Long employeeId, Long eventId);

    List<Registration> findByEventId(Long eventId);

    List<Registration> findByEmployeeId(Long employeeId);

    long countByEventIdAndStatus(Long eventId, RegistrationStatus status);
}
