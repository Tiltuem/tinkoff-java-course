package edu.project1.guessResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuessResultImpl implements GuessResult {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void won() {
        LOGGER.info("Поздравляем! Вы выиграли!");
    }

    @Override
    public void defeat(String secretWord) {
        LOGGER.info("Вы проиграли! Правильное слово - " + secretWord);
    }

    @Override
    public void successfulGuess(String maskedWord, StagesOfHangman stages) {
        LOGGER.info(stages.toString());
        LOGGER.info("Есть попадание!\nСлово - " + maskedWord);
    }

    @Override
    public void failedGuess(String maskedWord, StagesOfHangman stages) {
        LOGGER.info(stages.toString());
        LOGGER.info("Буква отсутствует\nСлово - " + maskedWord);
    }
}
