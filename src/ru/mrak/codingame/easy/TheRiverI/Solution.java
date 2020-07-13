package ru.mrak.codingame.easy.TheRiverI;

import java.util.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        long r1 = in.nextLong();
        long r2 = in.nextLong();

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        while (r1 != r2) {
            if(r1 < r2) {
                r1 = nextVal(r1);
            } else {
                r2 = nextVal(r2);
            }
        }

        System.out.println(r1);
    }

    private static long nextVal(long val) {
        for (char c : String.valueOf(val).toCharArray()) {
            val += Integer.parseInt(c + "");
        }
        return val;
    }
}