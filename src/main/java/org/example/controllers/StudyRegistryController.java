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
    private final StudyTaskManager studyTaskManager = StudyTaskManager.getStudyTaskManager();
    private final StudyMaterial studyMaterial = StudyMaterial.getStudyMaterial();
    private final Map<String, Runnable> actions = new HashMap<>();

    public StudyRegistryController() {
        assignActions();
    }

    private void assignActions() {
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
        StudyObjective.ObjectiveDetails details = collectObjectiveDetails();
        objective.handleSetObjective(details);
    }

    private StudyObjective.ObjectiveDetails collectObjectiveDetails() {
        var basicDetails = collectBasicObjectiveDetails();
        var additionalDetails = collectAdditionalObjectiveDetails();

        return new StudyObjective.ObjectiveDetails(
                basicDetails.id(),
                basicDetails.priority(),
                basicDetails.practicedDays(),
                basicDetails.targetDate(),
                additionalDetails.name(),
                additionalDetails.title(),
                additionalDetails.description(),
                additionalDetails.topic(),
                additionalDetails.objectiveInOneLine(),
                additionalDetails.objectiveFullDescription(),
                additionalDetails.motivation(),
                additionalDetails.duration(),
                additionalDetails.isActive()
        );
    }

    private BasicObjectiveDetails collectBasicObjectiveDetails() {
        int id = promptInt("Type the ID: ");
        int priority = promptInt("Type the priority: ");
        int practicedDays = promptInt("Type the practiced days: ");
        LocalDateTime targetDate = collectTargetDate();

        return new BasicObjectiveDetails(id, priority, practicedDays, targetDate);
    }

    private AdditionalObjectiveDetails collectAdditionalObjectiveDetails() {
        String name = promptString("Type the name: ");
        String title = promptString("Type the title: ");
        String description = promptString("Type the description: ");
        String topic = promptString("Type the topic: ");
        String objectiveInOneLine = promptString("Type the objective in one line: ");
        String objectiveFullDescription = promptString("Type the full description: ");
        String motivation = promptString("Type the motivation: ");
        double duration = promptDouble("Type the duration: ");
        boolean isActive = promptBoolean("Is the objective active? (true/false): ");

        return new AdditionalObjectiveDetails(
                name, title, description, topic, objectiveInOneLine,
                objectiveFullDescription, motivation, duration, isActive
        );
    }

    private LocalDateTime collectTargetDate() {
        int day = promptInt("Type the day: ");
        int month = promptInt("Type the month: ");
        int year = promptInt("Type the year: ");
        return LocalDateTime.of(year, month, day, 0, 0);
    }

    private String promptString(String message) {
        System.out.println(message);
        return getInput();
    }

    private int promptInt(String message) {
        System.out.println(message);
        return Integer.parseInt(getInput());
    }

    private double promptDouble(String message) {
        System.out.println(message);
        return Double.parseDouble(getInput());
    }

    private boolean promptBoolean(String message) {
        System.out.println(message);
        return Boolean.parseBoolean(getInput());
    }

    private StudyObjective getStudyObjectiveInfo() {
        handleMethodHeader("(Study Objective Creation)");
        String title = promptString("Type the objective title: ");
        String description = promptString("Type the objective description: ");
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

        try {
            System.out.println("Type the following info: String firstStep, String resetStudyMechanism, String consistentStep, " +
                    "String seasonalSteps, String basicSteps, String mainObjectiveTitle, String mainGoalTitle, String mainMaterialTopic, " +
                    "String mainTask, Integer numberOfSteps, boolean isImportant. " +
                    "The Date to start is today, the date to end is x days from now, type the quantity of days\n");

            LocalDateTime createdAt = LocalDateTime.now();

            // Criação de StepDetails diretamente
            StudyPlan.StepDetails stepDetails = new StudyPlan.StepDetails(
                    getInput(), // firstStep
                    getInput(), // resetStudyMechanism
                    getInput(), // consistentStep
                    getInput(), // seasonalSteps
                    getInput(), // basicSteps
                    getInput(), // mainObjectiveTitle
                    getInput(), // mainGoalTitle
                    getInput(), // mainMaterialTopic
                    getInput(), // mainTask
                    promptInt("Number of Steps: "),
                    promptBoolean("Is it important? (true/false): "),
                    createdAt,
                    createdAt.plusDays(promptInt("Number of days to end the task: "))
            );

            // Adicionar os passos ao plano
            studyPlan.assignSteps(stepDetails);
        } catch (Exception e) {
            System.out.println("Error setting steps: " + e.getMessage());
        }
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

    private AudioReference addAudioReference() {
        handleMethodHeader("(Audio Reference Creation)");
        System.out.println("Type the AudioQuality (LOW | MEDIUM | HIGH | VERY_HIGH): ");
        AudioReference.AudioQuality quality = AudioReference.audioQualityAdapter(getInput());
        AudioReference audioReference = new AudioReference(quality);
        editAudio(audioReference);
        return audioReference;
    }

    private void editAudio(AudioReference audioReference) {
        handleMethodHeader("(Audio Edit)");
        System.out.println("Type the following attributes: title, description, link, access rights, license, language, rating (integer), view count (integer), share count (integer), is downloadable (true/false): ");
        audioReference.editAudio(
                AudioReference.audioQualityAdapter(getInput()),
                new AudioReference.AudioAttributes(
                        getInput(), getInput(), getInput(), getInput(), getInput(), getInput(),
                        Integer.parseInt(getInput()), Integer.parseInt(getInput()), Integer.parseInt(getInput()),
                        Boolean.parseBoolean(getInput())
                )
        );
    }

    private VideoReference addVideoReference() {
        handleMethodHeader("(Video Reference Creation)");
        System.out.println("Type the following info: boolean isAvailable, String title, String description, String resolution, String frameRate, String videoFormat, String accessRights \n");
        return new VideoReference(Boolean.parseBoolean(getInput()), getInput(), getInput(), getInput(), getInput(),
                getInput(), getInput());
    }

    private TextReference addTextReference() {
        handleMethodHeader("(Text Reference Creation)");
        System.out.println("Type the following info: String title, String language, int wordCount, String format, String accessRights \n");
        return new TextReference(getInput(), getInput(), Integer.parseInt(getInput()), getInput(), getInput());
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
        System.out
                .println("(Study Task Manager Week Set Up) Type the following info: String planName, String objectiveTitle, String objectiveDescription, String materialTopic, String materialFormat, String goal, String reminderTitle, String reminderDescription, String mainTaskTitle, String mainHabit, String mainCardStudy");
        studyTaskManager.setUpWeek(getInput(), getInput(), getInput(), getInput(), getInput(), getInput(), getInput(), getInput(), getInput(), getInput(), getInput());
    }

    private void handleSetUpWeek() {
        getWeekInfo();
    }

    private void handleGetWeekResponsibilities() {
        List<String> responsibilities = studyTaskManager.getWeekResponsibilities();
        System.out.println(String.join(", ", responsibilities));
    }

    public void handleRegistryInput() {
        try {
            while (true) {
                controllerOptions();
                String response = validateInput(actions);
                if (response == null) {
                    return;
                }
                actions.get(response).run();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void controllerOptions() {
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

    // Classes auxiliares para coletar detalhes básicos e complementares
    private record BasicObjectiveDetails(
            int id,
            int priority,
            int practicedDays,
            LocalDateTime targetDate
    ) {}

    private record AdditionalObjectiveDetails(
            String name,
            String title,
            String description,
            String topic,
            String objectiveInOneLine,
            String objectiveFullDescription,
            String motivation,
            double duration,
            boolean isActive
    ) {}
}