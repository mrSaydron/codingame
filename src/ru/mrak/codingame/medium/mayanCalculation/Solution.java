package ru.mrak.codingame.medium.mayanCalculation;

import java.util.*;

public class Solution {
    private static int L;
    private static int H;
    private static Map<String, Integer> signToNumber;
    private static Map<Integer, String> numberToSign;
    private static Scanner in;

    public static void main(String args[]) {
        in = new Scanner(System.in);
        L = in.nextInt();
        H = in.nextInt();
        System.err.println(L + " " + H);
        String[] numeralLine = new String[H];
        for (int i = 0; i < H; i++) {
            numeralLine[i] = in.next();
            System.err.println(numeralLine[i]);
        }

        //Связываю знаки с числами
        signToNumber = new HashMap<>();
        numberToSign = new HashMap<>();
        for (int i = 0; i < 20; i++) {
            List<String> pieceNumber = new ArrayList<>(H);
            for (int j = 0; j < H; j++) {
                pieceNumber.add(numeralLine[j].substring(i * L, (i + 1) * L));
            }
            String sign = String.join("\n", pieceNumber);
            numberToSign.put(i, sign);
            signToNumber.put(sign, i);
        }

        long ferstNumber = readNumber();
        long secondtNumber = readNumber();

        String operation = in.next();
        System.err.println(operation);

        System.err.println();
        System.err.println("sirst: " + ferstNumber);
        System.err.println("second: " + secondtNumber);

        long resultNumber = 0;
        switch (operation) {
            case "+":
                resultNumber = ferstNumber + secondtNumber;
                break;
            case "-":
                resultNumber = ferstNumber - secondtNumber;
                break;
            case "*":
                resultNumber = ferstNumber * secondtNumber;
                break;
            case "/":
                resultNumber = ferstNumber / secondtNumber;
                break;
        }

        long upNumber = resultNumber;
        int downNumber = 0;
        List<String> resultString = new ArrayList<>();
        do {
            downNumber = (int)(upNumber % 20);
            upNumber = upNumber / 20;
            resultString.add(0, numberToSign.get(downNumber));
        } while (upNumber > 0);

        System.out.println(String.join("\n", resultString));
    }

    private static long readNumber() {
        int S = in.nextInt();
        System.err.println(S);
        long number = 0;
        for (int i = S / H - 1; i >= 0; i--) {
            List<String> pieceNumber = new ArrayList<>(H);
            for (int j = 0; j < H; j++) {
                String numLine = in.next();
                System.err.println(numLine);
                pieceNumber.add(numLine);
            }
            String sign = String.join("\n", pieceNumber);
            number += signToNumber.get(sign) * (long)Math.pow(20, i);
        }
        return number;
    }
}
