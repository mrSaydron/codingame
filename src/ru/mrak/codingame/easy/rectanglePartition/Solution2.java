package ru.mrak.codingame.easy.rectanglePartition;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Solution2 {
    
    public static void main(String args[]) {
        List<Integer> horizontal = new ArrayList<>();
        List<Integer> vertical = new ArrayList<>();
        
        Scanner in = new Scanner(System.in);
        int w = in.nextInt();
        int h = in.nextInt();
        int countX = in.nextInt();
        int countY = in.nextInt();
        
        horizontal.add(0);
        horizontal.add(w);
        vertical.add(0);
        vertical.add(h);
        
        for (int i = 0; i < countX; i++) {
            int x = in.nextInt();
            horizontal.add(x);
        }
        for (int i = 0; i < countY; i++) {
            int y = in.nextInt();
            vertical.add(y);
        }
        
        horizontal.sort(Integer::compareTo);
        vertical.sort(Integer::compareTo);
    
        int result = 0;
        for (int xi = 0; xi < horizontal.size() - 1; xi++) {
            for (int yi = 0; yi < vertical.size() - 1; yi++) {
                for (int xii = xi + 1; xii < horizontal.size(); xii++) {
                    for (int yii = yi + 1; yii < vertical.size(); yii++) {
                        int dx = Math.abs(horizontal.get(xi) - horizontal.get(xii));
                        int dy = Math.abs(vertical.get(yi) - vertical.get(yii));
                        if (dx == dy) result++;
                    }
                }
            }
        }
        
        System.out.println(result);
    }
}