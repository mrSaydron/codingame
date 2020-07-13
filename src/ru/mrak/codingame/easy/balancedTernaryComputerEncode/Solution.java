package ru.mrak.codingame.easy.balancedTernaryComputerEncode;

import java.util.*;

class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        //int N = in.nextInt();
        int N = -15;
        System.err.println(N);

        int addNumber = Integer.parseInt("1111111111", 3);
        char[] ternaryNumberInChar = Integer.toString(N + addNumber, 3).toCharArray();
        StringBuilder balancedTernary = new StringBuilder();
        for (char c : ternaryNumberInChar) {
            balancedTernary.append(c == '0' ? 'T' : (c == '1' ? '0' : '1'));
        }
        String result = balancedTernary.toString().replaceFirst("^0*", "");
        System.out.println(result.isEmpty() ? "0" : result);
    }
}