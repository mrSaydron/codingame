package ru.mrak.algorithm.miniMax;

@FunctionalInterface
public interface ValueFunction <T extends State> {
    double getValue(T  state);
}
