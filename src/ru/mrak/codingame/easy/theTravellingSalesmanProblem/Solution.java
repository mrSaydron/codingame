package ru.mrak.codingame.easy.theTravellingSalesmanProblem;

import java.util.*;

class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        List<Point> points = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            int X = in.nextInt();
            int Y = in.nextInt();
            points.add(new Point(X, Y));
        }

        List<Double> result = new ArrayList<>(N);
        Point startPoint = points.get(0);
        for (int i = 0; i < N - 1; i++) {
            Point nextPoint = points.get(0);
            points.remove(0);
            points.sort(Comparator.comparing(p -> p.distance(nextPoint)));
            result.add(nextPoint.distance(points.get(0)));
        }
        result.add(points.get(0).distance(startPoint));

        System.out.println(Math.round(result.stream().reduce((d1, d2) -> d1 + d2).get()));
    }

    private static class Point {
        double x;
        double y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        double distance(Point point) {
            return Math.sqrt(Math.pow(x - point.x, 2) + Math.pow(y - point.y, 2));
        }
    }
}
