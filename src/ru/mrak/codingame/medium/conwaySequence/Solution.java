package ru.mrak.codingame.medium.conwaySequence;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int R = in.nextInt();
        System.err.println(R);
        int L = in.nextInt();
        System.err.println(L);

        List<Integer> line = new ArrayList<>();
        line.add(R);
        for (int i = 0; i < L - 1; i++) {
            line = nextLine(line);
            System.err.println(String.join(" ", line.stream().map(Object::toString).toArray(String[]::new)));
        }

        System.out.println(String.join(" ", line.stream().map(Object::toString).toArray(String[]::new)));
    }

    private static List<Integer> nextLine(List<Integer> line) {
        List<Integer> result = new ArrayList<>();
        Integer current = null;
        int count = 0;
        for (int c : line) {
            if (count == 0) {
                current = c;
                count++;
            } else {
                if (current == c) {
                    count++;
                } else {
                    result.add(count);
                    result.add(current);
                    current = c;
                    count = 1;
                }
            }
        }
        result.add(count);
        result.add(current);
        return result;
    }
}
