package ru.mrak.algorithm.miniMax.test;

import org.junit.Test;
import ru.mrak.algorithm.miniMax.Action;
import ru.mrak.algorithm.miniMax.MiniMax;

public class MiniMaxTest {

    @Test
    public void testOne() {
        MiniMax<TicTakToe.GameState, TicTakToe.GameAction, TicTakToe.Cell> miniMax = new MiniMax<>(
                TicTakToe.valueFunction,
                TicTakToe.actionsFromState,
                TicTakToe.stateFromAction,
                TicTakToe.Cell.X,
                10);

        TicTakToe.GameState state = TicTakToe.newGame();
        state.matrix.set(0, 0, TicTakToe.Cell.X);
        state.matrix.set(1, 1, TicTakToe.Cell.X);
        state.matrix.set(0, 2, TicTakToe.Cell.O);
        state.matrix.set(2, 2, TicTakToe.Cell.O);
        Action nextAction = miniMax.getNextAction(state);

        System.out.println(nextAction);
    }
}
