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

    public Map<Integer, Card> getCardsMap() {
        return cards;
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards.values());
    }

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

    public Card getCard(Integer id) {
        return cards.get(id);
    }

    public Integer addCard(String question, String answer) {
        Card card = new Card(question, answer);
        Integer response = nextID;
        cards.put(nextID, card);
        nextID++;
        return response;
    }

    public void removeCard(Integer id) {
        cards.remove(id);
    }

    public void updateCard(Integer id, String question, String answer) {
        Card card = cards.get(id);
        if (card != null) {
            card.edit(question, answer);
        }
    }

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
}
