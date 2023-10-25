package edu.project1.hangmanGame;

import org.jetbrains.annotations.NotNull;

public interface HangmanGame {
    void initGame();

    void playGame();

    @NotNull String askLetter();

    @NotNull Boolean validate(String enter);

    @NotNull Boolean isOver();

    void giveUp();

    void endGame();
}
