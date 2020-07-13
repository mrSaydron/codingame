package ru.mrak.codingame.hard.winamaxSponsoredContest;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

class Solution {
    
    private static int index = -1;
    private static Array2<FieldType> field;
    private static List<Kick> kickList = new ArrayList<>();
    private static Map<Point, Boolean> holeUse;
    
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int width = in.nextInt();
        int height = in.nextInt();
    
        System.err.println(width + " " + height);
        field = new Array2<>(width, height, FieldType.EMPTY);
        for (int row = 0; row < height; row++) {
            String next = in.next();
            char[] cellArray = next.toCharArray();
            System.err.println(next);
            for (int coll = 0; coll < cellArray.length; coll++) {
                char c = cellArray[coll];
                if (c >= '1' && c <= '9') {
                    kickList.add(new Kick(c - '0', new Point(row, coll)));
                    c = FieldType.KICK.symbol;
                }
                field.set(row, coll, FieldType.fieldTypeBySymbol.get(c));
            }
        }
        kickList.sort(Comparator.comparing(Kick::getKicks));
        holeUse = getHoleList();
        
        Array2<Direction> emptyDirections = new Array2<>(width, height, Direction.EMPTY);
        Array2<Direction> result = next(null, new FindResult(emptyDirections, true, 0));
    
