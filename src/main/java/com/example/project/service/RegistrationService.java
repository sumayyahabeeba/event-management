package com.example.project.service;

import java.util.List;

import com.example.project.model.*;
import com.example.project.repository.EmployeeJpaRepository;
import com.example.project.repository.EventRepository;
import com.example.project.repository.RegistrationJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.project.model.Employee;
import com.example.project.model.Event;
import com.example.project.model.Registration;
import com.example.project.model.RegistrationStatus;
import com.example.project.repository.EmployeeJpaRepository;
import com.example.project.repository.EventRepository;
import com.example.project.repository.RegistrationJpaRepository;

@Service
public class RegistrationService {

    private final RegistrationJpaRepository registrationRepo;
    private final EmployeeJpaRepository employeeRepo;
    private final EventRepository eventRepo;

    public RegistrationService(RegistrationJpaRepository registrationRepo,
                               EmployeeJpaRepository employeeRepo,
                               EventRepository eventRepo) {
        this.registrationRepo = registrationRepo;
        this.employeeRepo = employeeRepo;
        this.eventRepo = eventRepo;
    }

    public List<Registration> getAllRegistrations(){
        return registrationRepo.findAll();
    }

    public Registration register(Long employeeId, Long eventId) {

        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Employee not found"));

        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Event not found"));

        // Duplicate check
        registrationRepo.findByEmployeeIdAndEventId(employeeId, eventId)
                .ifPresent(r -> {
                    if (r.getStatus() != RegistrationStatus.CANCELLED) {
                        throw new ResponseStatusException(
                                HttpStatus.CONFLICT,
                                "Employee already registered for this event");
                    }
                });

        // Capacity check
        long registeredCount = registrationRepo
                .countByEventIdAndStatus(eventId, RegistrationStatus.REGISTERED);

        if (registeredCount >= event.getMaxParticipants()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Event is already full");
        }

        return registrationRepo.save(new Registration(employee, event));
    }

    public void cancel(Long registrationId) {

        Registration registration = registrationRepo.findById(registrationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Registration not found"));

        if (registration.getStatus() == RegistrationStatus.CANCELLED) {
            return;
        }

        registration.setStatus(RegistrationStatus.CANCELLED);
        registrationRepo.save(registration);
    }

    public List<Registration> getByEvent(Long eventId) {
        return registrationRepo.findByEventId(eventId);
    }

    public List<Registration> getByEmployee(Long employeeId) {
        return registrationRepo.findByEmployeeId(employeeId);
    }

    public Registration markAttendance(Long registrationId) {

        Registration registration = registrationRepo.findById(registrationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Registration not found"));

        if (registration.getStatus() != RegistrationStatus.REGISTERED) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Attendance can be marked only for REGISTERED status");
        }

        registration.setStatus(RegistrationStatus.ATTENDED);
        return registrationRepo.save(registration);
    }
}
