package ru.mrak.codingame.easy.rectanglePartition;

import java.util.*;

class Solution {
    
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int w = in.nextInt();
        System.err.println(w);
        int h = in.nextInt();
        int countX = in.nextInt();
        int countY = in.nextInt();
        //соберу прямоугольник
        Point endPoint = new Point(w, h, null, null);
        Point leftPoint = new Point(0, h, null, endPoint);
        Point rightPoint = new Point(w, 0, endPoint, null);
        Point rootPoint = new Point(0, 0, leftPoint, rightPoint);
        
        for (int i = 0; i < countX; i++) {
            int x = in.nextInt();
            cutVertical(x, rootPoint);
        }
        for (int i = 0; i < countY; i++) {
            int y = in.nextInt();
            cutHorizontal(y, rootPoint);
        }
    
        Set<Point> processedPoints = new HashSet<>();
        int result = rootPoint.findAllSquare(processedPoints);
    
        System.out.println(result);
    }
    
    static void cutVertical(int x, Point root) {
        List<Point> newPointList = new ArrayList<>();
        Set<Point> processedPoints = new HashSet<>();
        
        root.cutVertical(x, processedPoints, newPointList);
        
        Point previousPoint = null;
        for (int i = newPointList.size() - 1; i >= 0; i--) {
            Point point = newPointList.get(i);
            point.left = previousPoint;
            previousPoint = point;
        }
    }
    
    static void cutHorizontal(int y, Point root) {
        List<Point> newPointList = new ArrayList<>();
        Set<Point> processedPoints = new HashSet<>();
        
        root.cutHorizontal(y, processedPoints, newPointList);
        
        Point previousPoint = null;
        for (int i = newPointList.size() - 1; i >= 0; i--) {
            Point point = newPointList.get(i);
            point.right = previousPoint;
            previousPoint = point;
        }
    }
    
    private static class Point {
        int x;
        int y;
        
        Point left; //y
        Point right; //x
        
        Integer hashCode;
        
        public Point(int x, int y, Point left, Point right) {
            this.x = x;
            this.y = y;
            this.left = left;
            this.right = right;
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }
    
        @Override
        public int hashCode() {
            if (hashCode == null) {
                hashCode = Objects.hash(x, y);
            }
            return hashCode;
        }
        
        void cutVertical(int x, Set<Point> processedPoints, List<Point> result) {
            if (!processedPoints.contains(this)) {
                processedPoints.add(this);
                
                if (this.x < x && x < right.x) {
                    Point newPoint = new Point(x, this.y, null, right);
                    right = newPoint;
                    result.add(newPoint);
                } else {
                    if (right != null) right.cutVertical(x, processedPoints, result);
                }
                
                if (left != null) left.cutVertical(x, processedPoints, result);
            }
        }
    
        void cutHorizontal(int y, Set<Point> processedPoints, List<Point> result) {
            if (!processedPoints.contains(this)) {
                processedPoints.add(this);
            
                if (this.y < y && y < left.y) {
                    Point newPoint = new Point(this.x, y, left, null);
                    left = newPoint;
                    result.add(newPoint);
                } else {
                    if (left != null) left.cutHorizontal(y, processedPoints, result);
                }
    
                if (right != null) right.cutHorizontal(y, processedPoints, result);
            }
        }
        
        void collectPoints(Set<Point> collect) {
            if (!collect.contains(this)) {
                collect.add(this);
                if (left != null) left.collectPoints(collect);
                if (right != null) right.collectPoints(collect);
            }
        }
        
        int findSquare() {
            Set<Point> pointSet = new HashSet<>();
            collectPoints(pointSet);
            int result = 0;
            for (Point point : pointSet) {
                int dx = Math.abs(this.x - point.x);
                int dy = Math.abs(this.y - point.y);
                if (dx == dy && dx != 0) result++;
            }
            return result;
        }
        
        int findAllSquare(Set<Point> processedPoints) {
            int result = 0;
            if (!processedPoints.contains(this)) {
                processedPoints.add(this);
                result += findSquare();
                if (left != null) result += left.findAllSquare(processedPoints);
                if (right != null) result += right.findAllSquare(processedPoints);
            }
            return result;
        }
    }
}