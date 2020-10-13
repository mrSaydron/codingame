package ru.mrak.algorithm.miniMax;

import java.util.Collection;

@FunctionalInterface
public interface ActionsFromState <T extends State> {
    Collection<Action> getActions(T state);
}
