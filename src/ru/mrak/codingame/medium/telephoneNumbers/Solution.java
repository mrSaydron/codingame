package ru.mrak.codingame.medium.telephoneNumbers;

import java.util.*;

public class Solution {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        System.err.println(N);
        Map tree = new HashMap();
        int count = 0;
        for (int i = 0; i < N; i++) {
            String telephone = in.next();
            System.err.println(telephone);
            Map currentElement = tree;
            for (Character c: telephone.toCharArray()) {
                if(!currentElement.containsKey(c)) {
                    count++;
                    currentElement.put(c, new HashMap<>());
                    currentElement = (Map)currentElement.get(c);
                } else {
                    currentElement = (Map)currentElement.get(c);
                }
            }
        }

        System.out.println(count);
    }

}
