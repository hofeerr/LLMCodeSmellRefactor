package org.example.studyplanner;

public class TimelineView {

    public String habitDateViewAll(HabitTracker ht) {
        // Apenas delega a responsabilidade ao método de HabitTracker
        return ht.getFormattedHabitView();
    }
}
