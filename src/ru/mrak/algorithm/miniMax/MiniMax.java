package ru.mrak.algorithm.miniMax;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;

public class MiniMax <S extends State, A extends Action>{
    private final ValueFunction<S> valueFunction;
    private final ActionsFromState<S> actionsFromState;
    private final StateFromAction<A, S> stateFromAction;
    private final CheckEndGame<S> checkEndGame;
    private final int deep;

    private double maxValue;
    private Action maxAction;
    private final Deque<StateAndDeep> stateToWork = new LinkedList<>();

    public MiniMax(ValueFunction<S> valueFunction,
                   ActionsFromState<S> actionsFromState,
                   StateFromAction<A, S> stateFromAction,
                   CheckEndGame<S> checkEndGame,
                   int deep) {
        this.valueFunction = valueFunction;
        this.actionsFromState = actionsFromState;
        this.stateFromAction = stateFromAction;
        this.checkEndGame = checkEndGame;

        this.deep = deep;
    }

    public Action getNextAction(S state) {
        maxValue = -10000.0;

        firstIter(state);
        nexIter();

        return maxAction;
    }

    private void firstIter(S state) {
        Collection<Action> firstActions = actionsFromState.getActions(state);

        if (deep == 0) {
            for (Action firstAction : firstActions) {
                S newState = stateFromAction.getState((A)firstAction, state);
                double newValue = valueFunction.getValue(newState);
                if (maxValue < newValue) {
                    maxValue = newValue;
                    maxAction = firstAction;
                }
            }
        } else {
            for (Action firstAction : firstActions) {
                S newState = stateFromAction.getState((A)firstAction, state);
                stateToWork.addLast(new StateAndDeep(newState, 1, (A)firstAction));
            }
        }
    }


    private void nexIter() {
        while (stateToWork.size() > 0) {
            StateAndDeep stateAndDeep = stateToWork.pollFirst();

            boolean endGame = checkEndGame.isEndGame(stateAndDeep.state);
            if (endGame) {
                double newValue = valueFunction.getValue(stateAndDeep.state);
                if (maxValue < newValue) {
                    maxValue = newValue;
                    maxAction = stateAndDeep.firstAction;
                }
                continue;
            }

            Collection<Action> actions = actionsFromState.getActions(stateAndDeep.state);

            if (deep == stateAndDeep.deep) {
                for (Action action : actions) {
                    S newState = stateFromAction.getState((A)action, stateAndDeep.state);
                    double newValue = valueFunction.getValue(newState);
                    if (maxValue < newValue) {
                        maxValue = newValue;
                        maxAction = stateAndDeep.firstAction;
                    }
                }
            } else {
                for (Action action : actions) {
                    S newState = stateFromAction.getState((A)action, stateAndDeep.state);
                    stateToWork.addLast(new StateAndDeep(newState, stateAndDeep.deep + 1, stateAndDeep.firstAction));
                }
            }
        }
    }

    private class StateAndDeep {
        S state;
        int deep;
        A firstAction;

        public StateAndDeep(S state, int deep, A firstAction) {
            this.state = state;
            this.deep = deep;
            this.firstAction = firstAction;
        }
    }


}
