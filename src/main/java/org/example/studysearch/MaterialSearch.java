package org.example.studysearch;

import java.util.List;

public class MaterialSearch implements Search<String> {

    private final SearchLog searchLog = new SearchLog("Material Search");

    public MaterialSearch() {}

    @Override
    public List<String> search(String text) {
        return searchLog.handleMaterialSearch(text); // Delega a busca ao SearchLog
    }

    public SearchLog getSearchLog() {
        return searchLog;
    }
}