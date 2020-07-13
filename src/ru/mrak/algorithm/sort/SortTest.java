package ru.mrak.algorithm.sort;

import ru.mrak.algorithm.Algorithm;
import ru.mrak.algorithm.sort.mergeSort.MergeSortA;
import ru.mrak.algorithm.sort.quickSort.QuickSortA;
import ru.mrak.algorithm.sort.quickSort.QuickSortB;
import ru.mrak.algorithm.sort.quickSort.SystemSort;
import ru.mrak.test.Test;
import ru.mrak.test.testData.ListData;
import ru.mrak.test.testData.RandomDataList;
import ru.mrak.test.testData.ReverseSortDataList;
import ru.mrak.test.testData.SortDataList;
import ru.mrak.test.testData.TestData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

public class SortTest {
    
    public static void main(String[] args) {
        int[] sizeList = {1_000, 10_000, 100_000, 1_000_000, 10_000_000};
        int rounds = 10;
        
        List<Algorithm> algorithms = Arrays.asList(
                //new BubbleSortA(),
                //new SelectSortA(),
                new QuickSortA(),
                //new QuickSortB(),
                new MergeSortA(),
                new SystemSort()
        );
        
        List<TestData> testData = new ArrayList<>();
    
        for (int size : sizeList) testData.add(new SortDataList(size));
        for (int size : sizeList) testData.add(new ReverseSortDataList(size));
        for (int size : sizeList) testData.add(new RandomDataList(size));
        
        BiConsumer<Algorithm, TestData> biConsumer = (alg, data) -> ((Sort) alg).sort(((ListData)data).getList());
        
        Test.test(algorithms, testData, biConsumer, rounds);
    }
    
}
