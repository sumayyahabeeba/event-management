package com.example.project.service;

import com.example.project.model.*;
import com.example.project.repository.EmployeeJpaRepository;
import com.example.project.repository.EventRepository;
import com.example.project.repository.RegistrationJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService {

    private final RegistrationJpaRepository registrationJpaRepo;
    private final EmployeeJpaRepository employeeRepo;
    private final EventRepository eventRepo;

    public RegistrationService(RegistrationJpaRepository registrationJpaRepo,
                               EmployeeJpaRepository employeeRepo,
                               EventRepository eventRepo) {
        this.registrationJpaRepo = registrationJpaRepo;
        this.employeeRepo = employeeRepo;
        this.eventRepo = eventRepo;
    }

    public Registration register(Long employeeId, Long eventId) {

        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Duplicate check
        registrationJpaRepo.findByEmployeeIdAndEventId(employeeId, eventId)
                .ifPresent(r -> {
                    if (r.getStatus() != RegistrationStatus.CANCELLED) {
                        throw new RuntimeException("Employee already registered");
                    }
                });

        // Capacity check
        long registeredCount = registrationJpaRepo
                .countByEventIdAndStatus(eventId, RegistrationStatus.REGISTERED);

        if (registeredCount >= event.getMaxParticipants()) {
            throw new RuntimeException("Event is full");
        }

        Registration registration = new Registration(employee, event);
        return registrationJpaRepo.save(registration);
    }

    public void cancel(Long registrationId) {

        Registration registration = registrationJpaRepo.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));

        if (registration.getStatus() == RegistrationStatus.CANCELLED) {
            return; // idempotent cancel
        }

        registration.setStatus(RegistrationStatus.CANCELLED);
        registrationJpaRepo.save(registration);
    }

    public List<Registration> getByEvent(Long eventId) {
        return registrationJpaRepo.findByEventId(eventId);
    }

    public List<Registration> getByEmployee(Long employeeId) {
        return registrationJpaRepo.findByEmployeeId(employeeId);
    }

    public Registration markAttendance(Long registrationId) {

        Registration registration = registrationJpaRepo.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));

        if (registration.getStatus() != RegistrationStatus.REGISTERED) {
            throw new RuntimeException("Attendance can be marked only for REGISTERED status");
        }

        registration.setStatus(RegistrationStatus.ATTENDED);
        return registrationJpaRepo.save(registration);
    }
}
