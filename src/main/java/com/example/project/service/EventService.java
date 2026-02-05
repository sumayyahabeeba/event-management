package com.example.project.service;

import com.example.project.model.Employee;
import com.example.project.model.Event;
import com.example.project.model.Registration;
import com.example.project.repository.EventRepository;
import com.example.project.repository.RegistrationJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RegistrationJpaRepository registrationJpaRepository;

    public Event createEvent(Event event) {
        event.setStatus("PLANNED");
        return eventRepository.save(event);
    }

    public Event updateEvent(Long id, Event updatedEvent) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Event not found"));

        event.setTitle(updatedEvent.getTitle());
        event.setDescription(updatedEvent.getDescription());
        event.setEventDate(updatedEvent.getEventDate());
        event.setStartTime(updatedEvent.getStartTime());
        event.setEndTime(updatedEvent.getEndTime());
        event.setMaxParticipants(updatedEvent.getMaxParticipants());

        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Event not found"));
    }

    public void cancelEvent(Long id) {
        Event event = getEventById(id);
        event.setStatus("CANCELLED");
        eventRepository.save(event);
    }

    public List<Event> getUpcomingEvents() {
        return eventRepository.findByEventDateAfter(LocalDate.now());
    }

    public List<Event> getPastEvents() {
        return eventRepository.findByEventDateBefore(LocalDate.now());
    }

    public List<Employee> allParticipantsByEventId(Long id) {
        List<Registration> regs =
                registrationJpaRepository.findByEventId(id);

        return regs.stream()
                .map(registration->registration.getEmployee())
                .toList();
    }
}

