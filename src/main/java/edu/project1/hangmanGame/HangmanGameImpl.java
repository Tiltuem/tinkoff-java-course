package edu.project1.hangmanGame;

import edu.project1.dictionary.Dictionary;
import edu.project1.dictionary.DictionaryImpl;
import edu.project1.guessResult.GuessResult;
import edu.project1.guessResult.GuessResultImpl;
import edu.project1.guessResult.StagesOfHangman;
import java.util.HashSet;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class HangmanGameImpl implements HangmanGame {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int MISTAKES_MAX = 6;
    private final Scanner inputLatter = new Scanner(System.in);

    private final Dictionary dictionary;
    private final GuessResult guessResult;

    private String secretWord;
    private StringBuilder maskedWord;
    private int mistakesCounter;
    private HashSet<Character> enteredLetters;
    private boolean isPlayerWin;

    public HangmanGameImpl() {
        this.dictionary = new DictionaryImpl();
        this.guessResult = new GuessResultImpl();
    }

    @Override
    public void initGame() {
        setSecretWord(dictionary.randomWord());
        setMaskedWord(new StringBuilder("_".repeat(secretWord.length())));
        setMistakesCounter(0);
    }

    @Override
    public void playGame() {
        initGame();

        enteredLetters = new HashSet<>();

        LOGGER.info("Начало игры.\nДля того чтобы сдаться, введите -1\nСлово - " + maskedWord);
        LOGGER.info(StagesOfHangman.values()[0].toString());

        while (true) {
            String guess = askLetter();

            if (guess.equals("-1")) {
                isPlayerWin = false;
                giveUp();
                break;
            }

            if (validate(guess)) {
                enteredLetters.add(guess.charAt(0));
                int indexLatter = secretWord.indexOf(guess);

                if (indexLatter != -1) {

                    for (int i = indexLatter; i < secretWord.length(); i++) {
                        if (secretWord.charAt(i) == guess.charAt(0)) {
                            maskedWord.setCharAt(i, guess.charAt(0));
                        }
                    }

                    guessResult.successfulGuess(maskedWord.toString(), StagesOfHangman.values()[mistakesCounter]);
                } else {
                    mistakesCounter++;
                    guessResult.failedGuess(maskedWord.toString(), StagesOfHangman.values()[mistakesCounter]);
                }
            }

            if (isOver()) {
                endGame();
                break;
            }
        }
    }

    @Override
    public void giveUp() {
        guessResult.defeat(secretWord);
    }

    @Override
    public void endGame() {
        isPlayerWin = mistakesCounter != MISTAKES_MAX;

        if (isPlayerWin) {
            guessResult.won();
        } else {
            guessResult.defeat(secretWord);
        }
    }

    @Override
    public @NotNull String askLetter() {
        LOGGER.info("Введите букву: ");
        return inputLatter.next().toLowerCase();
    }

    @Override
    public @NotNull Boolean validate(String enter) {
        if (!enter.matches("[а-яА-Я]")) {
            LOGGER.info("Некорректный ввод.");

            return false;
        } else if (enteredLetters.contains(enter.charAt(0))) {
            LOGGER.info("Вы уже вводили эту букву.");

            return false;
        }

        return true;
    }

    @Override
    public @NotNull Boolean isOver() {
        return mistakesCounter == MISTAKES_MAX || secretWord.contentEquals(maskedWord);
    }

    public String getSecretWord() {
        return secretWord;
    }

    public void setSecretWord(String secretWord) {
        this.secretWord = secretWord;
    }

    public StringBuilder getMaskedWord() {
        return maskedWord;
    }

    public void setMaskedWord(StringBuilder maskedWord) {
        this.maskedWord = maskedWord;
    }

    public int getMistakesCounter() {
        return mistakesCounter;
    }

    public void setMistakesCounter(int mistakesCounter) {
        this.mistakesCounter = mistakesCounter;
    }

    public HashSet<Character> getEnteredLetters() {
        return enteredLetters;
    }

    public void setEnteredLetters(HashSet<Character> enteredLetters) {
        this.enteredLetters = enteredLetters;
    }

    public boolean isPlayerWin() {
        return isPlayerWin;
    }
}
