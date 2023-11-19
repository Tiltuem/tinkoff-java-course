package edu.project2;

import edu.project2.models.Maze;
import edu.project2.prettyPrint.PrettyPrint;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConsoleMaze {
    private static final Logger LOGGER = LogManager.getLogger();

    public void menu() {
        boolean hasStarted = true;

        LOGGER.info("Добро пожаловать!");

        try (Scanner input = new Scanner(System.in)) {
            while (hasStarted) {
                LOGGER.info("""
                    Меню игры
                    ========================
                    1. Новый лабиринт
                    2. Выход
                    ========================
                    """);

                switch (input.next().charAt(0)) {
                    case ('1') -> {
                        LOGGER.info("Введите длину: ");
                        int height = Integer.parseInt(input.next());
                        LOGGER.info("Введите ширину: ");
                        int width = Integer.parseInt(input.next());

                        Maze maze = new Maze(height, width);
                        PrettyPrint.printMaze(maze.getGrid());
                        LOGGER.info("Решение:");
                        maze.findEscape();
                    }
                    case ('2') -> {
                        LOGGER.info("Выход");
                        hasStarted = false;
                    }
                    default -> LOGGER.info("Неверный ввод. Попробуйте ещё раз: ");
                }
            }
        }
    }
}
