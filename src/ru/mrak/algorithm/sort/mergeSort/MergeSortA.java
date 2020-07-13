package ru.mrak.algorithm.sort.mergeSort;

import ru.mrak.algorithm.sort.Sort;
import ru.mrak.test.check.CheckSortList;

import java.util.ArrayList;
import java.util.List;

/**
 * Сортирока слиянием
 */
public class MergeSortA implements Sort {
    
    @Override
    public <T extends Comparable> List<T> sort(List<T> list) {
        list = new ArrayList<>(list);
        
        if (list.size() > 1) {
            recursiveSort(list, 0, list.size() - 1);
        }
        
        return list;
    }
    
    @SuppressWarnings("unchecked")
    private <T extends Comparable> void recursiveSort(List<T> list, int start, int end) {
        if ((end - start) > 1) {
            //Делим массив попалам
            int index = (end - start) / 2 + start;
            recursiveSort(list, start, index);
            recursiveSort(list, index + 1, end);
            //Объединяем массивы
            merge(list, start, end);
        } else if (start != end) {
            if (list.get(start).compareTo(list.get(end)) > 0) {
                Sort.swap(list, start, end);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    <T extends Comparable> void merge(List<T> list, int start, int end) {
        List<T> list2 = new ArrayList<>(list.subList(start, end + 1));
        
        int leftIndex = start;
        int rightIndex = (end - start) / 2 + start + 1;
        int insertIndex = start;
        
        boolean hasBuffer = false;
        int startBuffer = -1;
        
        while (leftIndex != rightIndex && //Если все элемента левого массив закончились
                rightIndex <= end //Если все элементы правого массива закончились
        ) {
            if (list.get(leftIndex).compareTo(list.get(rightIndex)) < 0) {
                if (hasBuffer) {
                    if (leftIndex == insertIndex) {
                        //Если элемент для вставки совпадает с элементом буфера то просто переставляю на следующий индекс
                        leftIndex++;
                    } else {
                        //Для левого массива используется буфер расположенный в правом массиве
                        //Забираю элемент из буфера, на его место пишу элемент из левого массива
                        Sort.swap(list, leftIndex, insertIndex);
                        movementLeft(list, leftIndex, rightIndex - 1);
    
//                        if (insertIndex < startBuffer) {
//                            //Передвигаю указатель на следующий элемент, если там элемент из правого массива, то указатель в начало буфера
//                            leftIndex++;
//                            if (leftIndex == rightIndex) {
//                                leftIndex = startBuffer;
//                            }
//                        } else {
//                            //Если индекс вставляемого элемента добрался до буфера, то его пора смещать
//                            startBuffer = insertIndex + 1;
//                            movement(list, leftIndex, rightIndex - 1);
//                        }
                    }
                } else {
                    //Буфера еще нет, ни чего делать не надо, просто переставляю индекс
                    leftIndex++;
                }
            } else {
                //Перемещаю элемент из правого массива, на его месте теперь буфер с элементов из левого массива
                Sort.swap(list, rightIndex, insertIndex);
    
                //Если вставляем туда, где расположен указатель на левый массив, то перемещаем его за его значением
                if (insertIndex == leftIndex && hasBuffer) {
                    leftIndex ++;
                    movementRight(list, leftIndex, rightIndex);
                }
                
                //Если буфера еще нет, пора его создать
                if (!hasBuffer) {
                    hasBuffer = true;
                    //startBuffer = rightIndex;
                    leftIndex = rightIndex;
                }
                
                //Перемещаю индекс
                rightIndex ++;
            }
            
            //На каждой итерации вставляется один элемент, поэтому перемещаю индекс для вставки
            insertIndex ++;
        }
        
        //Правый массив закончился, а индекс вставки не указывает на индекс из буфера. Значит элементы буфера не по порядку
        if ((leftIndex - insertIndex) == 1) {
            movementLeft(list, leftIndex - 1, end);
        }
        
        List<T> list1 = list.subList(start, end + 1);
        boolean b = CheckSortList.checkList(list1);
        if (!b) {
            System.out.println("alarm");
        }
    }
    
    private <T extends Comparable> void movementLeft(List<T> list, int start, int end) {
        T temp = list.get(start);
        
        for (int i = start + 1; i <= end; i++) {
            list.set(i - 1, list.get(i));
        }
    
        list.set(end, temp);
    }
    
    private <T extends Comparable> void movementRight(List<T> list, int start, int end) {
        T temp = list.get(end);
        
        for (int i = end - 1; i >= start; i--) {
            list.set(i + 1, list.get(i));
        }
        
        list.set(start, temp);
    }
    
    @Override
    public String getName() {
        return "Сортировка слиянием. Без дополнительной памяти.";
    }
}
