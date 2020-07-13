package ru.mrak.codingame.hard.voxCodei;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

class Player {
    
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int width = in.nextInt(); // width of the firewall grid
        int height = in.nextInt(); // height of the firewall grid
        if (in.hasNextLine()) {
            in.nextLine();
        }
        System.err.println(width);
        System.err.println(height);
        Array2<Field> field = new Array2<>(width, height, Field.EMPTY);
        for (int row = 0; row < height; row++) {
            String mapRow = in.nextLine(); // one line of the firewall grid
            char[] chars = mapRow.toCharArray();
            for (int col = 0; col < chars.length; col++) {
                field.set(row, col, Field.fieldByChar.get(chars[col]));
            }
            System.err.println(mapRow);
        }
        int rounds = in.nextInt(); // number of rounds left before the end of the game
        int bombs = in.nextInt(); // number of bombs left
        System.err.println(rounds);
        System.err.println(bombs);

        //compute
        List<Point> turn = turn(field, rounds, bombs);
        Collections.reverse(turn);
    
        for (Point point : turn) {
            if (point != null) {
                System.out.println(point.coll + " " + point.row);
            } else {
                System.out.println("WAIT");
            }
            in.nextInt(); in.nextInt();
        }
        while (true) {
            System.out.println("WAIT");
            in.nextInt(); in.nextInt();
        }
    }
    
    private static List<Point> turn(Array2<Field> field, int turnNumber, int bombCount) {
        System.err.println();
        System.err.println("turn " + turnNumber);
        System.err.println("bomb " + bombCount);
        System.err.println(field.toString());
        Array2<Field> turnField = new Array2<>(field);
        boom(turnField);
        if (emptyCheck(turnField)) {
            return new ArrayList<>();
        }
        if (turnNumber == 0) {
            return null;
        }
        if (bombCount > 0) {
            List<BombPlace> bombPlaceList = getBombPlaceList(turnField);
            for (BombPlace bombPlace : bombPlaceList) {
                Array2<Field> bombField = new Array2<>(turnField);
                addBomb(bombPlace.point, bombField);
                Field testTurn = turnField.get(bombPlace.point);
                int addTurn = testTurn.addTurn;
                List<Point> turn = turn(bombField, turnNumber - 1 + addTurn, bombCount - 1);
                if (turn != null) {
                    turn.add(bombPlace.point);
                    for (int i = 0; i < addTurn; i++) {
                        turn.add(null);
                    }
                    return turn;
                }
            }
        }
        return null;
    }
    
    private static void addBomb(Point bomb, Array2<Field> bombField) {
        for (Direction direction : Direction.directionList) {
            for (int radius = 1; radius <= BOMB_RADIUS; radius++) {
                Point node = bomb.add(direction.delta.mul(radius));
                Field check = bombField.get(node);
                if (Objects.equals(check, Field.NODE)) bombField.set(node, Field.BOMB_3);
                if (Objects.equals(check, Field.PASSIVE)) break;
            }
        }
    }
    
    private static void boom(Array2<Field> field) {
        for (int row = 0; row < field.height; row++) {
            for (int col = 0; col < field.width; col++) {
                switch (field.get(row, col)) {
                    case BOMB_3: field.set(row, col, Field.BOMB_2); break;
                    case BOMB_2: field.set(row, col, Field.BOOM); break;
                    case BOOM: field.set(row, col, Field.EMPTY); break;
                }
            }
        }
    }
    
    private static boolean emptyCheck(Array2<Field> field) {
        for (int row = 0; row < field.height; row++) {
            for (int col = 0; col < field.width; col++) {
                if (Objects.equals(field.get(row, col), Field.NODE)) return false;
            }
        }
        return true;
    }
    
    private static final int BOMB_RADIUS = 3;
    
    private static List<BombPlace> getBombPlaceList(Array2<Field> filed) {
        List<BombPlace> result = new ArrayList<>();
        for (int row = 0; row < filed.height; row++) {
            for (int col = 0; col < filed.width; col++) {
                if (Objects.equals(filed.get(row, col), Field.EMPTY)
                        || Objects.equals(filed.get(row, col), Field.BOMB_3)
                        || Objects.equals(filed.get(row, col), Field.BOMB_2)
                        || Objects.equals(filed.get(row, col), Field.BOOM)) {
                    Point bomb = new Point(row, col);
                    List<Point> destroyNodeList = getDestroyNodeList(bomb, filed);
                    if (!destroyNodeList.isEmpty()) {
                        result.add(new BombPlace(destroyNodeList.size(), bomb));
                    }
                }
            }
        }
        result.sort(Comparator.comparing(bombPlace -> bombPlace.nodeCount, Comparator.reverseOrder()));
        return result;
    }
    
    private static List<Point> getDestroyNodeList(Point bomb, Array2<Field> filed) {
        List<Point> result = new ArrayList<>();
        dir:
        for (Direction direction : Direction.directionList) {
            for (int radius = 1; radius <= BOMB_RADIUS; radius++) {
                Point node = bomb.add(direction.delta.mul(radius));
                Field check = filed.get(node);
                if (Objects.equals(check, Field.NODE)) result.add(node);
                if (Objects.equals(check, Field.PASSIVE)) continue dir;
            }
        }
        return result;
    }
    
    private static class BombPlace {
        int nodeCount;
        Point point;
        
        public BombPlace(int nodeCount, Point point) {
            this.nodeCount = nodeCount;
            this.point =point;
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
    
    private enum Field {
        EMPTY('.', 0),
        NODE('@', 0),
        PASSIVE('#', 0),
        BOMB_3('3', 3),
        BOMB_2('2', 2),
        BOOM('1', 0),
        ;
        
        char symbol;
        int addTurn;
    
        Field(char symbol, int addTurn) {
            this.symbol = symbol;
            this.addTurn = addTurn;
        }
    
        public static Map<Character, Field> fieldByChar
                = Arrays.stream(Field.values())
                .collect(Collectors.toMap(field -> field.symbol, Function.identity()));
    
        @Override
        public String toString() {
            return "" + symbol;
        }
    }
    
    private enum Direction {
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
