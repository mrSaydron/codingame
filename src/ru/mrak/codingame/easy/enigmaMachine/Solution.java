package ru.mrak.codingame.easy.enigmaMachine;

import java.util.*;

class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        String operation = in.nextLine();
        int pseudoRandomNumber = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }

        char[] encodeRotor = in.nextLine().toCharArray();
        char[] decodeRotor = comDecodeRotor(encodeRotor);
        for (int i = 0; i < 2; i++) {
            encodeRotor = comEncodeRotor(decodeRotor, in.nextLine().toCharArray());
            decodeRotor = comDecodeRotor(encodeRotor);
        }
        String message = in.nextLine();

        if(operation.equals("ENCODE")) {
            System.out.println(encodeString(message, pseudoRandomNumber, encodeRotor));
        } else {
            System.out.println(decodeString(message, pseudoRandomNumber, decodeRotor));
        }
    }

    private static char[] comDecodeRotor(char[] encodeRotor) {
        char[] decodeRotor = new char[encodeRotor.length];
        for (int i = 0; i < encodeRotor.length; i++) {
            decodeRotor[encodeRotor[i] - 'A'] = (char)(i + 'A');
        }
        return decodeRotor;
    }

    private static char[] comEncodeRotor(char[] decodeRotor, char[] newRotor) {
        char[] encodeRotor = new char[decodeRotor.length];
        for (int i = 0; i < newRotor.length; i++) {
            encodeRotor[decodeRotor[i] - 'A'] = newRotor[i];
        }
        return encodeRotor;
    }

    private static char encodeChar(char baseChar, char[] encodeRotor) {
        return (char)(encodeRotor[baseChar - 'A']);
    }

    private static char decodeChar(char baseChar, char[] decodeRotor) {
        return (char)(decodeRotor[baseChar - 'A']);
    }

    private static String encodeString(String text, int increment, char[] encodeRotor) {
        char[] resText = new char[text.length()];
        char[] charText = text.toCharArray();
        for (int i = 0; i < charText.length; i++) {
            if(charText[i] >= 'A' && charText[i] <= 'Z') {
                resText[i] = encodeChar((char) (charText[i] + increment % 32), encodeRotor);
                increment++;
            } else {
                resText[i] = charText[i];
            }
        }
        return String.valueOf(resText);
    }

    private static String decodeString(String text, int increment, char[] decodeRotor) {
        char[] resText = new char[text.length()];
        char[] charText = text.toCharArray();
        for (int i = 0; i < charText.length; i++) {
            if(charText[i] >= 'A' && charText[i] <= 'Z') {
                resText[i] = (char) (decodeChar(charText[i], decodeRotor) - increment % 32);
                increment++;
            } else {
                resText[i] = charText[i];
            }
        }
        return String.valueOf(resText);
    }
}
