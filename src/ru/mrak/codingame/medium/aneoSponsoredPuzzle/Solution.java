package ru.mrak.codingame.medium.aneoSponsoredPuzzle;

import java.util.*;

class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int maxSpeed = in.nextInt();
        System.err.println(maxSpeed);
        int lightCount = in.nextInt();
        System.err.println(lightCount);
        int[] distance = new int[lightCount];
        int[] duration = new int[lightCount];
        for (int i = 0; i < lightCount; i++) {
            distance[i] = in.nextInt();
            duration[i] = in.nextInt();
            System.err.println(distance[i] + " " + duration[i]);
        }

        for (int i = maxSpeed; i > 0; i--) {
            for (int j = 0; j < lightCount; j++) {
                if (((int) (distance[j] * 3.6 / (double)i / (double)duration[j])) % 2 == 1) break;
                if(j == lightCount - 1) {
                    System.out.println(i);
                    return;
                }
            }
        }

        System.out.println("error");
    }

}
