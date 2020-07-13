package ru.mrak.test.testData;

import java.util.ArrayList;
import java.util.List;

public class ReverseSortDataList implements ListData {
    
    private int size;
    private List<Integer> list;
    
    public ReverseSortDataList(int size) {
        this.size = size;
        list = new ArrayList<>(size);
        for (int i = size - 1; i >= 0; i--) {
            list.add(i);
        }
    }
    
    @Override
    public List<Integer> getList() {
        return list;
    }
    
    @Override
    public String getName() {
        return "Упорядочен в обратном порядке. Количество: " + size;
    }
}
