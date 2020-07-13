package ru.mrak.test.testData;

import java.util.ArrayList;
import java.util.List;

public class SortDataList implements ListData {
    
    private int size;
    private List<Integer> list;
    
    public SortDataList(int size) {
        this.size = size;
        list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
    }
    
    @Override
    public List<Integer> getList() {
        return list;
    }
    
    @Override
    public String getName() {
        return "Отсортирован. Количество: " + size;
    }
}
