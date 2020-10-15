package ru.mrak.algorithm.miniMax;

import java.util.Collection;

public class MiniMax <S extends State, A extends Action, P extends Player>{
    private final ValueFunction<S, P> valueFunction;
    private final ActionsFromState<S> actionsFromState;
    private final StateFromAction<A, S> stateFromAction;
    private final P player;
    private final int deep;

    public MiniMax(ValueFunction<S, P> valueFunction,
                   ActionsFromState<S> actionsFromState,
                   StateFromAction<A, S> stateFromAction,
                   P player,
                   int deep) {
        this.valueFunction = valueFunction;
        this.actionsFromState = actionsFromState;
        this.stateFromAction = stateFromAction;
        this.player = player;
        this.deep = deep;
    }

    public A getNextAction(S state) {
        return firstIter(state);
    }

    private A firstIter(S state) {
        Collection<Action> firstActions = actionsFromState.getActions(state);
        Double maxValue = null;
        A maxAction = null;

        if (firstActions.isEmpty()) throw new RuntimeException();

        for (Action action : firstActions) {
            double value = nextIter(stateFromAction.getState((A)action, state), 0);
            if (maxValue == null || maxValue < value) {
                maxValue = value;
                maxAction = (A) action;
            }
        }

        return maxAction;
    }


    private double nextIter(S state, int iter) {
        Double result = null;
        Collection<Action> actions = actionsFromState.getActions(state);
        iter++;

        if (actions.isEmpty() || iter == deep) {
            result = valueFunction.getValue(state, player);
        } else {
            for (Action action : actions) {
                if (player.equals(action.getPlayer())) {
                    S newState = stateFromAction.getState((A) action, state);
                    double value = nextIter(newState, iter);
                    if (result == null || result < value) {
                        result = value;
                    }
                } else {
                    S newState = stateFromAction.getState((A) action, state);
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
