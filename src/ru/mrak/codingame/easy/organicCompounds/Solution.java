package ru.mrak.codingame.easy.organicCompounds;

import java.util.*;

class Solution {
    private static final int CHARS_ON_ELEMENT = 3;
    private static final int CARBON_BOUNDS = 4;

    private static List<List<String>> table;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        table = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            String COMPOUND = in.nextLine();
            System.err.println(COMPOUND);
            List<String> row = new ArrayList<>(COMPOUND.length() / CHARS_ON_ELEMENT);
            for (int j = 0; j < COMPOUND.length() / CHARS_ON_ELEMENT; j++) {
                row.add(COMPOUND.substring(j * CHARS_ON_ELEMENT, (j + 1) * CHARS_ON_ELEMENT));
            }
            table.add(row);
        }

        for (int i = 0; i < table.size(); i += 2) {
            for (int j = 0; j < table.get(i).size(); j += 2) {
                if(getElement(i, j) != 0 && getBoundsCarbon(getElement(i, j)) != summBound(i, j)) {
                    System.out.println("INVALID");
                    return;
                }
            }
        }

        System.out.println("VALID");
    }

    private static int getElement(int i, int j) {
        if(i >= 0
                && i < table.size()
                && j >= 0
                && j < table.get(i).size()) {
            String cell = table.get(i).get(j);
            if (cell.contains("CH")) {
                return Integer.parseInt(cell.substring(2));
            } else if(cell.equals("   ")) {
                return 0;
            } else {
                return Integer.parseInt(cell.substring(1, 2));
            }
        }
        return 0;
    }

    private static int summBound(int i, int j) {
        return getElement(i - 1, j) + getElement(i + 1, j) + getElement(i, j - 1) +getElement(i, j + 1);
    }

    private static int getBoundsCarbon(int element) {
        int bounds = CARBON_BOUNDS - element;
        if(bounds < 0) throw new RuntimeException();
        return bounds;
    }
}
