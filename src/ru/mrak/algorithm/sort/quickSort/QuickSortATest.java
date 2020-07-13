package ru.mrak.algorithm.sort.quickSort;

import org.junit.Assert;
import org.junit.Test;
import ru.mrak.test.check.CheckSortList;
import ru.mrak.test.testData.RandomDataList;
import ru.mrak.test.testData.ReverseSortDataList;
import ru.mrak.test.testData.SortDataList;

import java.util.List;

public class QuickSortATest {
    
    @Test
    public void reverseSortData() {
        List<Integer> list = (new ReverseSortDataList(100)).getList();
        List<Integer> sortList = (new QuickSortA()).sort(list);
        //System.out.println(sortList);
        
        boolean check = CheckSortList.checkList(sortList);
        Integer listSum = list.stream().reduce(Integer::sum).get();
        Integer sortSum = sortList.stream().reduce(Integer::sum).get();
        Assert.assertTrue(check && listSum.equals(sortSum));
    }
    
    @Test
    public void sortData() {
        List<Integer> list = (new SortDataList(100)).getList();
        List<Integer> sortList = (new QuickSortA()).sort(list);
        //System.out.println(sortList);
        
        boolean check = CheckSortList.checkList(sortList);
        Integer listSum = list.stream().reduce(Integer::sum).get();
        Integer sortSum = sortList.stream().reduce(Integer::sum).get();
        Assert.assertTrue(check && listSum.equals(sortSum));
    }
    
    @Test
    public void randomData() {
        List<Integer> list = (new RandomDataList(100)).getList();
        List<Integer> sortList = (new QuickSortA()).sort(list);
        //System.out.println(sortList);
        
        boolean check = CheckSortList.checkList(sortList);
        Integer listSum = list.stream().reduce(Integer::sum).get();
        Integer sortSum = sortList.stream().reduce(Integer::sum).get();
        Assert.assertTrue(check && listSum.equals(sortSum));
    }
    
}