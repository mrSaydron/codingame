package ru.mrak.codingame.hard.theLabyrinth;

import java.util.*;

class Player {
    
    static String[] rows;
    static int[][] waveField;
    static int R;
    static int C;
    static int A;
    
    static boolean findC;
    static Point cPoint;
    static boolean useC;
    
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        R = in.nextInt(); // number of rows.
        C = in.nextInt(); // number of columns.
        A = in.nextInt(); // number of rounds between the time the alarm countdown is activated and the time the alarm goes off.
        
        System.err.println(R);
        System.err.println(C);
        System.err.println(A);
        
        // game loop
        rows = new String[R];
        waveField = new int[R][C];
        while (true) {
            int KR = in.nextInt(); // row where Kirk is located.
            int KC = in.nextInt(); // column where Kirk is located.
    
            System.err.println(KR);
            System.err.println(KC);
            
            for (int rowIndex = 0; rowIndex < R; rowIndex++) {
                rows[rowIndex] = in.next(); // C of the characters in '#.TC?' (i.e. one line of the ASCII maze).
                System.err.println(rows[rowIndex]);
                if (!findC) {
                    int columnIndex = rows[rowIndex].indexOf('C');
                    if (columnIndex >= 0) {
                        findC = true;
                        cPoint = new Point(rowIndex, columnIndex);
                    }
                }
            }
            Direction direction;
            if (findC) {
                WaveResult tResult = waveCalc(cPoint.row, cPoint.column, 'T');
                if (tResult == null || tResult.steps > A) {
                    WaveResult result = waveCalc(KR, KC, '?');
                    direction = result.directionList.get(result.directionList.size() - 1);
                    System.out.println(direction.name()); // Kirk's next move (UP DOWN LEFT or RIGHT).
                } else {
                    WaveResult toC = waveCalc(KR, KC, 'C');
                    for (int i = toC.directionList.size() - 1; i >= 0; i--) {
                        System.out.println(toC.directionList.get(i).name());
                    }
                    for (int i = tResult.directionList.size() - 1; i >= 0; i--) {
                        System.out.println(tResult.directionList.get(i).name());
                    }
                }
            } else {
                WaveResult result = waveCalc(KR, KC, '?');
                direction = result.directionList.get(result.directionList.size() - 1);
                System.out.println(direction.name()); // Kirk's next move (UP DOWN LEFT or RIGHT).
            }
        }
    }
    
    static WaveResult waveCalc(int KR, int KC, char condition) {
        //Инициализация поля
        for (int rowIndex = 0; rowIndex < R; rowIndex++) {
            Arrays.fill(waveField[rowIndex], 0);
        }
        //Поиск вперед
        int steps = 1;
        waveField[KR][KC] = 1;
        WaveResult result = new WaveResult();
        Queue<Point> queue = new LinkedList<>();
        queue.offer(new Point(KR, KC));
        Point targetPoint;
        
        findFinish:
        while (true) {
            Point cell = queue.poll();
            if (cell == null) return null;
            int step = getMark(cell);
            for (Direction direction : Direction.values()) {
                Point calcPoint = cell.add(direction);
                int calcStep = getMark(calcPoint);
                if (calcStep == -1) continue;
                char calcCell = getCell(calcPoint);
                if (calcCell == condition) {
                    targetPoint = calcPoint;
                    waveField[calcPoint.row][calcPoint.column] = step + 1;
                    break findFinish;
                }
                if (calcStep > 0) continue;
                if (calcCell != '.' && calcCell != 'T') continue;
                waveField[calcPoint.row][calcPoint.column] = step + 1;
                queue.offer(calcPoint);
            }
        }
        //Поиск обратно
        int step = getMark(targetPoint);
        result.steps = step - 1;
        while(step != 1) {
            int nextStep = step - 1;
            for (Direction direction : Direction.values()) {
                Point nextPoint = targetPoint.add(direction);
                int mark = getMark(nextPoint);
                if (mark == nextStep) {
                    step--;
                    targetPoint = nextPoint;
                    result.directionList.add(direction.getReverse());
                    break;
                }
            }
        }
        return result;
    }
    
    static int getMark(Point point) {
        if (point.row < 0 || point.row >= R || point.column < 0 || point.column >= C) {
            return -1;
        }
        return waveField[point.row][point.column];
    }
    
    static char getCell(Point point) {
        return getCell(point.row, point.column);
    }
    
    static char getCell(int row, int column) {
        if (row < 0 || row >= R || column < 0 || column >= C) {
            return '#';
        }
        return rows[row].charAt(column);
    }
    
    static class WaveResult {
        int steps;
        List<Direction> directionList = new ArrayList<>();
    }
    
    static class Point {
        int row;
        int column;
    
        public Point(int row, int column) {
            this.row = row;
            this.column = column;
        }
        
        Point add(Direction direction) {
            return new Point(row + direction.dr, column + direction.dc);
        }
    }
    
    enum Direction {
        UP(-1, 0),
        DOWN(1, 0),
        LEFT(0, -1),
        RIGHT(0, 1);
        
        int dr;
        int dc;
    
        Direction(int dr, int dc) {
            this.dr = dr;
            this.dc = dc;
        }
        
        Direction getReverse() {
            switch (this) {
                case UP: return DOWN;
                case DOWN: return UP;
                case LEFT: return RIGHT;
                case RIGHT: return LEFT;
            }
            return null;
        }
    }
}
