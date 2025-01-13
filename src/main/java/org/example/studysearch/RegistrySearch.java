package org.example.studysearch;

import java.util.List;

public class RegistrySearch implements Search<String> {
    private final SearchLog searchLog = new SearchLog("Registry Search");

    public RegistrySearch() {}

    @Override
    public List<String> search(String text) {
        return searchLog.handleRegistrySearch(text); // Delega a busca ao SearchLog
    }

    public SearchLog getSearchLog() {
        return searchLog;
    }
}
