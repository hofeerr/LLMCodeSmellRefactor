package org.example.studymaterial;

import java.util.List;

public class AudioReference extends Reference {
    public enum AudioQuality {
        LOW, MEDIUM, HIGH, VERY_HIGH;
    }

    private AudioQuality audioQuality;

    public AudioReference(AudioQuality quality) {
        this.audioQuality = quality;
    }

    public AudioQuality getAudioQuality() {
        return audioQuality;
    }

    public static AudioQuality audioQualityAdapter(String quality) {
        return switch (quality.toLowerCase()) {
            case "low" -> AudioQuality.LOW;
            case "medium" -> AudioQuality.MEDIUM;
            case "high" -> AudioQuality.HIGH;
            case "very_high" -> AudioQuality.VERY_HIGH;
            default -> null;
        };
    }

    public void setAudioQuality(AudioQuality audioQuality) {
        this.audioQuality = audioQuality;
    }

    // Record para encapsular os parâmetros de editAudio
    public record AudioAttributes(
            String title,
            String description,
            String link,
            String accessRights,
            String license,
            String language,
            int rating,
            int viewCount,
            int shareCount,
            boolean isDownloadable
    ) {}

    public void editAudio(AudioQuality audioQuality, AudioAttributes attributes) {
        editBasic(attributes.title(), attributes.description(), attributes.link());
        this.setAccessRights(attributes.accessRights());
        this.setLicense(attributes.license());
        this.setAudioQuality(audioQuality);
        editVideoAttributes(attributes);
    }

    public void editAudioAdapter(List<String> properties, List<Integer> intProperties, AudioQuality audioQuality, boolean isDownloadable) {
        AudioAttributes attributes = new AudioAttributes(
                properties.get(0), // title
                properties.get(1), // description
                properties.get(2), // link
                properties.get(3), // accessRights
                properties.get(4), // license
                properties.get(5), // language
                intProperties.get(0), // rating
                intProperties.get(1), // viewCount
                intProperties.get(2), // shareCount
                isDownloadable // isDownloadable
        );
        this.editAudio(audioQuality, attributes);
    }

    private void editVideoAttributes(AudioAttributes attributes) {
        this.setRating(attributes.rating());
        this.setLanguage(attributes.language());
        this.setDownloadable(attributes.isDownloadable());
        this.getInteractionStatistics().setViewCount(attributes.viewCount());
        this.getInteractionStatistics().setShareCount(attributes.shareCount());
    }

    public void editBasic(String title, String description, String link) {
        this.setTitle(title);
        this.setDescription(description);
        this.setLink(link);
    }
}