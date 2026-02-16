package com.luisdev.heladopoints.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Scan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private boolean success;
    private String details;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Scan() {
    }

    public Scan(boolean success, String details, User user) {
        this.date = LocalDateTime.now();
        this.success = success;
        this.details = details;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
