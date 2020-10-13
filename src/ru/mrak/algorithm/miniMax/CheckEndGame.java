package ru.mrak.algorithm.miniMax;

@FunctionalInterface
public interface CheckEndGame <T extends State> {
    boolean isEndGame(T state);
}
