package ru.mrak.codingame.hard.surface;

import java.util.*;

class Solution {
    
    private static final char LAND = '#';
    private static final char WATER = 'O';
    
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int L = in.nextInt();
        int H = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        String[] field = new String[H];
        boolean[][] use = new boolean[H][];
        for (int i = 0; i < H; i++) {
            String row = in.nextLine();
            field[i] = row;
            use[i] = new boolean[L];
        }
        int N = in.nextInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int X = in.nextInt();
            int Y = in.nextInt();
            points[i] = new Point(Y, X);
        }
        
        for (int i = 0; i < N; i++) {
            initUseMas(use);
            Deque<Point> queue = new LinkedList<>();
            queue.addLast(points[i]);
            int laceSize = 0;
            while (queue.size() > 0) {
                Point first = queue.pollFirst();
                if (field[first.row].toCharArray()[first.col] == WATER && !use[first.row][first.col]) {
                    laceSize++;
                    use[first.row][first.col] = true;
                    //up
                    if (first.row > 0) queue.addLast(new Point(first.row - 1, first.col));
                    //down
                    if (first.row < H - 1) queue.addLast(new Point(first.row + 1, first.col));
                    //left
                    if (first.col > 0) queue.addLast(new Point(first.row, first.col - 1));
                    //right
                    if (first.col < L - 1) queue.addLast(new Point(first.row, first.col + 1));
                }
            }
            
            System.out.println(laceSize);
        }
    }
    
    private static void initUseMas(boolean[][] use) {
        for (int row = 0; row < use.length; row++) {
            for (int col = 0; col < use[row].length; col++) {
                use[row][col] = false;
            }
        }
    }
    
    private static class Point {
        int row;
        int col;
    
        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
