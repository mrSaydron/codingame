package ru.mrak.codingame.medium.benderEpisode1;

import java.util.*;

public class Solution {
    private static int L;
    private static int C;
    private static char[][] place;
    private static Direction[] usual = {Direction.SOUTH, Direction.EAST, Direction.NORTH, Direction.WEST};
    private static Direction[] invert = {Direction.WEST, Direction.NORTH, Direction.EAST, Direction.SOUTH};
    private static Point[] teleports = new Point[2];
    private static Set<Character> canMove = new HashSet<>(Arrays.asList(' ', '$', 'S', 'E', 'N', 'W', 'B', 'I', 'T'));
    private static Set<Bender> history = new HashSet<>();

    private static Bender bender = new Bender();

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        L = in.nextInt();
        C = in.nextInt();
        System.err.println(L + " " + C);
        if (in.hasNextLine()) {
            in.nextLine();
        }
        place = new char[L][];
        for (int i = 0; i < L; i++) {
            String row = in.nextLine();
            place[i] = row.toCharArray();
        }

        bender.bear = false;
        bender.inverse = false;
        bender.countBreak = 0;
        findStartPosition();
        findTeleports();
        printPlace();
        bender.benderDirectin = usual[0];
        List<String> historyDir = new ArrayList<>();
        ext:
        while(true) {
            switch (bender.benderPosition.getChar()) {
                case 'S':
                    bender.benderDirectin = Direction.SOUTH;
                    break;
                case 'E':
                    bender.benderDirectin = Direction.EAST;
                    break;
                case 'N':
                    bender.benderDirectin = Direction.NORTH;
                    break;
                case 'W':
                    bender.benderDirectin = Direction.WEST;
                    break;
                case 'B':
                    if (bender.bear) {
                        bender.bear = false;
                        canMove.remove('X');
                    } else {
                        bender.bear = true;
                        canMove.add('X');
                    }
                    break;
                case 'X':
                    place[bender.benderPosition.line][bender.benderPosition.col] = ' ';
                    bender.countBreak++;
                    break;
                case 'I':
                    bender.inverse ^= true;
                    break;
                case 'T':
                    if (bender.benderPosition.col == teleports[0].col && bender.benderPosition.line == teleports[0].line) {
                        bender.benderPosition = teleports[1];
                    } else {
                        bender.benderPosition = teleports[0];
                    }
                    break;
                case '$':
                    break ext;
            }

            Point movePoint = bender.benderPosition.move(bender.benderDirectin);
            char moveChar = movePoint.getChar();
            if (!canMove.contains(moveChar)) {
                bender.benderDirectin = freeDirection();
                movePoint = bender.benderPosition.move(bender.benderDirectin);
            }
            bender.benderPosition = movePoint;

            if (!history.contains(bender)) {
                history.add(bender);
            } else {
                System.out.println("LOOP");
                return;
            }

            historyDir.add(bender.benderDirectin.toString());
            printPlace();
        }

        historyDir.forEach(System.out::println);
    }

    private static Direction freeDirection() {
        Direction[] dirs = bender.inverse ? invert : usual;
        for (Direction dir: dirs) {
            char moveChar = bender.benderPosition.move(dir).getChar();
            if(canMove.contains(moveChar)) return dir;
        }
        throw new RuntimeException();
    }

    private static class Point {
        int col;
        int line;

        Point(int col, int line) {
            this.col = col;
            this.line = line;
        }

        Point move (Direction direction) {
            return new Point(col + direction.dCol, line + direction.dLine);
        }

        char getChar() {
            return place[line][col];
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return col == point.col &&
                    line == point.line;
        }

        @Override
        public int hashCode() {
            return Objects.hash(col, line);
        }
    }

    private static void findStartPosition() {
        for (int i = 1; i < L - 1; i++) {
            for (int j = 0; j < C; j++) {
                if (place[i][j] == '@') {
                    place[i][j] = ' ';
                    bender.benderPosition = new Point(j, i);
                }
            }
        }
    }

    private static void findTeleports() {
        int teleportNumber = 0;
        for (int i = 1; i < L - 1; i++) {
            for (int j = 0; j < C; j++) {
                if (place[i][j] == 'T') {
                    teleports[teleportNumber] = new Point(j, i);
                    teleportNumber++;
                }
            }
        }
    }

    private enum Direction {
        SOUTH(0, 1),
        EAST(1, 0),
        NORTH(0, -1),
        WEST(-1, 0)
        ;

        int dCol;
        int dLine;

        Direction(int dCol, int dLine) {
            this.dCol = dCol;
            this.dLine = dLine;
        }
    }

    private static void printPlace() {
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < C; j++) {
                if(bender.benderPosition.col == j && bender.benderPosition.line == i) {
                    System.err.print('@');
                } else {
                    System.err.print(place[i][j]);
                }
            }
            if(i == 0) System.err.println(" " + bender.inverse + " " + bender.bear);
            else System.err.println();
        }
        System.err.println();
    }

    private static class Bender {
        Point benderPosition;
        boolean inverse;
        boolean bear;
        Direction benderDirectin;
        int countBreak;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Bender bender = (Bender) o;
            return inverse == bender.inverse &&
                    bear == bender.bear &&
                    countBreak == bender.countBreak &&
                    Objects.equals(benderPosition, bender.benderPosition) &&
                    benderDirectin == bender.benderDirectin;
        }

        @Override
        public int hashCode() {
            return Objects.hash(benderPosition, inverse, bear, benderDirectin, countBreak);
        }
    }
}
