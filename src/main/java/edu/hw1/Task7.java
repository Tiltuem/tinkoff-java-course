package edu.hw1;

public class Task7 {
    private Task7() {
    }

    public static int rotateLeft(int n, int shift) {
        return rotate(n, shift, true);
    }

    public static int rotateRight(int n, int shift) {
        return rotate(n, shift, false);
    }

    public static int rotate(int n, int shift, boolean direction) {
        if (n < 0 || shift < 0) {
            return -1;
        }

        String binaryNum = Integer.toBinaryString(n);
        StringBuilder result = new StringBuilder();
        int myShift = shift > binaryNum.length() ? shift % binaryNum.length() : shift;

        if (myShift == binaryNum.length() || myShift == 0 || n == 0) {
            return Integer.parseInt(binaryNum, 2);
        }

        int index = direction ? myShift : Integer.toBinaryString(n).length() - myShift;

        for (int i = index; i < binaryNum.length(); i++) {
            result.append(binaryNum.charAt(i));
        }
        for (int i = 0; i < index; i++) {
            result.append(binaryNum.charAt(i));
        }

        return Integer.parseInt(result.toString(), 2);
    }
}
