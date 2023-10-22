package edu.project1.hangmanGame;

import edu.project1.dictionary.Dictionary;
import edu.project1.dictionary.DictionaryImpl;
import edu.project1.guessResult.GuessResult;
import edu.project1.guessResult.GuessResultImpl;
import edu.project1.guessResult.StagesOfHangman;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HangmanGameImpl implements HangmanGame {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int MISTAKES_MAX = 6;
    private static final int ZERO_MISTAKES = 0;
    private static final int ONE_MISTAKES = 1;
    private static final int TWO_MISTAKES = 2;
    private static final int THREE_MISTAKES = 3;
    private static final int FOUR_MISTAKES = 4;
    private static final int FIVE_MISTAKES = 5;
    private static final int SIX_MISTAKES = 6;
    private static final HashMap<Integer, StagesOfHangman> STAGES = new HashMap<>() {{
        put(ZERO_MISTAKES, StagesOfHangman.ZERO);
        put(ONE_MISTAKES, StagesOfHangman.ONE);
        put(TWO_MISTAKES, StagesOfHangman.TWO);
        put(THREE_MISTAKES, StagesOfHangman.THREE);
        put(FOUR_MISTAKES, StagesOfHangman.FOUR);
        put(FIVE_MISTAKES, StagesOfHangman.FIVE);
        put(SIX_MISTAKES, StagesOfHangman.SIX);
    }};

    private final Dictionary dictionary;
    private final GuessResult guessResult;

    private String secretWord;
    private StringBuilder maskedWord;
    private int mistakesCounter;
    private HashSet<Character> enteredLetters;
    private boolean isGameOver;
    private boolean isPlayerWin;
    private final Scanner inputLatter = new Scanner(System.in);

    public HangmanGameImpl() {
        this.dictionary = new DictionaryImpl();
        this.guessResult = new GuessResultImpl();
    }

    @Override
    public void initGame() {
        setSecretWord(dictionary.randomWord());
        setMaskedWord(new StringBuilder("_".repeat(secretWord.length())));
        setMistakesCounter(0);
        setGameOver(false);
    }

    @Override
    public void playGame() {
        initGame();

        enteredLetters = new HashSet<>();


        LOGGER.info("Начало игры.\nДля того чтобы сдаться, введите -1\nСлово - " + maskedWord);
        LOGGER.info(STAGES.get(0).toString());

        while (!isGameOver) {
            String guess = askLetter();

            if (guess.equals("-1")) {
                giveUp();
                isGameOver = true;
            }

            if (!isGameOver && validate(guess)) {
                enteredLetters.add(guess.charAt(0));
                int indexLatter = secretWord.indexOf(guess);

                if (indexLatter != -1) {

                    for (int i = indexLatter; i < secretWord.length(); i++) {
                        if (secretWord.charAt(i) == guess.charAt(0)) {
                            maskedWord.setCharAt(i, guess.charAt(0));
                        }
                    }

                    guessResult.successfulGuess(maskedWord.toString(), STAGES.get(mistakesCounter));
                } else {
                    mistakesCounter++;
                    guessResult.failedGuess(maskedWord.toString(), STAGES.get(mistakesCounter));
                }
            }

            if (isOver()) {
                isGameOver = true;
                endGame();
            }
        }
    }


    @Override
    public void giveUp() {
        guessResult.defeat(secretWord);
    }

    @Override
    public void endGame() {
        if (mistakesCounter == MISTAKES_MAX) {
            isPlayerWin = false;
            guessResult.defeat(secretWord);
        } else {
            isPlayerWin = true;
            guessResult.won();
        }
    }

    @Override
    public String askLetter() {
        LOGGER.info("Введите букву: ");
        return inputLatter.next().toLowerCase();
    }

    @Override
    public Boolean validate(String enter) {
        if (!enter.matches("[а-яА-Я]{1}")) {
            LOGGER.info("Некорректный ввод.");

            return false;
        } else if (enteredLetters.contains(enter.charAt(0))) {
            LOGGER.info("Вы уже вводили эту букву.");

            return false;
        }

        return true;
    }

    @Override
    public Boolean isOver() {
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

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public boolean isPlayerWin() {
        return isPlayerWin;
    }
}
