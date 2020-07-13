package ru.mrak.codingame.medium.theLastCrusadeEpisode1;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int W = in.nextInt(); // number of columns.
        System.err.println(W);
        int H = in.nextInt(); // number of rows.
        System.err.println(H);

        List<List<Integer>> grid = new ArrayList<>();

        if (in.hasNextLine()) {
            in.nextLine();
        }
        for (int i = 0; i < H; i++) {
            String LINE = in.nextLine(); // represents a line in the grid and contains W integers. Each integer represents one room of a given type.
            System.err.println(LINE);
            grid.add(Arrays.stream(LINE.split(" ")).map(Integer::parseInt).collect(Collectors.toList()));
        }
        int EX = in.nextInt(); // the coordinate along the X axis of the exit (not useful for this first mission, but must be read).
        System.err.println(EX);

        // game loop
        int XI = 1;
        int YI = 0;
        String POS = "TOP";
        while (true) {
            //int XI = in.nextInt();
            //int YI = in.nextInt();
            //String POS = in.next();

            Integer out = (Integer) rooms[grid.get(YI).get(XI)].get(Entrance.valueOf(POS).i);
            switch (out) {
                case 1:
                    YI++;
                    POS = "TOP";
                    break;
                case 2:
                    XI++;
                    POS = "LEFT";
                    break;
                case 3:
                    XI--;
                    POS = "RIGHT";
                    break;
            }

            // One line containing the X Y coordinates of the room in which you believe Indy will be on the next turn.
            System.out.println(XI + " " + YI);
            if(YI == (H - 1) && XI == EX) return;
        }
    }

    /*
     * Входы:
     *  1
     * 2 3
     * Выходы:
     * 3 2
     *  1
     */
    private static Map[] rooms = new HashMap[14];
    static {
        rooms[0] = new HashMap<Integer, Integer>();

        rooms[1] = new HashMap<Integer, Integer>();
        rooms[1].put(1, 1);
        rooms[1].put(2, 1);
        rooms[1].put(3, 1);

        rooms[2] = new HashMap<Integer, Integer>();
        rooms[2].put(2, 2);
        rooms[2].put(3, 3);

        rooms[3] = new HashMap<Integer, Integer>();
        rooms[3].put(1, 1);

        rooms[4] = new HashMap<Integer, Integer>();
        rooms[4].put(1, 3);
        rooms[4].put(3, 1);

        rooms[5] = new HashMap<Integer, Integer>();
        rooms[5].put(1, 2);
        rooms[5].put(2, 1);

        rooms[6] = new HashMap<Integer, Integer>();
        rooms[6].put(2, 2);
        rooms[6].put(3, 3);

        rooms[7] = new HashMap<Integer, Integer>();
        rooms[7].put(1, 1);
        rooms[7].put(3, 1);

        rooms[8] = new HashMap<Integer, Integer>();
        rooms[8].put(2, 1);
        rooms[8].put(3, 1);

        rooms[9] = new HashMap<Integer, Integer>();
        rooms[9].put(1, 1);
        rooms[9].put(2, 1);

        rooms[10] = new HashMap<Integer, Integer>();
        rooms[10].put(1, 3);

        rooms[11] = new HashMap<Integer, Integer>();
        rooms[11].put(1, 2);

        rooms[12] = new HashMap<Integer, Integer>();
        rooms[12].put(3, 1);

        rooms[13] = new HashMap<Integer, Integer>();
        rooms[13].put(2, 1);
    }

    private enum Entrance {
        TOP(1),
        LEFT(2),
        RIGHT(3)
        ;

        int i;

        Entrance(int i) {
            this.i = i;
        }
    }
}
