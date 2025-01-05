package org.example.studycards;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Box {
    private List<Integer> cards;
    public Box() {
        cards = new ArrayList<>();
    }
    public Box(List<Integer> cards) {
        this.cards = cards;
    }
    @Override
    public String toString() {
        CardManager manager = CardManager.getCardManager();
        StringBuilder response = new StringBuilder();
        for (Integer cardId : cards) { // Presumindo que 'cards' seja uma coleção de IDs de cartões
            Card card = manager.getCard(cardId); // Obter a instância do cartão
            if (card != null) {
                response.append(card.format(cardId)).append(System.lineSeparator()); // Usar o método 'format' do Card
            } else {
                response.append("[id: ").append(cardId).append("] Card not found").append(System.lineSeparator());
            }
        }
        return response.toString();
    }

    public List<Integer> getCards() {
        return cards;
    }

    public boolean hasCard(int cardId) {
        return !cards.contains(cardId);
    }

    public void setCards(List<Integer> cards) {
        this.cards = cards;
    }
    public void removeCard (Integer id) {
        cards.remove(id);
    }
    public void addCard (Integer id) {
        cards.add(id);
    }

    public void addCards (List<Integer> cards) {
        this.cards.addAll(cards);
    }

    public Integer getRandomCard(){
        Random random = new Random();
        if (cards.isEmpty()) {
            return null;
        }
        int randomIndex = random.nextInt(cards.size());
        return cards.get(randomIndex);
    }

    public boolean isEmpty() {
        return cards.isEmpty(); // Retorna true se não houver cartões na caixa
    }

}
