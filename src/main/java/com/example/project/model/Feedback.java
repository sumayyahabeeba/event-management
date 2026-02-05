package com.example.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "feedbacks",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"employee_id", "event_id"})
    }
)
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Proper relationship with Employee
    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    // Proper relationship with Event
    @ManyToOne(optional = false)
    @JoinColumn(name = "event_id")
    private Event event;

    private int rating;        // 1–5
    private String comment;

    public Feedback() {}

    public Feedback(Employee employee, Event event, int rating, String comment) {
        this.employee = employee;
        this.event = event;
        this.rating = rating;
        this.comment = comment;
    }

    // Getters & Setters
    public Long getId() {
        return id;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }    

    public String getComment() {
        return comment;
    }    

    public void setComment(String comment) {
        this.comment = comment;
    }
}
