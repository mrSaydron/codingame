package ru.mrak.algorithm.sort.quickSort;

import ru.mrak.algorithm.sort.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Быфстрая сортировка. Разбиение Хора
 */
public class QuickSortA implements Sort {
    @Override
    public <T extends Comparable> List<T> sort(List<T> list) {
        calc(list, 0, list.size());
        return list;
    }
    
    @SuppressWarnings("unchecked")
    private <T extends Comparable> void calc(List<T> list, int startIndex, int endIndex) {
        int length = endIndex - startIndex;
        if (length > 2) {
            int elementIndex = (endIndex - startIndex) / 2 + startIndex;
            T element = list.get(elementIndex);
            
            T buffer = list.get(startIndex);
            list.set(startIndex, element);
            list.set(elementIndex, buffer);
            
            int left = startIndex;
            int right = endIndex;
            T leftElement;
            T rightElement;
            ext:
            while (left < right) {
                do {
                    left++;
                    if (left == endIndex) break ext;
                    leftElement = list.get(left);
                } while (leftElement.compareTo(element) <= 0 && left < right);
                
                do {
                    right--;
                    rightElement = list.get(right);
                } while (rightElement.compareTo(element) > 0 && left < right);
                
                if (left < right) {
                    list.set(left, rightElement);
                    list.set(right, leftElement);
                }
            }
            calc(list, startIndex, left);
            calc(list, left, endIndex);
            
        } else if (length == 2){
            if (list.get(startIndex).compareTo(list.get(startIndex + 1)) > 0) {
                T buffer = list.get(startIndex);
                list.set(startIndex, list.get(startIndex + 1));
                list.set(startIndex + 1, buffer);
            }
        }
    }
    
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(10, 5, 15, 66, 2));
        Sort sort = new QuickSortA();
        System.out.println(sort.sort(list));
    }
    
    @Override
    public String getName() {
        return "Быстрая сортировка. Разбиение Хора.";
    }
}
