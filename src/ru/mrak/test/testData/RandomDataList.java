package ru.mrak.test.testData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomDataList implements ListData {
    
    private int size;
    private List<Integer> list;
    
    public RandomDataList(int size) {
        this.size = size;
        list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
    
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < size; i++) {
            int indexOne = random.nextInt(size);
            int indexTwo = random.nextInt(size);
        
            Integer temp = list.get(indexOne);
            list.set(indexOne, list.get(indexTwo));
            list.set(indexTwo, temp);
        }
    }
    
    @Override
    public List<Integer> getList() {
        return list;
    }
    
    @Override
    public String getName() {
        return "Случайный порядок. Количество: " + size;
    }
}
