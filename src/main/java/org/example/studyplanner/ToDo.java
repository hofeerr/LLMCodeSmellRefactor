package org.example.studyplanner;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ToDo implements PlannerMaterial {
    private Integer id;
    private String title;
    private String description;
    private int priority;

    public ToDo(Integer id, String title, String description, int priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return MessageFormat.format("[(Priority:{3}) ToDo {0}: {1}, {2}]", id, title, description, priority);
    }

    /**
     * Adds domain logic to determine if the ToDo item is high priority.
     */
    public boolean isHighPriority() {
        return priority > 7;
    }

    /**
     * Updates the description with a timestamp (encapsulates description logic).
     */
    public void updateDescription(String newDescription) {
        this.description = newDescription + " (Updated at: " + System.currentTimeMillis() + ")";
    }

    /**
     * Generates detailed information about the ToDo, including execution times.
     */
    public String getDetailedInfo(List<LocalDateTime> executionTimes) {
        StringBuilder str = new StringBuilder();
        str.append(toString()).append("\n");
        if (executionTimes == null || executionTimes.isEmpty()) {
            str.append("No tracks found\n");
        } else {
            for (LocalDateTime dateTime : executionTimes) {
                str.append(formatDate(dateTime)).append("\n");
            }
        }
        return str.toString();
    }

    private String formatDate(LocalDateTime dateTime) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(dateTime);
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Moves responsibility for tracking ToDo items to ToDoTracker.
     */
    public void markAsCompleted(TodoTracker tracker) {
        tracker.markCompleted(this);
    }

    /**
     * Encapsulates logic to assign this ToDo to a habit using HabitTracker.
     */
    public void assignToHabit(HabitTracker tracker, Habit habit) {
        tracker.assignToHabit(this, habit);
    }
}