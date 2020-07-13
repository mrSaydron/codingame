package ru.mrak.algorithm;

public interface Algorithm {
    default String getName() {
        return this.getClass().getSimpleName();
    }
}
