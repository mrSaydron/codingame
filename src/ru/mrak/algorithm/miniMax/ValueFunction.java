package ru.mrak.algorithm.miniMax;

@FunctionalInterface
public interface ValueFunction <T extends State, P extends Player> {
    double getValue(T  state, P player);
}
