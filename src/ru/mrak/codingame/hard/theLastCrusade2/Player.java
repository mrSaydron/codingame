package ru.mrak.codingame.hard.theLastCrusade2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

class Player {
    
    private static int exitColumn;
    
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int W = in.nextInt(); // number of columns.
        int H = in.nextInt(); // number of rows.
        if (in.hasNextLine()) {
            in.nextLine();
        }
        System.err.println(W + " " + H);
        Array2 field = new Array2(W, H);
        for (int row = 0; row < H; row++) {
            String LINE = in.nextLine(); // each line represents a line in the grid and contains W integers T. The absolute value of T specifies the type of the room. If T is negative, the room cannot be rotated.
            String[] s = LINE.split(" ");
            System.err.println(LINE);
            for (int col = 0; col < s.length; col++) {
                int roomId = Integer.parseInt(s[col]);
                Room room = new Room();
                room.canTurn = roomId > 0;
                room.roomType = roomById.get(Math.abs(roomId));
                room.roomPosition = new Point(row, col);
                field.setRoom(row, col, room);
            }
        }
        exitColumn = in.nextInt(); // the coordinate along the X axis of the exit.
        System.err.println(exitColumn);
        
        State state = new State();
        state.field = field;
        
        // game loop
        int lastTurnRockNumber = 0;
        List<ResultFields> calc = null;
        int index = -1;
        while (true) {
            int XI = in.nextInt();
            int YI = in.nextInt();
            String POSI = in.next();
            System.err.println(XI + " " + YI + " " + POSI);
            
            ObjectState indy = new ObjectState();
            indy.position = new Point(YI, XI);
            indy.moveDirection = Direction.valueOf(POSI);
            state.indy = indy;
            
            int R = in.nextInt(); // the number of rocks currently in the grid.
            System.err.println(R);
            for (int i = 0; i < R; i++) {
                int XR = in.nextInt();
                int YR = in.nextInt();
                String POSR = in.next();
                System.err.println(XR + " " + YR + " " + POSR);
    
                ObjectState stone = new ObjectState();
                stone.position = new Point(YR, XR);
                stone.moveDirection = Direction.valueOf(POSR);
                state.stoneList.add(stone);
            }
    
            if (lastTurnRockNumber != R || calc == null) {
                calc = calc(state, Choice.LEFT);
                index = calc.size() - 1;
            }
            
            String resultString = Choice.WHITE.name();
            if (calc != null && index >= 0) {
                Choice turnChose = calc.get(index).choice;
                Point turnPoint = calc.get(index).point;
                if (index > 0 && calc.get(index - 1).choice.equals(Choice.RIGHT_TWO)) {
                    turnChose = Choice.RIGHT;
                    turnPoint = calc.get(index - 1).point;
                }
                if (turnChose.equals(Choice.WHITE)) {
                    resultString = Choice.WHITE.name();
                } else {
                    resultString = turnPoint.col + " " + turnPoint.row + " " + turnChose.name();
                }
                index--;
            }
            System.out.println(resultString);
        }
    }
    
    private static List<ResultFields> calc(State state, Choice lastChoice) {
        ObjectState oldIndy = state.indy;
        List<ObjectState> oldStoneList = state.stoneList;
    
        List<Point> calcPointList = new ArrayList<>();
        ObjectState newIndy = nextPositionNotCheck(state.field, state.indy);
        calcPointList.add(newIndy.position);
        List<ObjectState> newStoneList = oldStoneList.stream().map(stone -> {
            ObjectState nextStone = nextPositionNotCheck(state.field, stone);
            if (nextStone != null) return nextStone;
            else return stone;})
                .collect(Collectors.toList());
        newStoneList.stream()
                .filter(stone -> !stone.position.equals(newIndy.position))
                .forEach(stone -> calcPointList.add(stone.position));
        
        for (int pointIndex = 0; pointIndex < calcPointList.size(); pointIndex++) {
            Point point = calcPointList.get(pointIndex);
            Room calcRoom = state.field.getRoom(point);
            
            if (calcRoom.canTurn) {
                RoomType startType = calcRoom.roomType;
                for (Choice choice : Choice.values()) {
                    if (Choice.RIGHT_TWO.equals(choice) && !Choice.WHITE.equals(lastChoice)) continue;
                    calcRoom.roomType = choice.getRoom(startType);
                    
                    if (pointIndex == 0) {
                        if (!calcRoom.roomType.transitList.containsKey(oldIndy.moveDirection)) continue;
                    }
    
                    newStoneList = oldStoneList.stream().map(stone -> {
                        ObjectState nextStone = nextPositionNotCheck(state.field, stone);
                        if (nextStone != null) return nextStone;
                        else return stone;})
                            .collect(Collectors.toList());
    
                    boolean isCrack = newStoneList.stream().anyMatch(stone -> stone.position.equals(newIndy.position));
                    if (isCrack) continue;
    
                    state.indy = newIndy;
                    state.stoneList = newStoneList;
                    
                    List<ResultFields> calc = calc(state, choice);
                    if (calc != null) {
                        calc.add(new ResultFields(choice, point));
                        return calc;
                    }
                }
                calcRoom.roomType = startType;
            } else {
                newStoneList = oldStoneList.stream().map(stone -> {
                    ObjectState nextStone = nextPositionNotCheck(state.field, stone);
                    if (nextStone != null) return nextStone;
                    else return stone;})
                        .collect(Collectors.toList());
    
                boolean isCrack = newStoneList.stream().anyMatch(stone -> stone.position.equals(newIndy.position));
                if (isCrack) continue;
    
                state.indy = newIndy;
                state.stoneList = newStoneList;
    
                if (newIndy.position.col == exitColumn && newIndy.position.row == (state.field.height - 1)) {
                    return new ArrayList<>();
                }
    
                List<ResultFields> calc = calc(state, Choice.WHITE);
                if (calc != null) {
                    calc.add(new ResultFields(Choice.WHITE, point));
                    return calc;
                }
            }
        }
        
        state.indy = oldIndy;
        state.stoneList = oldStoneList;
        return null;
    }
    
    private static class ResultFields {
        Choice choice;
        Point point;
    
        public ResultFields(Choice choice, Point point) {
            this.choice = choice;
            this.point = point;
        }
    }
    
    private enum Choice {
        WHITE,
        LEFT,
        RIGHT,
        RIGHT_TWO,
        ;
        
        RoomType getRoom(RoomType roomType) {
            RoomType result = null;
            switch (this) {
                case WHITE:
                    result = roomType;
                    break;
                case LEFT:
                    result = roomType.turnRoom.get(Turn.LEFT);
                    break;
                case RIGHT:
                    result = roomType.turnRoom.get(Turn.RIGHT);
                    break;
                case RIGHT_TWO:
                    result = roomType.turnRoom.get(Turn.RIGHT).turnRoom.get(Turn.RIGHT);
                    break;
            }
            return result;
        }
    }
    
    private static boolean checkPath(State state) {
        boolean result = false;
        ObjectState indy = state.indy;
        List<ObjectState> stoneList = new ArrayList<>(state.stoneList);
        while (true) {
            indy = nextPosition(state.field, indy);
            stoneList = stoneList.stream().map(stone -> {
                ObjectState stoneNextPosition = nextPosition(state.field, stone);
                if (stoneNextPosition != null) return stoneNextPosition;
                return stone;})
                    .collect(Collectors.toList());
            if (indy == null) {
                break;
            } else if (indy.position.col == exitColumn && indy.position.row == (state.field.height - 1)) {
                result = true;
                break;
            } else {
                boolean crack = false;
                for (ObjectState stone : stoneList) {
                    if (stone.position.equals(indy.position)) {
                        crack = true;
                        break;
                    }
                }
                if (crack) {
                    break;
                }
            }
        }
        return result;
    }
    
    private static ObjectState nextPosition(Array2 field, ObjectState objectState) {
        ObjectState result = null;
    
        Room room = field.getRoom(objectState.position);
        if (room != null) {
            Direction outDirection = room.roomType.transitList.get(objectState.moveDirection);
            Point nextPoint = objectState.position.add(outDirection.delta);
            Room nextRoom = field.getRoom(nextPoint);
            if (nextRoom != null && nextRoom.roomType.transitList.containsKey(outDirection)) result = new ObjectState(nextPoint, outDirection);
        }
    
        return result;
    }
    
    private static ObjectState nextPositionNotCheck(Array2 field, ObjectState objectState) {
        ObjectState result = null;
        
        Room room = field.getRoom(objectState.position);
        if (room != null) {
            Direction outDirection = room.roomType.transitList.get(objectState.moveDirection);
            Point nextPoint = objectState.position.add(outDirection.delta);
            Room nextRoom = field.getRoom(nextPoint);
            if (nextRoom != null) result = new ObjectState(nextPoint, outDirection);
        }
        
        return result;
    }
    
    private static class ObjectState {
        Point position;
        Direction moveDirection;
    
        public ObjectState() {
        }
    
        public ObjectState(Point position, Direction moveDirection) {
            this.position = position;
            this.moveDirection = moveDirection;
        }
    }
    
    private static class State {
        Array2 field;
        ObjectState indy;
        List<ObjectState> stoneList = new ArrayList<>();
        
        StepState stepState;
    }
    
    private enum StepState {
        GOOD,
        FINISH,
        FAIL,
    }
    
    private static class Room {
        RoomType roomType;
        Point roomPosition;
        boolean canTurn;
    }
    
    private static class RoomType {
        int roomType;
        Map<Direction, Direction> transitList = new HashMap<>();
        Map<Turn, RoomType> turnRoom = new HashMap<>(2);
    }
    
    private static Map<Integer, RoomType> roomById = new HashMap<>();
    static {
        RoomType type0 = new RoomType(); roomById.put(0, type0); type0.roomType = 0;
        RoomType type1 = new RoomType(); roomById.put(1, type1); type1.roomType = 1;
        RoomType type2 = new RoomType(); roomById.put(2, type2); type2.roomType = 2;
        RoomType type3 = new RoomType(); roomById.put(3, type3); type3.roomType = 3;
        RoomType type4 = new RoomType(); roomById.put(4, type4); type4.roomType = 4;
        RoomType type5 = new RoomType(); roomById.put(5, type5); type5.roomType = 5;
        RoomType type6 = new RoomType(); roomById.put(6, type6); type6.roomType = 6;
        RoomType type7 = new RoomType(); roomById.put(7, type7); type7.roomType = 7;
        RoomType type8 = new RoomType(); roomById.put(8, type8); type8.roomType = 8;
        RoomType type9 = new RoomType(); roomById.put(9, type9); type9.roomType = 9;
        RoomType type10 = new RoomType(); roomById.put(10, type10); type10.roomType = 10;
        RoomType type11 = new RoomType(); roomById.put(11, type11); type11.roomType = 11;
        RoomType type12 = new RoomType(); roomById.put(12, type12); type12.roomType = 12;
        RoomType type13 = new RoomType(); roomById.put(13, type13); type13.roomType = 13;
        
        //0
        //000
        //000
        //000
        type0.turnRoom.put(Turn.LEFT, type0);
        type0.turnRoom.put(Turn.RIGHT, type0);
        
        //1
        //0|0
        //---
        //0|0
        type1.transitList.put(Direction.TOP, Direction.TOP);
        type1.transitList.put(Direction.LEFT, Direction.TOP);
        type1.transitList.put(Direction.RIGHT, Direction.TOP);
        type1.turnRoom.put(Turn.LEFT, type9);
        type1.turnRoom.put(Turn.RIGHT, type7);
        
        //2
        //000
        //---
        //000
        type2.transitList.put(Direction.LEFT, Direction.LEFT);
        type2.transitList.put(Direction.RIGHT, Direction.RIGHT);
        type2.turnRoom.put(Turn.LEFT, type3);
        type2.turnRoom.put(Turn.RIGHT, type3);
        
        //3
        //0|0
        //0|0
        //0|0
        type3.transitList.put(Direction.TOP, Direction.TOP);
        type3.turnRoom.put(Turn.LEFT, type2);
        type3.turnRoom.put(Turn.RIGHT, type2);
        
        //4
        //0\0
        //-0-
        //0\0
        type4.transitList.put(Direction.TOP, Direction.LEFT);
        type4.transitList.put(Direction.RIGHT, Direction.TOP);
        type4.turnRoom.put(Turn.LEFT, type5);
        type4.turnRoom.put(Turn.RIGHT, type5);
        
        //5
        //0/0
        //-0-
        //0/0
        type5.transitList.put(Direction.TOP, Direction.RIGHT);
        type5.transitList.put(Direction.LEFT, Direction.TOP);
        type5.turnRoom.put(Turn.LEFT, type4);
        type5.turnRoom.put(Turn.RIGHT, type4);
        
        //6
        //0|0
        //---
        //000
        type6.transitList.put(Direction.LEFT, Direction.LEFT);
        type6.transitList.put(Direction.RIGHT, Direction.RIGHT);
        type6.transitList.put(Direction.TOP, null);
        type6.turnRoom.put(Turn.LEFT, type9);
        type6.turnRoom.put(Turn.RIGHT, type7);
        
        //7
        //0|0
        //0|-
        //0|0
        type7.transitList.put(Direction.TOP, Direction.TOP);
        type7.transitList.put(Direction.RIGHT, Direction.TOP);
        type7.turnRoom.put(Turn.LEFT, type6);
        type7.turnRoom.put(Turn.RIGHT, type8);
        
        //8
        //000
        //-|-
        //0|0
        type8.transitList.put(Direction.LEFT, Direction.TOP);
        type8.transitList.put(Direction.RIGHT, Direction.TOP);
        type8.turnRoom.put(Turn.LEFT, type7);
        type8.turnRoom.put(Turn.RIGHT, type9);
        
        //9
        //0|0
        //-|0
        //0|0
        type9.transitList.put(Direction.LEFT, Direction.TOP);
        type9.transitList.put(Direction.TOP, Direction.TOP);
        type9.turnRoom.put(Turn.LEFT, type8);
        type9.turnRoom.put(Turn.RIGHT, type6);
        
        //10
        //0|0
        //00-
        //000
        type10.transitList.put(Direction.TOP, Direction.LEFT);
        type10.turnRoom.put(Turn.LEFT, type11);
        type10.turnRoom.put(Turn.RIGHT, type12);
        
        //11
        //0|0
        //-00
        //000
        type11.transitList.put(Direction.TOP, Direction.RIGHT);
        type11.turnRoom.put(Turn.LEFT, type13);
        type11.turnRoom.put(Turn.RIGHT, type10);
        
        //12
        //000
        //00-
        //0|0
        type12.transitList.put(Direction.RIGHT, Direction.TOP);
        type12.turnRoom.put(Turn.LEFT, type10);
        type12.turnRoom.put(Turn.RIGHT, type13);
        
        //13
        //000
        //-00
        //0|0
        type13.transitList.put(Direction.LEFT, Direction.TOP);
        type13.turnRoom.put(Turn.LEFT, type12);
        type13.turnRoom.put(Turn.RIGHT, type11);
    }
    
    private static class Point {
        int row;
        int col;
    
        Point(int row, int col) {
            this.row = row;
            this.col = col;
        }
        
        public Point add(Point point) {
            return new Point(this.row + point.row, this.col + point.col);
        }
    
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return row == point.row &&
                    col == point.col;
        }
    
        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }
    
    private static class Array2 {
        int width;
        int height;
        
        Room[][] rooms;
        
        public Array2(int width, int height) {
            this.width = width;
            this.height = height;
            
            rooms = new Room[height][];
            for (int row = 0; row < height; row++) {
                rooms[row] = new Room[width];
            }
        }
    
        public Room getRoom(Point point) {
            return getRoom(point.row, point.col);
        }
        
        public Room getRoom(int row, int col) {
            Room result = null;
            if (row >= 0 && row < height && col >= 0 && col < width) {
                result = rooms[row][col];
            }
            return result;
        }
        
        public void setRoom(int row, int col, Room room) {
            if (row >= 0 && row < height && col >= 0 && col < width) {
                rooms[row][col] = room;
            }
        }
    }
    
    private enum Turn {
        LEFT,
        RIGHT,
    }
    
    private enum Direction {
        TOP(new Point(1, 0)),
        RIGHT(new Point(0, -1)),
        LEFT(new Point(0, 1)),
        ;
    
        Point delta;
    
        Direction(Point delta) {
            this.delta = delta;
        }
    }
}
