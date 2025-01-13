package org.example.studymaterial;

public abstract class Reference {
    private String title;
    private String description;
    private String link;
    private String accessRights;
    private String license;
    private boolean isDownloadable;
    private int rating;
    private String language;

    private InteractionStatistics interactionStatistics;

    public Reference() {
        this.interactionStatistics = new InteractionStatistics();
    }

    // Encapsulate logic related to interaction statistics
    public void incrementViewCount() {
        interactionStatistics.incrementViewCount();
    }

    public void incrementDownloadCount() {
        interactionStatistics.incrementDownloadCount();
    }

    public void incrementShareCount() {
        interactionStatistics.incrementShareCount();
    }

    public int getViewCount() {
        return interactionStatistics.getViewCount();
    }

    public int getDownloadCount() {
        return interactionStatistics.getDownloadCount();
    }

    public int getShareCount() {
        return interactionStatistics.getShareCount();
    }

    // Getters and Setters for Reference attributes
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public String getAccessRights() {
        return accessRights;
    }

    public void setAccessRights(String accessRights) {
        this.accessRights = accessRights;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public boolean isDownloadable() {
        return isDownloadable;
    }

    public void setDownloadable(boolean downloadable) {
        isDownloadable = downloadable;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public InteractionStatistics getInteractionStatistics() {
        return interactionStatistics;
    }

    public boolean getIsDownloadable() {
        return isDownloadable();
    }
}

class InteractionStatistics {
    private int viewCount;
    private int downloadCount;
    private int shareCount;

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public void incrementViewCount() {
        viewCount++;
    }

    public void incrementDownloadCount() {
        downloadCount++;
    }

    public void incrementShareCount() {
        shareCount++;
    }

    public int getViewCount() {
        return viewCount;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public int getShareCount() {
        return shareCount;
    }
}
