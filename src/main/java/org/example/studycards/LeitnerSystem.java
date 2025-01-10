package org.example.studycards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeitnerSystem extends StudyMethod {
    List<Box> boxes = null;

    public LeitnerSystem(String methodName) {
        super(methodName);
        initializeBoxes();
    }

    private void initializeBoxes() {
        boxes = new ArrayList<>(Arrays.asList(new Box(), new Box(), new Box(), new Box(), new Box()));
    }

    @Override
    public String getMethodName() {
        return this.methodName;
    }

    @Override
    void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String toString() {
        return buildBoxesDescription();
    }

    private String buildBoxesDescription() {
        StringBuilder response = new StringBuilder();
        int index = 0;
        for (Box box : boxes) {
            response.append("Box ").append(index).append(": ").append(box.toString()).append("\n");
            index++;
        }
        return response.toString();
    }

    public void clearBoxes() {
        initializeBoxes();
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public String getRandomCardFromAllBoxes() {
        StringBuilder response = new StringBuilder();
        response.append(getMethodName()).append("\n");
        List<Box> boxes = getBoxes();
        response.append(getRandomCard(boxes));
        return response.toString();
    }

    public String getRandomCard(List<Box> otherBoxes) {
        if (isInvalidBoxList(otherBoxes)) {
            return null;
        }
        return generateRandomCardResponse(otherBoxes);
    }

    private boolean isInvalidBoxList(List<Box> otherBoxes) {
        return otherBoxes == null || otherBoxes.isEmpty();
    }

    private String generateRandomCardResponse(List<Box> otherBoxes) {
        Box allBoxes = combineAllBoxes(otherBoxes);
        Integer randomCard = allBoxes.getRandomCard();
        return randomCard == null ? "No card found" : formatCardResponse(randomCard);
    }

    private Box combineAllBoxes(List<Box> otherBoxes) {
        Box allBoxes = new Box();
        for (Box box : otherBoxes) {
            allBoxes.addCards(box.getCards());
        }
        return allBoxes;
    }

    private String formatCardResponse(Integer randomCard) {
        CardManager manager = CardManager.getCardManager();
        Card card = manager.getCard(randomCard);
        return String.format("[%d] The random question was: %s | The answer is: %s",
                randomCard, card.getQuestion(), card.getAnswer());
    }

    public void addCardToBox(Integer id, Integer boxId) {
        this.boxes.get(boxId).addCard(id);
    }

    public void removeCardFromBox(Integer id, Integer boxId) {
        this.boxes.get(boxId).removeCard(id);
    }

    public Card takeCardFromBox(Integer boxId) {
        Integer cardId = boxes.get(boxId).getRandomCard();
        return this.cardManager.getCard(cardId);
    }

    public void boxIdValidation(Integer boxId) throws Exception {
        if (isInvalidBoxId(boxId)) {
            throw new Exception("Invalid box ID");
        }
    }

    private boolean isInvalidBoxId(Integer boxId) {
        return boxId == null || boxId > (boxes.size() - 1) || boxId <= 0;
    }

    public void upgradeCard(Integer cardId, Integer boxId) throws Exception {
        boxIdValidation(boxId);
        processCardUpgrade(cardId, boxId);
    }

    private void processCardUpgrade(Integer cardId, Integer boxId) throws Exception {
        Box refBox = boxes.get(boxId);
        if (refBox.hasCard(cardId)) {
            throw new Exception("No card Found");
        }
        refBox.removeCard(cardId);
        boxes.get(Math.min(boxId + 1, 4)).addCard(cardId);
    }

    public void downgradeCard(Integer cardId, Integer boxId) throws Exception {
        boxIdValidation(boxId);
        processCardDowngrade(cardId, boxId);
    }

    private void processCardDowngrade(Integer cardId, Integer boxId) throws Exception {
        Box refBox = boxes.get(boxId);
        if (refBox.hasCard(cardId)) {
            throw new Exception("No card Found");
        }
        refBox.removeCard(cardId);
        boxes.get(Math.max(boxId - 1, 0)).addCard(cardId);
    }
}