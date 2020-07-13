package ru.mrak.codingame.medium.networkCabling;

import java.util.Arrays;
import java.util.Scanner;

public class Solution {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        long[] X = new long[N];
        long[] Y = new long[N];
        for (int i = 0; i < N; i++) {
            X[i] = in.nextLong();
            Y[i] = in.nextLong();
        }

        long maxX = X[0];
        long minX = X[0];
        for (int i = 1; i < N; i++) {
            if (X[i] > maxX) maxX = X[i];
            if (X[i] < minX) minX = X[i];
        }
        Arrays.sort(Y);
        long line = Y[N / 2];
        long length = (maxX - minX) + Arrays.stream(Y).map(y -> Math.abs(line - y)).sum();

        System.out.println(length);
    }
}
