package ru.mrak.algorithm.sort;

import ru.mrak.algorithm.Algorithm;

import java.util.List;

public interface Sort extends Algorithm {
    <T extends Comparable> List<T> sort(List<T> list);
    
    static void swap(List list, int indexOne, int indexTwo) {
        Object item = list.get(indexOne);
        list.set(indexOne, list.get(indexTwo));
        list.set(indexTwo, item);
    }
}
