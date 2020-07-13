package ru.mrak.codingame.medium.theGift;

import java.util.*;

public class Solution {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        System.err.println(N);
        long C = in.nextLong();
        System.err.println(C);
        List<Long> maxPay = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            long B = in.nextLong();
            maxPay.add(B);
            System.err.println(B);
        }

        List<Long> pay = new ArrayList<>();
        maxPay.sort(Long::compareTo);
        for (int i = 0; i < N; i++) {
            long average = C / (N - i);
            long currentPay = average >= maxPay.get(i) ? maxPay.get(i) : average;
            C -= currentPay;
            pay.add(currentPay);
        }
        if (C == 0) {
            pay.forEach(System.out::println);
        } else {
            System.out.println("IMPOSSIBLE");
        }
    }
}
