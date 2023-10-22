package edu.project1.hangmanGame;

public interface HangmanGame {
    void initGame();

    void playGame();

    String askLetter();

    Boolean validate(String enter);

    Boolean isOver();

    void giveUp();

    void endGame();
}
