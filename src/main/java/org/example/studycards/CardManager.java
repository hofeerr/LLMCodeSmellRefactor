package org.example.studycards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardManager {
    private Map<Integer, Card> cards;
    private Integer nextID = 1;

    private static CardManager instance = null;

    private CardManager() {
        this.cards = new HashMap<>();
    }

    public static CardManager getCardManager() {
        if (instance == null) {
            instance = new CardManager();
        }
        return instance;
    }

    /**
     * Retrieves all cards as a map.
     */
    public Map<Integer, Card> getCardsMap() {
        return cards;
    }

    /**
     * Retrieves all cards as a list.
     */
    public List<Card> getCards() {
        return new ArrayList<>(cards.values());
    }

    /**
     * Retrieves cards by their IDs.
     */
    public List<Card> getCards(List<Integer> ids) {
        List<Card> responseCards = new ArrayList<>();
        for (Integer id : ids) {
            Card card = cards.get(id);
            if (card != null) {
                responseCards.add(card);
            }
        }
        return responseCards;
    }

    /**
     * Retrieves a single card by its ID.
     */
    public Card getCard(Integer id) {
        return cards.get(id);
    }

    /**
     * Adds a new card to the system.
     */
    public Integer addCard(String question, String answer) {
        Card card = new Card(question, answer);
        Integer response = nextID;
        cards.put(nextID, card);
        nextID++;
        return response;
    }

    /**
     * Creates a new card (additional abstraction for handling creation).
     */
    public void createCard(String question, String answer) {
        addCard(question, answer);
    }

    /**
     * Deletes a card by its ID.
     */
    public void removeCard(Integer id) {
        cards.remove(id);
    }

    /**
     * Deletes a card by its ID (additional abstraction for handling deletion).
     */
    public void deleteCardById(int id) {
        removeCard(id);
    }

    /**
     * Updates a card's question and answer by its ID.
     */
    public void updateCard(Integer id, String question, String answer) {
        Card card = cards.get(id);
        if (card != null) {
            card.edit(question, answer);
        }
    }

    /**
     * Searches for cards containing a specific term in their question or answer.
     */
    public List<String> searchInCards(String search) {
        List<String> responseCards = new ArrayList<>();
        for (int id : cards.keySet()) {
            Card card = cards.get(id);
            if (card.getQuestion().contains(search) || card.getAnswer().contains(search)) {
                responseCards.add(card.format(id));
            }
        }
        return responseCards;
    }

    /**
     * Returns a string representation of all cards.
     */
    public String getCardsAsString() {
        StringBuilder response = new StringBuilder();
        for (Map.Entry<Integer, Card> entry : cards.entrySet()) {
            Integer key = entry.getKey();
            Card card = entry.getValue();
            response.append("[id: ").append(key)
                    .append("] Question: ").append(card.getQuestion())
                    .append(", Answer: ").append(card.getAnswer()).append("\n");
        }
        return response.toString().isEmpty() ? "No cards" : response.toString();
    }
}
