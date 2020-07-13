package ru.mrak.algorithm.sort.quickSort;

import ru.mrak.algorithm.sort.Sort;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Быстроя сортировка. Разбиение Ламуто
 */
public class QuickSortB implements Sort {
    @Override
    public <T extends Comparable> List<T> sort(List<T> list) {
        list = new ArrayList<>(list);
        if (list.size() > 1) {
            Deque<Border> borderDeque = new LinkedList<>();
            borderDeque.addFirst(new Border(0, list.size() - 1));
            
            while (borderDeque.size() > 0) {
                blockSort(list, borderDeque.poll(), borderDeque);
            }
        }
        return list;
    }
    
    @SuppressWarnings("unchecked")
    private <T extends Comparable> void blockSort(List<T> list, Border border, Deque<Border> borderDeque) {
        if ((border.end - border.start) >= 2) {
            T item = list.get(border.end);
            int i = border.start;
            for (int j = border.start; j < border.end; j++) {
                if (list.get(j).compareTo(item) <= 0) {
                    if (j != i) {
                        Sort.swap(list, i, j);
                    }
                    i++;
                }
            }
            Sort.swap(list, i, border.end);
            
            borderDeque.addFirst(new Border(border.start, i - 1));
            borderDeque.addFirst(new Border(i, border.end));
        } else if ((border.end - border.start) == 1) {
            if (list.get(border.start).compareTo(list.get(border.end)) > 0) {
                Sort.swap(list, border.start, border.end);
            }
        }
    }
    
    private static class Border {
        int start;
        int end;
    
        public Border(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
    
    @Override
    public String getName() {
        return "Быстроя сортировка. Разбиение Ламуто.";
    }
}
