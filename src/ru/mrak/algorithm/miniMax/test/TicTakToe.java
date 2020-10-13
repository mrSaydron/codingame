package ru.mrak.algorithm.miniMax.test;

import ru.mrak.algorithm.miniMax.*;
import ru.mrak.types.Matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicTakToe {

    public static class GameState implements State {
        Matrix<Cell> matrix = new Matrix<>(3, 3, Cell.empty);
        Cell player;
    }

    public enum  Cell {
        empty,
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

        @Override
        public String toString() {
            return "GameAction{" +
                    "player=" + player +
                    ", row=" + row +
                    ", column=" + column +
                    '}';
        }
    }

    public static CheckEndGame<GameState> checkEndGame = state -> {
        boolean result = false;
        for (Cell player : Cell.players) {
            result = result || checkWin(state, player);
        }
        result = result || checkDraw(state);
        return result;
    };

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
                if (state.matrix.get(i, j).equals(Cell.empty)) {
                    result = false;
                    break end;
                }
            }
        }
        return result;
    }

    public static ActionsFromState<GameState> actionsFromState = state -> {
        List<Action> result = new ArrayList<>();
        Cell nextPlayer = nextPlayer(state.player);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state.matrix.get(i, j).equals(Cell.empty)) {
                    result.add(new GameAction(nextPlayer, i, j));
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

    public static ValueFunction<GameState> valueFunction = state -> {
        double result = 0;
        if (checkWin(state, Cell.X)) {
            result = 100;
        } else if (checkWin(state, Cell.O)) {
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
        state.matrix = new Matrix<>(3, 3, Cell.empty);
        state.player = Cell.O;
        return state;
    }
}
