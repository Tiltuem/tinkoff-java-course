package edu.project1;

import edu.project1.dictionary.Dictionary;
import edu.project1.dictionary.DictionaryImpl;
import edu.project1.hangmanGame.HangmanGameImpl;
import java.util.HashSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Project1Test {
    @Test
    @DisplayName("testInitGame")
    public void testInitGame() {
        HangmanGameImpl game = new HangmanGameImpl();
        game.initGame();

        assertNotNull(game.getSecretWord());
        assertFalse(game.getSecretWord().isEmpty());
        assertEquals(game.getMaskedWord().toString(), "_".repeat(game.getSecretWord().length()));
        assertEquals(game.getMistakesCounter(), 0);
        assertNull(game.getEnteredLetters());
        assertFalse(game.isGameOver());
    }

    @Test
    @DisplayName("testValidate")
    public void testValidate() {
        HangmanGameImpl game = new HangmanGameImpl();
        HashSet<Character> enteredLetters = new HashSet<>();
        enteredLetters.add('а');
        game.setEnteredLetters(enteredLetters);

        String input1 = "б";
        assertTrue(game.validate(input1));

        String input2 = "1";
        assertFalse(game.validate(input2));

        String input3 = "а";
        assertFalse(game.validate(input3));

        String input4 = "аб";
        assertFalse(game.validate(input4));
    }

    @Test
    @DisplayName("testIsOver")
    public void testIsOver() {
        HangmanGameImpl game = new HangmanGameImpl();

        game.setMistakesCounter(6);
        game.setSecretWord("тест");
        game.endGame();
        assertFalse(game.isPlayerWin());

        game.setMistakesCounter(0);
        game.setMaskedWord(new StringBuilder("тест"));
        game.endGame();
        assertTrue(game.isPlayerWin());
    }

    @Test
    @DisplayName("testRandomWord")
    public void testRandomWord() {
        Dictionary dictionary = new DictionaryImpl();
        assertNotNull(dictionary.randomWord());
    }
}
