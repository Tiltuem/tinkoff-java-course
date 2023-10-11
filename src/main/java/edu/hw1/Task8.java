package edu.hw1;

import java.util.Arrays;

public class Task8 {
    private static final int DIMENSION = 8;
    private static final int MAX_STEP = 2;
    private static final int MOVING_DISTANCE = 3;

    private Task8() {

    }

    public static boolean knightBoardCapture(int[][] board) {
        boolean isInvalidBoard = Arrays.stream(board)
            .flatMapToInt(Arrays::stream)
            .anyMatch(n -> n != 0 && n != 1);

        if (board.length != DIMENSION || board[0].length != DIMENSION || isInvalidBoard) {
            return false;
        }

        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                if (board[i][j] == 1) {
                    if (!isKnightsSafe(i, j, board)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public static boolean isKnightsSafe(int x, int y, int[][] board) {
        for (int i = -MAX_STEP; i <= MAX_STEP; i++) {
            for (int j = -MAX_STEP; j <= MAX_STEP; j++) {
                if (Math.abs(i) + Math.abs(j) == MOVING_DISTANCE
                    && x + i >= 0 && x + i < DIMENSION
                    && y + j >= 0 && y + j < DIMENSION) {

                    if (board[x + i][y + j] == 1) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

}
