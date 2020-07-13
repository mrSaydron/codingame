package ru.mrak.codingame.easy.bankRobbers;

import java.util.*;
import java.util.stream.Collectors;

class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int R = in.nextInt();
        int V = in.nextInt();
        List<Long> vs = new ArrayList<>(V);
        List<Long> rs = new ArrayList<>(R);

        System.err.println("R: " + R);
        System.err.println("V: " + V);

        for (int i = 0; i < V; i++) {
            int C = in.nextInt();
            int N = in.nextInt();
            vs.add(complexity(C, N));

            System.err.println("C: " + C);
            System.err.println("N: " + N);
        }

        long summ = 0;
        while(vs.size() > 0) {
            if(rs.size() < R) {
                rs.add(vs.get(0));
                vs.remove(0);
            } else {
                rs.sort(Long::compareTo);
                long min = rs.get(0);
                summ += min;
                rs = rs.stream().map(r -> r - min).collect(Collectors.toList());
                rs.remove(0);
            }
        }
        if(rs.size() > 0) {
            rs.sort(Long::compareTo);
            summ += rs.get(rs.size() - 1);
        }

        System.out.println(summ);
    }

    private static long complexity(int C, int N) {
        return (long) (Math.pow(10, N) * Math.pow(5, C - N));
    }
}
