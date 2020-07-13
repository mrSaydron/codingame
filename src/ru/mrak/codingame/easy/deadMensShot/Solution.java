package ru.mrak.codingame.easy.deadMensShot;

import java.util.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Solution {

    public static void main(String args[]) {
        class Point {
            private int x;
            private int y;

            private Point(int x, int y) {
                this.x = x;
                this.y = y;
            }

            @Override
            public String toString() {
                return "Point{" +
                        "x=" + x +
                        ", y=" + y +
                        '}';
            }
        }

        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        List<Point> points = new ArrayList<>(N + 1);
        for (int i = 0; i < N; i++) {
            points.add(new Point(in.nextInt(), in.nextInt()));
        }
        points.add(points.get(0));
        System.err.println(points);

        int M = in.nextInt();
        for (int i = 0; i < M; i++) {
            Point shoot = new Point(in.nextInt(), in.nextInt());
            String resilt = "hit";
            for (int j = 0; j < points.size() - 1; j++) {
                Point p = new Point(shoot.x - points.get(j).x, shoot.y - points.get(j).y);
                Point v = new Point(points.get(j + 1).x - points.get(j).x, points.get(j + 1).y - points.get(j).y);
                System.err.println(p);
                System.err.println(v);
                double side = p.x * v.y - p.y * v.x;
                if(side < 0) {
                    resilt = "miss";
                    break;
                }
            }
            System.out.println(resilt);
        }
    }
}