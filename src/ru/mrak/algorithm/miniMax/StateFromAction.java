package ru.mrak.algorithm.miniMax;

@FunctionalInterface
public interface StateFromAction <A extends Action, S extends State> {
    S getState(A action, S state);
}
