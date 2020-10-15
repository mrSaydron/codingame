package ru.mrak.algorithm.miniMax.test;

import ru.mrak.algorithm.miniMax.*;
import ru.mrak.types.Matrix;

import java.util.*;

public class TicTakToe {

    public static class GameState implements State {
        Matrix<Cell> matrix = new Matrix<>(3, 3, Cell.e);
        Cell player;

        public Player getPlayer() {
            return player;
        }
    }

    public enum  Cell implements Player {
        e,
        X,
        O;

        static List<Cell> players = Arrays.asList(Cell.X, Cell.O);
    }

    public static class GameAction implements Action {
        Cell player;
        int row;
        int column;

        public GameAction(Cell player, int row, int column) {
            this.player = player;
            this.row = row;
            this.column = column;
        }

        public Cell getPlayer() {
            return player;
        }

        @Override
        public String toString() {
            return "GameAction{" +
                    "player=" + player +
                    ", row=" + row +
                    ", column=" + column +
                    '}';
        }
    }

    private static boolean checkWin(GameState state, Cell player) {
        boolean result = false;
        Matrix<Cell> matrix = state.matrix;
        for (int i = 0; i < 3; i++) {
            int countRow = 0;
            int countColl = 0;
            for (int j = 0; j < 3; j++) {
                if (matrix.get(i, j).equals(player)) countRow++;
                if (matrix.get(j, i).equals(player)) countColl++;
            }
            result = countRow == 3 || countColl == 3;
            if (result) break;
        }

        if (matrix.get(0, 0).equals(player)
                && matrix.get(1, 1).equals(player)
                && matrix.get(2, 2).equals(player)) {
            result = true;
        }

        if (matrix.get(2, 0).equals(player)
                && matrix.get(1, 1).equals(player)
                && matrix.get(0, 2).equals(player)) {
            result = true;
        }
        return result;
    }

    private static boolean checkDraw(GameState state) {
        boolean result = true;
        end:
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state.matrix.get(i, j).equals(Cell.e)) {
                    result = false;
                    break end;
                }
            }
        }
        return result;
    }

    public static ActionsFromState<GameState> actionsFromState = state -> {
        List<Action> result = new ArrayList<>();
        boolean win = false;
        for (Cell player : Cell.players) {
            win = win || checkWin(state, player);
        }
        if (!win) {
            Cell nextPlayer = nextPlayer(state.player);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (state.matrix.get(i, j).equals(Cell.e)) {
                        result.add(new GameAction(nextPlayer, i, j));
                    }
                }
            }
        }
        return result;
    };

    private static Cell nextPlayer(Cell player) {
        if (Cell.players.get(0).equals(player)) {
            return Cell.players.get(1);
        }
        return Cell.players.get(0);
    }

    public static ValueFunction<GameState, Cell> valueFunction = (state, player) -> {
        double result = 0;
        if (checkWin(state, player)) {
            result = 100;
        } else if (checkWin(state, player == Cell.X ? Cell.O : Cell.X)) {
            result = -100;
        }
        return result;
    };

    public static StateFromAction<GameAction, GameState> stateFromAction = (action, state) -> {
        GameState newState = new GameState();
        newState.matrix = new Matrix<>(state.matrix);
        newState.matrix.set(action.row, action.column, action.player);
        newState.player = action.player;
        return newState;
    };

    public static GameState newGame() {
        GameState state = new GameState();
        state.matrix = new Matrix<>(3, 3, Cell.e);
        state.player = Cell.O;
        return state;
    }

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        GameState state = newGame();
        Map<Cell, Avatar> turn = new HashMap<>();

        for (Cell player : Cell.players) {
            System.out.print(player + ": ");
            String playerName = scanner.next();
            if (playerName.contains("miniMax")) {
                MiniMaxPlayer mmPlayer = new MiniMaxPlayer();
                mmPlayer.name = playerName;
                mmPlayer.player = player;
                mmPlayer.miniMax = new MiniMax<>(
                        valueFunction,
                        actionsFromState,
                        stateFromAction,
                        player,
                        10);

                turn.put(player, mmPlayer);
            } else {
                HumanPlayer humanPlayer = new HumanPlayer();
                humanPlayer.name = playerName;
                humanPlayer.player = player;

                turn.put(player, humanPlayer);
            }
        }

        System.out.println("start game");
        while(true) {
            Cell player = nextPlayer(state.player);
            Avatar avatar = turn.get(player);
            System.out.println("Turn of: " + player + " (" + avatar.name + ")");

            state = avatar.turn(state);

            System.out.println(state.matrix);
            if (checkWin(state, player)) {
                System.out.println("Win: " + avatar.name);
                break;
            }
            if (checkDraw(state)) {
                System.out.println("Game draw");
                break;
            }
        }
    }

    static abstract class Avatar {
        String name;
        Cell player;

        abstract GameState turn(GameState state);
    }

    static class HumanPlayer extends Avatar {
        GameState turn(GameState state) {
            System.out.print("print turn: ");
            String row = scanner.next();
            String col = scanner.next();
            GameAction action = new GameAction(player, Integer.parseInt(row), Integer.parseInt(col));
            return stateFromAction.getState(action, state);
        }
    }

    static class MiniMaxPlayer extends Avatar {
        MiniMax<GameState, GameAction, Cell> miniMax;

        @Override
        GameState turn(GameState state) {
            GameAction nextAction = (GameAction) miniMax.getNextAction(state);
            return stateFromAction.getState(nextAction, state);
        }
    }
}
