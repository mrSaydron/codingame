package ru.mrak.codewars.kyu4.snail;

import java.util.Arrays;

public class Snail {
    
    public static int[] snail(int[][] array) {
        //System.out.println(Arrays.toString(array));
        int startX = 0;
        int endX = array[0].length - 1;
        int startY = 0;
        int endY = array.length - 1;
        
        int[] result = new int[(endX + 1) * (endY + 1)];
        int resultIndex = 0;
        while (true) {
            for (int x = startX; x <= endX; x++) {
                result[resultIndex] = array[startY][x];
                resultIndex++;
            }
            startY++;
            if (startX > endX && startY > endY) break;
            for (int y = startY; y <= endY; y++) {
                result[resultIndex] = array[y][endX];
                resultIndex++;
            }
            endX--;
            if (startX > endX && startY > endY) break;
            for (int x = endX; x >= startX; x--) {
                result[resultIndex] = array[endY][x];
                resultIndex++;
            }
            endY--;
            if (startX > endX && startY > endY) break;
            for (int y = endY; y >= startY; y--) {
                result[resultIndex] = array[y][startX];
                resultIndex++;
            }
            startX++;
            if (startX > endX && startY > endY) break;
        }
        
        System.out.println("Result");
        System.out.println(Arrays.toString(result));
        return result;
    }
}