package org.example.studysearch;

import java.util.*;

public class SearchLog {
    private final List<String> searchHistory;
    private final Map<String, Integer> searchCount;
    private boolean isLocked;
    private int numUsages;
    private String logName;

    public SearchLog(String logName) {
        this.searchHistory = new ArrayList<>();
        this.searchCount = new HashMap<>();
        this.logName = logName;
        this.numUsages = 0;
        this.isLocked = false;
    }

    // Encapsulate adding a search term and updating its count
    public void logSearch(String term) {
        if (isLocked) {
            throw new IllegalStateException("Cannot log searches; the log is locked.");
        }

        searchHistory.add(term);
        searchCount.put(term, searchCount.getOrDefault(term, 0) + 1);
        numUsages++;
    }

    // Encapsulate retrieval of search history, protecting internal list
    public List<String> getSearchHistory() {
        return Collections.unmodifiableList(searchHistory);
    }

    // Retrieve the count of a specific search term
    public int getSearchCount(String term) {
        return searchCount.getOrDefault(term, 0);
    }

    // Retrieve the most searched term(s)
    public List<String> getTopSearches() {
        int maxCount = searchCount.values().stream().max(Integer::compareTo).orElse(0);
        List<String> topSearches = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : searchCount.entrySet()) {
            if (entry.getValue() == maxCount) {
                topSearches.add(entry.getKey());
            }
        }
        return topSearches;
    }

    // Toggle locking/unlocking the log
    public void lock() {
        isLocked = true;
    }

    public void unlock() {
        isLocked = false;
    }

    // Retrieve number of usages
    public int getNumUsages() {
        return numUsages;
    }

    // Retrieve log name
    public String getLogName() {
        return logName;
    }

    // Update log name with validation
    public void setLogName(String logName) {
        if (logName == null || logName.isBlank()) {
            throw new IllegalArgumentException("Log name cannot be null or blank.");
        }
        this.logName = logName;
    }

    public void addSearchHistory(String query) {
        searchHistory.add(query);
    }

    public void setNumUsages(int numUsages) {
        this.numUsages = numUsages;
    }
}