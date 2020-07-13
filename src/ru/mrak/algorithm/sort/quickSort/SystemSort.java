package ru.mrak.algorithm.sort.quickSort;

import ru.mrak.algorithm.sort.Sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class SystemSort implements Sort {
    
    @Override
    public <T extends Comparable> List<T> sort(List<T> list) {
        List<T> result = new ArrayList<>(list);
        result.sort(Comparator.comparing(Function.identity()));
        return result;
    }
    
    @Override
    public String getName() {
        return "Стандартная сортировка";
    }
}
