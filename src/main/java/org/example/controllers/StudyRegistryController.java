package org.example.controllers;

import org.example.studymaterial.AudioReference;
import org.example.studymaterial.Reference;
import org.example.studymaterial.TextReference;
import org.example.studymaterial.VideoReference;
import org.example.studyregistry.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.controllers.MainController.getInput;
import static org.example.controllers.MainController.validateInput;

public class StudyRegistryController {
    StudyTaskManager studyTaskManager = StudyTaskManager.getStudyTaskManager();
    StudyMaterial studyMaterial = StudyMaterial.getStudyMaterial();
    private Map<String, Runnable> actions = new HashMap<>();

    public StudyRegistryController() {
        assignActions();
    }

    void assignActions() {
        actions.put("1", this::handleAddStudyTask);
        actions.put("2", this::handleAddStudyGoal);
        actions.put("3", this::handleAddStudyMaterial);
        actions.put("4", this::handleAddStudyObjective);
        actions.put("5", this::handleAddStudyPlan);
        actions.put("6", this::handleSetUpWeek);
        actions.put("7", this::handleGetWeekResponsibilities);
    }

    private void handleMethodHeader(String header) {
        System.out.println("~~~~" + header + "~~~~\n");
    }

    private Task getStudyTaskInfo() {
        System.out.println("Type the following info: title, description, author \n");
        String title = getInput();
        String description = getInput();
        String author = getInput();
        return new Task(title, description, author, LocalDateTime.now());
    }

    private void handleAddStudyTask() {
        Task task = getStudyTaskInfo();
        studyTaskManager.addRegistry(task);
    }

    private void handleSetObjective(StudyObjective objective) {
        handleMethodHeader("(Study Objective Edit)");
        System.out.println("Type the following info: Integer id, Integer priority " +
                "Integer practicedDays, int day, int month, int year, String name, String title, String description, " +
                "String topic, String objectiveInOneLine, String objectiveFullDescription, String motivation, " +
                "Double duration, boolean isActive  \n");
        objective.handleSetObjective(Integer.parseInt(getInput()), Integer.parseInt(getInput()), Integer.parseInt(getInput()), Integer.parseInt(getInput()), Integer.parseInt(getInput()),
                Integer.parseInt(getInput()), getInput(), getInput(), getInput(), getInput(), getInput(), getInput(), getInput(),
                Double.parseDouble(getInput()), Boolean.parseBoolean(getInput()));
    }

    private StudyObjective getStudyObjectiveInfo() {
        handleMethodHeader("(Study Objective Creation)");
        System.out.println("Type the following info: title, description \n");
        String title = getInput();
        String description = getInput();
        StudyObjective studyObjective = new StudyObjective(title, description);
        handleSetObjective(studyObjective);
        studyTaskManager.addRegistry(studyObjective);
        return studyObjective;
    }

    private StudyPlan getStudyPlanInfo() {
        handleMethodHeader("(Study Plan Creation)");
        System.out.println("Type the following info: name \n");
        String name = getInput();
        StudyObjective studyObjective = getStudyObjectiveInfo();
        StudyPlan plan = new StudyPlan(name, studyObjective, new ArrayList<>());
        studyTaskManager.addRegistry(plan);
        return plan;
    }

    private void handleSetSteps(StudyPlan studyPlan) {
        handleMethodHeader("(Study Plan Edit)");
        System.out.println("Type the following info: String firstStep, String resetStudyMechanism, String consistentStep, " +
                "String seasonalSteps, String basicSteps, String mainObjectiveTitle, String mainGoalTitle, String mainMaterialTopic, " +
                "String mainTask, @NotNull  Integer numberOfSteps, boolean isImportant. " +
                "The Date to start is today, the date to end is x days from now, type the quantity of days\n");
        LocalDateTime createdAT = LocalDateTime.now();
        studyPlan.assignSteps(getInput(), getInput(), getInput(), getInput(), getInput(), getInput(), getInput(), getInput(), getInput(),
                Integer.parseInt(getInput()), Boolean.parseBoolean(getInput()), createdAT, createdAT.plusDays(Long.parseLong(getInput())));
    }

    private StudyGoal getStudyGoalInfo() {
        handleMethodHeader("(Study Goal Creation)");
        System.out.println("Type the following info: name \n");
        String name = getInput();
        StudyPlan studyPlan = getStudyPlanInfo();
        handleSetSteps(studyPlan);
        StudyObjective studyObjective = studyPlan.getObjective();
        return new StudyGoal(name, studyObjective, studyPlan);
    }

