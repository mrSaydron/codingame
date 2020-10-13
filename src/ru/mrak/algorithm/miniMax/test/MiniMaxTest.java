package ru.mrak.algorithm.miniMax.test;

import org.junit.Test;
import ru.mrak.algorithm.miniMax.Action;
import ru.mrak.algorithm.miniMax.MiniMax;

public class MiniMaxTest {

    @Test
    public void testOne() {
        MiniMax<TicTakToe.GameState, TicTakToe.GameAction> miniMax = new MiniMax<>(
                TicTakToe.valueFunction,
                TicTakToe.actionsFromState,
                TicTakToe.stateFromAction,
                TicTakToe.checkEndGame,
                10);

        TicTakToe.GameState state = TicTakToe.newGame();
        Action nextAction = miniMax.getNextAction(state);

        System.out.println(nextAction);
    }
}
