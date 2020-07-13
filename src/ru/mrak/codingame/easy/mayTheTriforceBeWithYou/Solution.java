package ru.mrak.codingame.easy.mayTheTriforceBeWithYou;

import java.util.*;

class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();

        char[][] place = new char[N * 4 - 1][N * 2];
        for (char[] col: place) {
            Arrays.fill(col, ' ');
        }

        triforce(N * 2 - 1, 0, N, place);
        triforce(N - 1, N, N, place);
        triforce(N * 3 - 1, N, N, place);

        place[0][0] = '.';

        for (int i = 0; i < place[0].length; i++) {
            for (int j = 0; j < N * 2 + i; j++) {
                System.out.print(place[j][i]);
            }
            System.out.println();
        }
    }

    private static void triforce(int x, int y, int n, char[][] place) {
        for (int i = 0; i < n; i++) {
            for (int j = -i; j <= i; j++) {
                place[x + j][y + i] = '*';
            }
        }
    }
}
