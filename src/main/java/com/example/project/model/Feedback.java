package com.example.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long eventId;      // no Event entity yet (simple)
    private Long employeeId;   // reference to employee

    private int rating;        // 1–5
    private String comment;

    // Constructors
    public Feedback() {}

    public Feedback(Long eventId, Long employeeId, int rating, String comment) {
        this.eventId = eventId;
        this.employeeId = employeeId;
        this.rating = rating;
        this.comment = comment;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}
