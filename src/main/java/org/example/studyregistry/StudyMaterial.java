package org.example.studyregistry;
import org.example.studymaterial.AudioReference;
import org.example.studymaterial.Reference;
import org.example.studymaterial.TextReference;
import org.example.studymaterial.VideoReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudyMaterial{
    List<Reference> references;
    private static StudyMaterial studyMaterial;
    private Map<String, Integer> referenceCount;

    private StudyMaterial(){
        references = new ArrayList<Reference>();
    }

    public static StudyMaterial getStudyMaterial(){
        if(studyMaterial == null){
            studyMaterial = new StudyMaterial();
        }
        return studyMaterial;
    }

    public void addReference(Reference ref){
        references.add(ref);
    }

    List<Reference> getReferences(){
        return references;
    }

    public List<Reference> getTypeReference(Reference type){
        List<Reference> response = new ArrayList<>();
        for(Reference reference : references){
            if(reference.getClass() == type.getClass()){
                response.add(reference);
            }
        }
        return response;
    }

    public void setReferenceCount(Map<String, Integer> referenceCount) {
        this.referenceCount = referenceCount;
    }

    public List<String> searchInMaterials(String text){
        List<String> response = new ArrayList<>();
        for(Reference reference : references){
            String mix = (reference.getTitle() != null ? reference.getTitle() : "") + (reference.getDescription() != null ? reference.getDescription() : "");
            if (mix.toLowerCase().contains(text.toLowerCase())){
                response.add(reference.getTitle());
            }
        }
        return response;
    }

    public Map<String, Integer> getReferenceCountMap() {
        Map<String, Integer> response = initializeReferenceCountMap();
        for (Reference reference : references) {
            updateReferenceCount(reference, response);
        }
        setReferenceCount(response);
        return response;
    }

    private Map<String, Integer> initializeReferenceCountMap() {
        Map<String, Integer> referenceCountMap = new HashMap<>();
        referenceCountMap.put("Audio References", 0);
        referenceCountMap.put("Video References", 0);
        referenceCountMap.put("Text References", 0);
        return referenceCountMap;
    }

    private void updateReferenceCount(Reference reference, Map<String, Integer> referenceCountMap) {
        if (reference.getClass() == AudioReference.class) {
            incrementCount("Audio References", referenceCountMap);
        } else if (reference.getClass() == VideoReference.class) {
            handleVideoReference(reference, referenceCountMap);
        } else if (reference.getClass() == TextReference.class) {
            handleTextReference(reference, referenceCountMap);
        }
    }

    private void handleVideoReference(Reference reference, Map<String, Integer> referenceCountMap) {
        if (((VideoReference) reference).handleStreamAvailability()) {
            incrementCount("Video References", referenceCountMap);
        }
    }

    private void handleTextReference(Reference reference, Map<String, Integer> referenceCountMap) {
        if (((TextReference) reference).handleTextAccess()) {
            incrementCount("Text References", referenceCountMap);
        }
    }

    private void incrementCount(String key, Map<String, Integer> referenceCountMap) {
        int currentCount = referenceCountMap.get(key);
        referenceCountMap.put(key, currentCount + 1);
    }

}
