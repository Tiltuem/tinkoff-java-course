package edu.project1;

import edu.project1.hangmanGame.HangmanGameImpl;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConsoleHangman {
    private static final Logger LOGGER = LogManager.getLogger();

    public void startGame() {
        boolean hasGameStarted = true;

        LOGGER.info("Добро пожаловать!");

        try (Scanner input = new Scanner(System.in)) {
            while (hasGameStarted) {
                LOGGER.info("""
                    Меню игры
                    ========================
                    1. Новая игра
                    2. Выход
                    ========================
                    """);

                switch (input.nextLine().charAt(0)) {
                    case ('1') -> new HangmanGameImpl().playGame();
                    case ('2') -> {
                        LOGGER.info("Игра окончена");
                        hasGameStarted = false;
                    }
                    default -> LOGGER.info("Неверный ввод. Попробуйте ещё раз: ");
                }
            }
        }
    }
}
