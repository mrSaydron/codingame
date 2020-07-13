package ru.mrak.codingame.easy.Gravity;

import java.util.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int width = in.nextInt();
        int height = in.nextInt();
        int count = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        List<List<Character>> table = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            String raster = in.nextLine();
            List<Character> lc = new ArrayList<>();
            for (char c: raster.toCharArray())
                lc.add(c);
            table.add(lc);
        }

        for (int i = 0; i < count; i++) {
            table = revert(table);
            for (int j = 0; j < table.size(); j++) {
                table.get(j).sort(Collections.reverseOrder());
            }
        }

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        for(List<Character> row: table) {
            for(Character c: row)
                System.out.print(c);
            System.out.println("");
        }
    }

    private static List<List<Character>> revert(List<List<Character>> table) {
        List<List<Character>> result = new ArrayList<>();
        for (int i = 0; i < table.size(); i++) {
            List<Character> row = new ArrayList<>();
            for (int j = 0; j < table.get(0).size(); j++)
                row.add(table.get(j).get(i));
            result.add(row);
        }
        return result;
    }
}