    private void handleAddStudyGoal() {
        StudyGoal goal = getStudyGoalInfo();
        studyTaskManager.addRegistry(goal);
    }

    private void editAudio(AudioReference audioReference) {
        handleMethodHeader("(Audio Edit)");
        AudioReference.AudioQuality quality = promptAudioQuality();
        AudioReference.AudioAttributes attributes = promptAudioAttributes();
        audioReference.editAudio(quality, attributes);
    }

    private AudioReference.AudioQuality promptAudioQuality() {
        System.out.println("Type the AudioQuality (LOW | MEDIUM | HIGH | VERY_HIGH): ");
        return AudioReference.audioQualityAdapter(getInput());
    }

    private AudioReference.AudioAttributes promptAudioAttributes() {
        return new AudioReference.AudioAttributes(
                promptString("Enter the title: "),
                promptString("Enter the description: "),
                promptString("Enter the link: "),
                promptString("Enter the access rights: "),
                promptString("Enter the license: "),
                promptString("Enter the language: "),
                promptInt("Enter the rating (integer): "),
                promptInt("Enter the view count (integer): "),
                promptInt("Enter the share count (integer): "),
                promptBoolean("Is the audio downloadable? (true/false): ")
        );
    }

    private boolean promptBoolean(String message) {
        System.out.println(message);
        return Boolean.parseBoolean(getInput());
    }

    private String promptString(String message) {
        System.out.println(message);
        return getInput();
    }

    private int promptInt(String message) {
        System.out.println(message);
        return Integer.parseInt(getInput());
    }

    private AudioReference addAudioReference() {
        handleMethodHeader("(Audio Reference Creation)");
        AudioReference audioReference = new AudioReference(promptAudioQuality());
        editAudio(audioReference);
        return audioReference;
    }

    private VideoReference addVideoReference() {
        handleMethodHeader("(Video Reference Creation)");
        System.out.println("Type the following info: boolean isAvailable, String title, " +
                "String description, String resolution, String frameRate, String videoFormat, String accessRights \n");
        return new VideoReference(Boolean.parseBoolean(getInput()), getInput(), getInput(), getInput(), getInput(),
                getInput(), getInput());
    }

    private TextReference addTextReference() {
        handleMethodHeader("(Text Reference Creation)");
        System.out.println("Type the following info:  String title, String language, int wordCount, String format, String accessRights \n");
        return new TextReference(getInput(), getInput(), Integer.parseInt(getInput()), getInput(),
                getInput());
    }

    private Reference addStudyMaterial() {
        handleMethodHeader("(Study Material Creation)");
        System.out.println("Type the following info: ( AUDIO | VIDEO | TEXT ) \n");
        String type = getInput();
        return switch (type.toLowerCase()) {
            case "audio" -> addAudioReference();
            case "video" -> addVideoReference();
            case "text" -> addTextReference();
            default -> null;
        };
    }

    private void handleAddStudyMaterial() {
        Reference reference = addStudyMaterial();
        if (reference != null) {
            studyMaterial.addReference(reference);
        }
    }

    private void handleAddStudyObjective() {
        getStudyObjectiveInfo();
    }

    private void handleAddStudyPlan() {
        getStudyPlanInfo();
        System.out.println("Study Plan Added");
    }

    private void getWeekInfo() {
        System.out.println("(Study Task Manager Week Set Up) Type the following info: String planName, String objectiveTitle, " +
                "String objectiveDescription, String materialTopic, String materialFormat, String goal, String reminderTitle, " +
                "String reminderDescription, String mainTaskTitle, String mainHabit, String mainCardStudy");
        studyTaskManager.setUpWeek(getInput(), getInput(), getInput(), getInput(), getInput(), getInput(), getInput(), getInput(),
                getInput(), getInput(), getInput());
    }

    private void handleSetUpWeek() {
        getWeekInfo();
    }

    private void handleGetWeekResponsibilities() {
        List<String> responsibilities = studyTaskManager.getWeekResponsibilities();
        System.out.println(String.join(", ", responsibilities));
    }

    public void handleRegistryInput(){
        try{
            while(true){
                controllerOptions();
                String response = validateInput(actions);
                if(response == null) {return;}
                actions.get(response).run();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void controllerOptions(){
        System.out.println("""
                0 - return
                1 - add study task
                2 - add study goal
                3 - add study material (audio, video, text)
                4 - add study objective
                5 - add study plan
                6 - set up week
                7 - get week responsibilities
               """);
    }
}
