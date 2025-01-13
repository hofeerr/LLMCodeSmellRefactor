package org.example.studycards;

public class Card {
    private String question;
    private String answer;

    public Card(String question, String answer) {
        if (!isValid(question, answer)) {
            throw new IllegalArgumentException("Invalid question or answer");
        }
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        if (!isValid(question, this.answer)) {
            throw new IllegalArgumentException("Invalid question");
        }
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        if (!isValid(this.question, answer)) {
            throw new IllegalArgumentException("Invalid answer");
        }
        this.answer = answer;
    }

    public void edit(String question, String answer) {
        if (!isValid(question, answer)) {
            throw new IllegalArgumentException("Invalid question or answer");
        }
        this.question = question;
        this.answer = answer;
    }

    public String format(int id) {
        return "[id: " + id + "] Question: " + question + " Answer: " + answer;
    }

    private boolean isValid(String question, String answer) {
        return question != null && !question.isEmpty() && answer != null && !answer.isEmpty();
    }
}
