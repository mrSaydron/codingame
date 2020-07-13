package ru.mrak.algorithm.sort.selectSort;

import ru.mrak.algorithm.sort.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * Сортировка вставкой
 */
public class SelectSortA implements Sort {
    @Override
    public <T extends Comparable> List<T> sort(List<T> list) {
        List<T> result = new ArrayList<T>(list);
    
        if (result.size() > 1) {
            for (int i = 0; i < result.size() - 1; i++) {
                T choiceItem = result.get(i);
                int choiceIndex = i;
                for (int j = i + 1; j < result.size(); j++) {
                    if (result.get(j).compareTo(choiceItem) < 0) {
                        choiceItem = result.get(j);
                        choiceIndex = j;
                    }
                }
                result.set(choiceIndex, result.get(i));
                result.set(i, choiceItem);
            }
        }
        
        return result;
    }
    
    @Override
    public String getName() {
        return "Сортировка вставкой.";
    }
}
