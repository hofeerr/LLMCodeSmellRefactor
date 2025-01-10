package org.example.studysearch;

import org.example.studycards.CardManager;
import org.example.studyplanner.HabitTracker;
import org.example.studyplanner.TodoTracker;
import org.example.studyregistry.StudyMaterial;
import org.example.studyregistry.StudyTaskManager;

import java.util.ArrayList;
import java.util.List;

public class GeneralSearch implements Search<String> {
    private final SearchLog searchLog = new SearchLog("General Search");

    public GeneralSearch() {
    }

    @Override
    public List<String> search(String text) {
        return searchLog.handleSearch(text); // Delega a lógica para SearchLog
    }

    public SearchLog getSearchLog() {
        return searchLog;
    }
}