        if (result != null) {
            System.out.println(result.toString());
        }
    }
    
    private static Map<Point, Boolean>  getHoleList() {
        Map<Point, Boolean>  result = new HashMap<>();
        for (int row = 0; row < field.height; row++) {
            for (int coll = 0; coll < field.width; coll++) {
                if (field.get(row, coll).equals(FieldType.HOLE)) {
                    result.put(new Point(row, coll), false);
                }
            }
        }
        return result;
    }
    
    private static Array2<Direction> findPath(int kick, Point start, Array2<Direction> directions) {
        if (kick == 0) return null;
        List<Point> nextKick = new ArrayList<>();
        for (Direction direction : Direction.directionList) {
            nextKick.add(start.add(direction.delta.mul(kick)));
        }
        
        for (Point point : nextKick) {
            if (checkPoint(point)) {
                Array2<Direction> newDirection = checkCross(directions, start, point);
                if (newDirection != null) {
                    if (holeUse.containsKey(point)) {
                        if (!holeUse.get(point)) {
                            holeUse.put(point, true);
                            Array2<Direction> next = next(point, new FindResult(newDirection, true, 0));
                            if (next != null) return next;
                            holeUse.put(point, false);
                        }
                    } else {
                        Array2<Direction> next = next(point, new FindResult(newDirection, false, kick - 1));
                        if (next != null) return next;
                    }
                }
            }
        }
        return null;
    }
    
    private static class FindResult {
        Array2<Direction> directions;
        boolean holeFind;
        int kick;
    
        public FindResult(Array2<Direction> directions, boolean holeFind, int kick) {
            this.directions = directions;
            this.holeFind = holeFind;
            this.kick = kick;
        }
    }
    
    private static Array2<Direction> next(Point start, FindResult findResult) {
        System.err.println(index + 1);
        System.err.println(findResult.directions.toString());
        System.err.println();
        if (findResult.holeFind) {
            index++;
            if (kickList.size() == index) return findResult.directions;
            Kick kick = kickList.get(index);
            Array2<Direction> path = findPath(kick.kicks, kick.startPoint, findResult.directions);
            index--;
            return path;
        } else {
            return findPath(findResult.kick, start, findResult.directions);
        }
    }
    
    private static boolean checkPoint(Point point) {
        return Objects.equals(field.get(point), FieldType.EMPTY) || Objects.equals(field.get(point), FieldType.HOLE);
    }
    
    private static Array2<Direction> checkCross(Array2<Direction> directions, Point start, Point finish) {
        Array2<Direction> newDirection = null;
        Direction direction;
        if (start.row == finish.row) {
            int lowColl;
            int upColl;
            if (start.coll < finish.coll) {
                direction = Direction.RIGHT;
                lowColl = start.coll;
                upColl = finish.coll;
            } else {
                direction = Direction.LEFT;
                lowColl = finish.coll;
                upColl = start.coll;
            }
            for (int coll = lowColl; coll <= upColl; coll++) {
                if (!directions.get(start.row, coll).equals(Direction.EMPTY)) {
                    return null;
                }
            }
            newDirection = new Array2<>(directions);
            for (int coll = lowColl; coll <= upColl; coll++) {
                newDirection.set(start.row, coll, direction);
            }
            newDirection.set(finish.row, finish.coll, Direction.EMPTY);
        } else if (start.coll == finish.coll) {
            int lowRow;
            int upRow;
            if (start.row < finish.row) {
                direction = Direction.DOWN;
                lowRow = start.row;
                upRow = finish.row;
            } else {
                direction = Direction.UP;
                lowRow = finish.row;
                upRow = start.row;
            }
            for (int row = lowRow; row <= upRow; row++) {
                if (!directions.get(row, start.coll).equals(Direction.EMPTY)) {
                    return null;
                }
            }
            newDirection = new Array2<>(directions);
            for (int row = lowRow; row <= upRow; row++) {
                newDirection.set(row, start.coll, direction);
            }
            newDirection.set(finish.row, finish.coll, Direction.EMPTY);
        }
        return newDirection;
    }
    
    private static class Kick {
        int kicks;
        Point startPoint;
    
        public Kick(int kicks, Point startPoint) {
            this.kicks = kicks;
            this.startPoint = startPoint;
        }
    
        public int getKicks() {
            return kicks;
        }
    }
    
    private static class Point {
        int row;
        int coll;
    
        public Point(int row, int coll) {
            this.row = row;
            this.coll = coll;
        }
        
        public Point mul(int k) {
            return new Point(this.row * k, this.coll * k);
        }
        
        public Point add(Point point) {
            return new Point(this.row + point.row, this.coll + point.coll);
        }
    
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return row == point.row &&
                    coll == point.coll;
        }
    
        @Override
        public int hashCode() {
            return Objects.hash(row, coll);
        }
    }
    
    private static class Array2<T extends Enum> {
        int width;
        int height;
        
        Object[][] fields;
    
        public Array2(int width, int height, T defaultField) {
            this.width = width;
            this.height = height;
            
            fields = new Object[height][];
            for (int i = 0; i < height; i++) {
                fields[i] = new Object[width];
                Arrays.fill(fields[i], defaultField);
            }
        }
        
        public Array2(Array2<T> array2) {
            this.width = array2.width;
            this.height = array2.height;
            
            fields = new Object[height][];
            for (int i = 0; i < height; i++) {
                fields[i] = Arrays.copyOf(array2.fields[i], array2.fields[i].length);
            }
        }
        
        public T get(Point point) {
            return get(point.row, point.coll);
        }
        
        public T get(int row, int coll) {
            if (row < 0 || coll < 0 || row >= height || coll >= width) return null;
            
            return (T)fields[row][coll];
        }
        
        public void set(Point point, T fieldType) {
            set(point.row, point.coll, fieldType);
        }
        
        public void set(int row, int coll, T fieldType) {
            if (row >= 0 && coll >= 0 && row < height && coll < width) {
                fields[row][coll] = fieldType;
            }
        }
        
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            for (int row = 0; row < height - 1; row++) {
                for (int coll = 0; coll < width; coll++) {
                    stringBuilder.append(get(row, coll).toString());
                }
                stringBuilder.append('\n');
            }
            for (int coll = 0; coll < width; coll++) {
                stringBuilder.append(get(height - 1, coll).toString());
            }
            return stringBuilder.toString();
        }
    }
    
    private enum FieldType {
        EMPTY('.'),
        WATER('X'),
        HOLE('H'),
        KICK('K')
        ;
        
        char symbol;
    
        FieldType(char symbol) {
            this.symbol = symbol;
        }
    
        public char getSymbol() {
            return symbol;
        }
     
        static Map<Character, FieldType> fieldTypeBySymbol = Arrays.stream(FieldType.values())
                .collect(Collectors.toMap(FieldType::getSymbol, Function.identity()));
    
        @Override
        public String toString() {
            return symbol + "";
        }
    }
    
    private enum Direction {
        EMPTY('.', null),
        UP('^', new Point(-1, 0)),
        DOWN('v', new Point(1, 0)),
        LEFT('<', new Point(0, -1)),
        RIGHT('>', new Point(0, 1)),
        ;
    
        char symbol;
        Point delta;
    
        Direction(char symbol, Point delta) {
            this.symbol = symbol;
            this.delta = delta;
        }
        
        static List<Direction> directionList = Arrays.asList(UP, DOWN, LEFT, RIGHT);
    
        @Override
        public String toString() {
            return symbol + "";
        }
    }
}