package org.example.studyregistry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudyPlan extends Registry {
    private StudyObjective objective;
    private List<String> steps;

    public StudyPlan(String planName, StudyObjective objective, List<StudyMaterial> materials) {
        this.name = planName;
        this.objective = objective;
        this.steps = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Plan: " + name + ",\nObjective: " + objective.getDescription() + ",\nSteps: " + String.join(", ", steps);
    }

    public List<String> getSteps() {
        return steps;
    }

    public StudyObjective getObjective() {
        return objective;
    }

    public void assignObjective(StudyObjective objective) {
        this.objective = objective;
    }

    public void addSingleStep(String toAdd) {
        steps.add(toAdd);
    }

    // Record para encapsular os detalhes das etapas
    public record StepDetails(
            String firstStep,
            String resetStudyMechanism,
            String consistentStep,
            String seasonalSteps,
            String basicSteps,
            String mainObjectiveTitle,
            String mainGoalTitle,
            String mainMaterialTopic,
            String mainTask,
            Integer numberOfSteps,
            boolean isImportant,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {}

    // Método refatorado para usar StepDetails
    public void assignSteps(StepDetails stepDetails) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        this.steps = new ArrayList<>(Arrays.asList(
                stepDetails.firstStep(),
                stepDetails.resetStudyMechanism(),
                stepDetails.consistentStep(),
                stepDetails.seasonalSteps(),
                stepDetails.basicSteps(),
                "Number of steps: " + stepDetails.numberOfSteps(),
                "Is it important to you? " + stepDetails.isImportant(),
                stepDetails.startDate().format(formatter),
                stepDetails.endDate().format(formatter),
                stepDetails.mainObjectiveTitle(),
                stepDetails.mainGoalTitle(),
                stepDetails.mainMaterialTopic(),
                stepDetails.mainTask()
        ));
    }

    // Método handleAssignSteps também refatorado
    public void handleAssignSteps(List<String> stringProperties, Integer numberOfSteps, boolean isImportant,
                                  LocalDateTime startDate, LocalDateTime endDate) {
        StepDetails stepDetails = new StepDetails(
                stringProperties.get(0), stringProperties.get(1), stringProperties.get(2),
                stringProperties.get(3), stringProperties.get(4), stringProperties.get(5),
                stringProperties.get(6), stringProperties.get(7), stringProperties.get(8),
                numberOfSteps, isImportant, startDate, endDate
        );
        assignSteps(stepDetails);
    }
}
