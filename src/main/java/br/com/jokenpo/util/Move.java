package br.com.jokenpo.util;

public enum Move {
    ROCK(0),

    SCISSORS(1),

    PAPER(2);

    private int id;
    private String description;

    Move(int id) {
        this.id = id;
        if (id == 0) {
            description = "Rock";
        }
        if (id == 1) {
            description = "Scissors";
        }
        if (id == 2) {
            description = "Paper";
        }
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
