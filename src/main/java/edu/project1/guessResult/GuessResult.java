package edu.project1.guessResult;

public interface GuessResult {
    void won();

    void defeat(String secretWord);

    void successfulGuess(String maskedWord, StagesOfHangman stages);

    void failedGuess(String maskedWord, StagesOfHangman stages);
}
