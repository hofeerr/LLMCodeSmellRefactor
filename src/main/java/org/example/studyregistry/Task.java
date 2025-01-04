package org.example.studyregistry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task extends Registry {
    private String title;
    private String description;
    private String author;
    private LocalDateTime date;

    public Task(String title, String description, String author, LocalDateTime date) {
        this.title = title;
        this.name = title;
        this.description = description;
        this.author = author;
        this.date = date;
    }

    // Encapsulating behavior related to date formatting and manipulation
    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return this.date.format(formatter);
    }

    public boolean isDueBefore(LocalDateTime otherDate) {
        return this.date.isBefore(otherDate);
    }

    public boolean isDueAfter(LocalDateTime otherDate) {
        return this.date.isAfter(otherDate);
    }

    public void postponeByDays(int days) {
        this.date = this.date.plusDays(days);
    }

    public void advanceByDays(int days) {
        this.date = this.date.minusDays(days);
    }

    // Getters and setters for fields
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
