package org.example.studymaterial;

public class VideoReference extends Reference {
    private boolean isAvailable;
    private String resolution;
    private String frameRate;
    private String videoFormat;

    public VideoReference(String title, String description){
        this.setTitle(title);
        this.setDescription(description);
    }

    public VideoReference(boolean isAvailable, String title, String description, String resolution, String frameRate, String videoFormat, String accessRights){
        this.isAvailable = isAvailable;
        this.resolution = resolution;
        this.frameRate = frameRate;
        this.videoFormat = videoFormat;
        this.setTitle(title);
        this.setDescription(description);
        this.setAccessRights(accessRights);
    }

    public void editAvailability(boolean isAvailable, boolean isDownloadable){
        this.isAvailable = isAvailable;
        this.setDownloadable(isDownloadable);
    }

    public boolean handleStreamAvailability() {
        return isAvailable && this.isDownloadable();
    }
}
