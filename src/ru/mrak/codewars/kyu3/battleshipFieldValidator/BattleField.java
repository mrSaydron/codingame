package ru.mrak.codewars.kyu3.battleshipFieldValidator;

import java.util.ArrayList;
import java.util.List;

public class BattleField {
    
    public static boolean fieldValidator(int[][] field) {
        PatternStamp connerStamp = new PatternStamp(new int[][]
                {{0, 2},
                 {2, 0}});
        Pattern connerPattern = new Pattern(connerStamp, TurnType.TURN_90_DEGREES);
    
        PatternStamp shipOneStamp = new PatternStamp(new int[][]
                {{-2, -2, -2},
                 {-2,  2, -2},
                 {-2, -2, -2}});
        Pattern shipOnePattern = new Pattern(shipOneStamp, TurnType.NO);
    
        PatternStamp shipTwoStamp = new PatternStamp(new int[][]
                {{-2,  2,  2, -2}});
        Pattern shipTwoPattern = new Pattern(shipTwoStamp, TurnType.TURN_90_DEGREES);
    
        PatternStamp shipThreeStamp = new PatternStamp(new int[][]
                {{-2,  2,  2,  2, -2}});
        Pattern shipThreePattern = new Pattern(shipThreeStamp, TurnType.TURN_90_DEGREES);
    
        PatternStamp shipFourStamp = new PatternStamp(new int[][]
                {{-2,  2,  2,  2,  2, -2}});
        Pattern shipFourPattern = new Pattern(shipFourStamp, TurnType.TURN_90_DEGREES);
    
        PatternStamp shipTooLargeStamp = new PatternStamp(new int[][]
                {{2, 2, 2, 2, 2}});
        Pattern shipTooLargePattern = new Pattern(shipTooLargeStamp, TurnType.TURN_90_DEGREES);
        
        Field battleField = new Field(field);
        battleField.addOne();
        
        if (connerPattern.findOnField(battleField) != 0) return false;
        if (shipOnePattern.findOnField(battleField) != 4) return false;
        if (shipTwoPattern.findOnField(battleField) != 3) return false;
        if (shipThreePattern.findOnField(battleField) != 2) return false;
        if (shipFourPattern.findOnField(battleField) != 1) return false;
        if (shipTooLargePattern.findOnField(battleField) != 0) return false;
    
        return true;
    }
    
    private static class Pattern {
        List<PatternStamp> patternStampList;
    
        Pattern(PatternStamp stamp, TurnType turnType) {
            patternStampList = new ArrayList<>();
            patternStampList.add(stamp);
            
            switch (turnType) {
                case NO: break;
                case TURNS:
                    PatternStamp turnStamp = stamp;
                    for (int i = 0; i < 3; i++) {
                        turnStamp = stamp.turn();
                        patternStampList.add(turnStamp);
                    }
                    break;
                case TURN_90_DEGREES:
                    patternStampList.add(stamp.turn());
                    break;
            }
        }
        
        int findOnField(Field field) {
            return patternStampList
                    .stream()
                    .mapToInt(patternStamp -> patternStamp.findOnField(field))
                    .sum();
        }
    }
    
    private static class PatternStamp{
        int sizeX;
        int sizeY;
        int[][] stamp;
    
        public PatternStamp(int[][] stamp) {
            this.stamp = stamp;
            this.sizeX = stamp.length;
            this.sizeY = stamp[0].length;
        }
    
        PatternStamp turn() {
            PatternStamp newStamp = new PatternStamp(new int[sizeY][sizeX]);
    
            for (int x = 0; x < sizeX; x++) {
                for (int y = 0; y < sizeY; y++) {
                    newStamp.stamp[y][x] = stamp[x][y];
                }
            }
            
            return newStamp;
        }
        
        int findOnField(Field field) {
            int result = 0;
            for (int x = 1 - sizeX; x < field.sizeX; x++) {
                for (int y = 1 - sizeY; y < field.sizeY; y++) {
                    result += find(field, x, y) ? 1 : 0;
                }
            }
            return result;
        }
        
        boolean find(Field field, int dx, int dy) {
            boolean result = true;
            fin:
            for (int x = 0; x < sizeX; x++) {
                for (int y = 0; y < sizeY; y++) {
                    if (stamp[x][y] > 0) {
                        if (field.get(x + dx,y + dy) != stamp[x][y]) {
                            result = false;
                            break fin;
                        }
                    }
                    if (stamp[x][y] < 0) {
                        if (field.get(x + dx,y + dy) == -1 * stamp[x][y]) {
                            result = false;
                            break fin;
                        }
                    }
                }
            }
            return result;
        }
    }
    
    private enum TurnType {
        NO,
        TURNS,
        TURN_90_DEGREES,
    }
    
    private static class Field {
        int[][] field;
        int sizeX;
        int sizeY;
    
        public Field(int[][] field) {
            this.field = field;
            this.sizeX = field.length;
            this.sizeY = field[0].length;
        }
    
        int get(int x, int y) {
            if (x < 0 || y < 0 || x >= sizeX || y >= sizeY) {
                return 10;
            }
            return field[x][y];
        }
        
        void addOne() {
            for (int x = 0; x < sizeX; x++) {
                for (int y = 0; y < sizeY; y++) {
                    field[x][y]++;
                }
            }
        }
    }
}
