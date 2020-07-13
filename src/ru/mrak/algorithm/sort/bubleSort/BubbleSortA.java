package ru.mrak.algorithm.sort.bubleSort;

import ru.mrak.algorithm.sort.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * Сортировка пузырьком
 */
public class BubbleSortA implements Sort {
    
    @Override
    public <T extends Comparable> List<T> sort(List<T> list) {
        List<T> resultList = new ArrayList<>(list);
        
        if (list.size() > 1) {
            for (int i = resultList.size() - 1; i > 0; i--) {
                boolean fin = true;
                for (int j = 0; j < i; j++) {
                    if (resultList.get(j).compareTo(resultList.get(j + 1)) > 0) {
                        T temp = resultList.get(j);
                        resultList.set(j, resultList.get(j + 1));
                        resultList.set(j + 1, temp);
                
                        fin = false;
                    }
                }
                if (fin) break;
            }
        }
        
        return resultList;
    }
    
    @Override
    public String getName() {
        return "Сортировка пузырьком";
    }
}
