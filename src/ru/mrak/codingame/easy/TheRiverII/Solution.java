package ru.mrak.codingame.easy.TheRiverII;

import java.util.*;

class Solution {
    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        int r1 = in.nextInt();
        int meet = 0;
        for (long i = Long.max(0, r1 - String.valueOf(r1).length() * 9); i < r1; i++) {
            if(r1 == nextVal(i)) meet++;
        }
        System.out.println(meet > 0 ? "YES" : "NO");
    }
    private static long nextVal(long val) {
        return String.valueOf(val).chars().map(v -> Integer.parseInt((char)v + "")).sum() + val;
    }
}
