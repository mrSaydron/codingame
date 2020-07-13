package ru.mrak.algorithm;

import ru.mrak.types.Point;

import java.util.*;
import java.util.function.Predicate;

/**
 * Реализация волнового алгоритма
 * На вход принимает двух мерное поле, предикаты условия преград и конечной точки, координаты начальной точки, а также
 * тип окресностей (ортогональные, диагональные)
 *
 * @param <T>
 */
public final class Lee <T> {

    private NeighborHood neighborHood;
    private T[][] matrix;
    private Predicate<T> borderPredicate;
    private Predicate<T> targetPredicate;
    private Point startPoint;

    private int width;
    private int height;
    private int[][] weight;
    private Queue<Point> workQueue = new LinkedList<>();

    public Lee(NeighborHood neighborHood, T[][] matrix, Predicate<T> borderPredicate, Predicate<T> targetPredicate, Point startPoint) {
        this.neighborHood = neighborHood;
        this.matrix = matrix;
        this.borderPredicate = borderPredicate;
        this.targetPredicate = targetPredicate;
        this.startPoint = startPoint;
    }

    public List<Point> findPath() {
        List<Point> result = null;
        Point targetPoint = null;
        init();

        find:
        while (workQueue.size() > 0) {
            Point nextPoint = workQueue.poll();
            List<Point> surroundPoints = getSurroundPoints(nextPoint);
            surroundPoints.removeIf(point ->
                    weight[point.getX()][point.getY()] != -1 ||
                    borderPredicate.test(matrix[point.getX()][point.getY()]));
            int nextWeight = weight[nextPoint.getX()][nextPoint.getY()] + 1;
            for (Point surroundPoint : surroundPoints) {
                weight[surroundPoint.getX()][surroundPoint.getY()] = nextWeight;
                if (targetPredicate.test(matrix[surroundPoint.getX()][surroundPoint.getY()])) {
                    targetPoint = surroundPoint;
                    break find;
                }
            }
        }

        if (targetPoint != null) {
            Point[] pathArray = new Point[weight[targetPoint.getX()][targetPoint.getY()] + 1];
            while (true) {
                int pointWeight = weight[targetPoint.getX()][targetPoint.getY()];
                pathArray[pointWeight] = targetPoint;
                if (targetPoint.equals(startPoint)) {
                    break;
                }
                List<Point> surroundPoints = getSurroundPoints(targetPoint);
                for (Point surroundPoint : surroundPoints) {
                    if (weight[surroundPoint.getX()][surroundPoint.getY()] == pointWeight - 1) {
                        targetPoint = surroundPoint;
                        break;
                    }
                }
            }
            result = Arrays.asList(pathArray);
        }

        return result;
    }

    private void init() {
        width = matrix.length;
        height = matrix[0].length;
        weight = new int[width][height];

        for (int i = 0; i < matrix.length; i++) {
            Arrays.fill(weight[i], -1);
        }

        weight[startPoint.getX()][startPoint.getY()] = 0;
        workQueue.offer(startPoint);
    }

    private List<Point> getSurroundPoints(Point point) {
        List<Point> result = null;
        if (neighborHood.equals(NeighborHood.MOORE)) {
            result = new ArrayList<>(8);
            for (int x = point.getX() - 1; x <= point.getY() + 1; x++) {
                for (int y = point.getY() - 1; y <= point.getY() + 1; y++) {
                    if (
                            x >= 0 &&
                                    x < width &&
                                    y >= 0 &&
                                    y < height &&
                                    !(x == point.getX() && y == point.getY())
                    ) {
                        result.add(new Point(x, y));
                    }
                }
            }
        } if (neighborHood.equals(NeighborHood.NEUMANN)) {
            result = new ArrayList<>(4);
            if (point.getX() - 1 >= 0) {
                result.add(new Point(point.getX() - 1, point.getY()));
            }
            if (point.getY() - 1 >= 0) {
                result.add(new Point(point.getX(), point.getY() - 1));
            }
            if (point.getX() + 1 < width) {
                result.add(new Point(point.getX() + 1, point.getY()));
            }
            if (point.getY() + 1 < height) {
                result.add(new Point(point.getX(), point.getY() + 1));
            }
        }
        return result;
    }

    public enum NeighborHood {
        MOORE, //Ортогональные и диаганальные окресности
        NEUMANN, //Ортогональные окресности
    }
}
