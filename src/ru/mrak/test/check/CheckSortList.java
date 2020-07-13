package ru.mrak.test.check;

import java.util.List;

public class CheckSortList {
    public static <T extends Comparable> boolean checkList(List<T> list) {
        boolean result = true;
    
        if (list.size() > 1) {
            for (int i = 0; i < list.size() - 1; i++) {
                if (list.get(i).compareTo(list.get(i + 1)) > 0) {
                    result = false;
                    break;
                }
            }
        }
        
        return result;
    }
}
