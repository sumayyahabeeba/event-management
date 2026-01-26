package com.example.project.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "registrations")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Employee employee;

    @ManyToOne(optional = false)
    private Event event;

    private LocalDate registeredOn;

    @Enumerated(EnumType.STRING)
    private RegistrationStatus status;

    public Registration() {}

    public Registration(Employee employee, Event event) {
        this.employee = employee;
        this.event = event;
        this.registeredOn = LocalDate.now();
        this.status = RegistrationStatus.REGISTERED;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public LocalDate getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDate registeredOn) {
        this.registeredOn = registeredOn;
    }

    public RegistrationStatus getStatus() {
        return status;
    }

    public void setStatus(RegistrationStatus status) {
        this.status = status;
    }
}
