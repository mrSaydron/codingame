package ru.mrak.codingame.botProgramming.dotsAndBoxes;

import java.util.*;

class Player {

    static GamePlayer gamePlayer = null;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        boardSize = in.nextInt(); // The size of the board.
        String playerId = in.next(); // The ID of the player. 'A'=first player, 'B'=second player.
        System.err.println(boardSize);
        System.err.println(playerId);

        initGameField();

        gamePlayer = GamePlayer.valueOf(playerId);
        MiniMax miniMax = new MiniMax(
                valueFunction,
                actionsFromState,
                stateFromAction,
                gamePlayer,
                4);
        // game loop
        while (true) {
            int playerScore = in.nextInt(); // The player's score.
            int opponentScore = in.nextInt(); // The opponent's score.
            int numBoxes = in.nextInt(); // The number of playable boxes.
            System.err.println(playerScore);
            System.err.println(opponentScore);
            System.err.println(numBoxes);

            GameState gameState = new GameState(gamePlayer, playerScore, opponentScore);

            for (int i = 0; i < numBoxes; i++) {
                String box = in.next(); // The ID of the playable box.
                String sides = in.next(); // Playable sides of the box.
                System.err.println(box + " " + sides);

                gameState.addAction(box, sides);
            }

            GameAction nextAction = miniMax.getNextAction(gameState);
            CellSide cellSide = sides.get(nextAction.cellSideIndex);
            Cell cell = cellSide.cells.get(0);
            Side resultSide = null;
            for (Map.Entry<Side, CellSide> sideCellSideEntry : cell.sideBySide.entrySet()) {
                if (sideCellSideEntry.getValue() == cellSide) {
                    resultSide = sideCellSideEntry.getKey();
                    break;
                }
            }

            System.out.println(cell.name + " " + resultSide); // <box> <side> [MSG Optional message]
        }
    }

    enum GamePlayer {
        A,
        B;
    }

    static int boardSize;
    static List<CellSide> sides = new ArrayList<>();
    static Map<String, Cell> cellByName = new HashMap<>();

    static void initGameField() {
        int sideIndex = 0;
        for (int row = 0; row < boardSize; row++) {
            for (int coll = 0; coll < boardSize; coll++) {
                Cell cell = new Cell();
                cell.name = "" + (char) ('A' + coll) + (row + 1);
                cellByName.put(cell.name, cell);

                CellSide topSide = new CellSide();
                topSide.cells.add(cell);
                topSide.index = sideIndex++;
                cell.sideBySide.put(Side.T, topSide);
                sides.add(topSide);

                CellSide rightSide = new CellSide();
                rightSide.cells.add(cell);
                rightSide.index = sideIndex++;
                cell.sideBySide.put(Side.R, rightSide);
                sides.add(rightSide);

                if (row == 0) {
                    CellSide bottomSide = new CellSide();
                    bottomSide.cells.add(cell);
                    bottomSide.index = sideIndex++;
                    cell.sideBySide.put(Side.B, bottomSide);
                    sides.add(bottomSide);
                } else {
                    Cell bCell = cellByName.get("" + (char) ('A' + coll) + row);
                    CellSide bottomSide = bCell.sideBySide.get(Side.T);
                    bottomSide.cells.add(cell);
                    cell.sideBySide.put(Side.B, bottomSide);
                }

                if (coll == 0) {
                    CellSide leftSide = new CellSide();
                    leftSide.cells.add(cell);
                    leftSide.index = sideIndex++;
                    cell.sideBySide.put(Side.L, leftSide);
                    sides.add(leftSide);
                } else {
                    Cell leftCell = cellByName.get("" + (char) ('A' + coll - 1) + (row + 1));
                    CellSide leftSide = leftCell.sideBySide.get(Side.R);
                    leftSide.cells.add(cell);
                    cell.sideBySide.put(Side.L, leftSide);
                }
            }
        }
    }

    static class GameState {
        GamePlayer gamePlayer;
        int playerScore;
        int opponentScore;
        boolean[] sideState;

        void addAction(String box, String sides) {
            Cell cell = cellByName.get(box);

            for (char side : sides.toCharArray()) {
                CellSide cellSide = cell.sideBySide.get(Side.valueOf("" + side));
                sideState[cellSide.index] = false;
            }
        }

        void drop() {
            Arrays.fill(sideState, true);
        }

        public GameState(GamePlayer gamePlayer, int playerScore, int opponentScore) {
            this.gamePlayer = gamePlayer;
            this.playerScore = playerScore;
            this.opponentScore = opponentScore;
            sideState = new boolean[2 * boardSize * boardSize + 2 * boardSize];
            Arrays.fill(sideState, true);
        }

        GameState getState() {
            GameState gameState = new GameState(gamePlayer, playerScore, opponentScore);
            System.arraycopy(sideState, 0, gameState.sideState, 0, 0);
            return gameState;
        }
    }

    static class GameAction {
        GamePlayer gamePlayer;
        int cellSideIndex;

        public GameAction(GamePlayer gamePlayer, int cellSideIndex) {
            this.gamePlayer = gamePlayer;
            this.cellSideIndex = cellSideIndex;
        }

        public GamePlayer getPlayer() {
            return gamePlayer;
        }
    }

    enum Side {
        L,
        T,
        R,
        B;
    }

    static class Cell {
        String name;
        Map<Side, CellSide> sideBySide = new HashMap<>();
    }

    static class CellSide {
        List<Cell> cells = new ArrayList<>();
        int index;
    }

    static ValueFunction valueFunction = (state, player) -> {
        return state.playerScore * 10 - state.opponentScore * 10;
    };

    static ActionsFromState actionsFromState = state -> {
        List<GameAction> result = new ArrayList<>();
        for (int i = 0; i < sides.size(); i++) {
            if (!state.sideState[i]) {
                result.add(new GameAction(state.gamePlayer, i));
            }
        }
        return result;
    };

    static StateFromAction stateFromAction = (action, state) -> {
        GameState newState = state.getState();
        CellSide cellSide = sides.get(action.cellSideIndex);
        newState.sideState[action.cellSideIndex] = true;
        newState.gamePlayer = nextPlayer(state.gamePlayer);
        for (Cell cell : cellSide.cells) {
            if (checkCell(cell, state)) {
                if (action.gamePlayer == gamePlayer) {
                    newState.playerScore++;
                } else {
                    newState.opponentScore++;
                }
                newState.gamePlayer = state.gamePlayer;
            }
        }
        return newState;
    };

    static GamePlayer nextPlayer(GamePlayer player) {
        return player == GamePlayer.A ? GamePlayer.B : GamePlayer.A;
    }

    static boolean checkCell(Cell cell, GameState state) {
        return cell.sideBySide.values().stream().allMatch(cellSide -> state.sideState[cellSide.index]);
    }

    static public class MiniMax {
        private final ValueFunction valueFunction;
        private final ActionsFromState actionsFromState;
        private final StateFromAction stateFromAction;
        private final GamePlayer player;
        private final int deep;

        public MiniMax(ValueFunction valueFunction,
                       ActionsFromState actionsFromState,
                       StateFromAction stateFromAction,
                       GamePlayer player,
                       int deep) {
            this.valueFunction = valueFunction;
            this.actionsFromState = actionsFromState;
            this.stateFromAction = stateFromAction;
            this.player = player;
            this.deep = deep;
        }

        public GameAction getNextAction(GameState state) {
            return firstIter(state);
        }

        private GameAction firstIter(GameState state) {
            Collection<GameAction> firstActions = actionsFromState.getActions(state);
            Double maxValue = null;
            GameAction maxAction = null;

            if (firstActions.isEmpty()) throw new RuntimeException();

            for (GameAction action : firstActions) {
                double value = nextIter(stateFromAction.getState(action, state), 0);
                if (maxValue == null || maxValue < value) {
                    maxValue = value;
                    maxAction = action;
                }
            }

            return maxAction;
        }


        private double nextIter(GameState state, int iter) {
            Double result = null;
            Collection<GameAction> actions = actionsFromState.getActions(state);
            iter++;

            if (actions.isEmpty() || iter == deep) {
                result = valueFunction.getValue(state, player);
            } else {
                for (GameAction action : actions) {
                    if (player.equals(action.getPlayer())) {//player
                        GameState newState = stateFromAction.getState(action, state);
                        double value = nextIter(newState, iter);
                        if (result == null || result < value) {
                            result = value;
                        }
                    } else {//opponent
                        GameState newState = stateFromAction.getState(action, state);
                        double value = nextIter(newState, iter);
                        if (result == null || result > value) {
                            result = value;
                        }
                    }
                }
            }

            return result;
        }
    }

    interface ActionsFromState {
        Collection<GameAction> getActions(GameState state);
    }

    interface StateFromAction {
        GameState getState(GameAction action, GameState state);
    }

    interface ValueFunction {
        double getValue(GameState  state, GamePlayer player);
    }
}
