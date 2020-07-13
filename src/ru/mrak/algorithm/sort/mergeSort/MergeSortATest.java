package ru.mrak.algorithm.sort.mergeSort;

import org.junit.Assert;
import org.junit.Test;
import ru.mrak.test.check.CheckSortList;
import ru.mrak.test.testData.RandomDataList;
import ru.mrak.test.testData.ReverseSortDataList;
import ru.mrak.test.testData.SortDataList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeSortATest {
    @Test
    public void reverseSortData() {
        List<Integer> list = (new ReverseSortDataList(100)).getList();
        List<Integer> sortList = (new MergeSortA()).sort(list);
        System.out.println(sortList);
        
        boolean check = CheckSortList.checkList(sortList);
        Integer listSum = list.stream().reduce(Integer::sum).get();
        Integer sortSum = sortList.stream().reduce(Integer::sum).get();
        Assert.assertTrue(check && listSum.equals(sortSum));
    }
    
    @Test
    public void sortData() {
        List<Integer> list = (new SortDataList(100)).getList();
        List<Integer> sortList = (new MergeSortA()).sort(list);
        System.out.println(sortList);
        
        boolean check = CheckSortList.checkList(sortList);
        Integer listSum = list.stream().reduce(Integer::sum).get();
        Integer sortSum = sortList.stream().reduce(Integer::sum).get();
        Assert.assertTrue(check && listSum.equals(sortSum));
    }
    
    @Test
    public void randomData() {
        List<Integer> list = (new RandomDataList(1000)).getList();
        List<Integer> sortList = (new MergeSortA()).sort(list);
        //System.out.println(sortList);
        
        boolean check = CheckSortList.checkList(sortList);
        Integer listSum = list.stream().reduce(Integer::sum).get();
        Integer sortSum = sortList.stream().reduce(Integer::sum).get();
        Assert.assertTrue(check && listSum.equals(sortSum));
    }
    
    @Test
    public void mergeOne() {
        MergeSortA sort = new MergeSortA();
        ArrayList<Integer> integers = new ArrayList<>(Arrays.asList(5, 73, 19));
        sort.merge(integers, 0, 2);
        Assert.assertTrue(CheckSortList.checkList(integers));
    }
    
    @Test
    public void mergeTwo() {
        MergeSortA sort = new MergeSortA();
        ArrayList<Integer> integers = new ArrayList<>(Arrays.asList(4, 46, 54, 61, 81, 85, 91, 1, 10, 27, 32, 39, 99));
        sort.merge(integers, 0, 12);
        Assert.assertTrue(CheckSortList.checkList(integers));
    }
    
    @Test
    public void mergeThree() {
        MergeSortA sort = new MergeSortA();
        ArrayList<Integer> integers = new ArrayList<>(Arrays.asList(1, 32, 35, 48, 62, 67, 95, 0, 7, 13, 22, 39, 82));
        sort.merge(integers, 0, 12);
        Assert.assertTrue(CheckSortList.checkList(integers));
    }
    
    @Test
    public void mergeFour() {
        MergeSortA sort = new MergeSortA();
        ArrayList<Integer> integers = new ArrayList<>(Arrays.asList(4, 10, 21, 58, 86, 96, 19, 22, 24, 26, 60, 75));
        sort.merge(integers, 0, 11);
        Assert.assertTrue(CheckSortList.checkList(integers));
    }
    
    @Test
    public void mergeFive() {
        MergeSortA sort = new MergeSortA();
        ArrayList<Integer> integers = new ArrayList<>(Arrays.asList(24, 28, 42, 43, 65, 70, 15, 25, 30, 66, 71, 84));
        sort.merge(integers, 0, 11);
        Assert.assertTrue(CheckSortList.checkList(integers));
    }
    
    @Test
    public void mergeSix() {
        MergeSortA sort = new MergeSortA();
        ArrayList<Integer> integers = new ArrayList<>(Arrays.asList(16, 24, 79, 90, 92, 93, 7, 27, 30, 39, 50, 60));
        sort.merge(integers, 0, 11);
        Assert.assertTrue(CheckSortList.checkList(integers));
    }
}