package ru.mrak.test;

import ru.mrak.algorithm.Algorithm;
import ru.mrak.test.testData.TestData;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class Test {
    public static void test(List<Algorithm> algorithmList, List<TestData> dataList, BiConsumer<Algorithm, TestData> consumer, int rounds) {
        System.out.println("-Start-");
        System.out.println();
        
        Map<TestData, AlgorithmData> top = new LinkedHashMap<>();
    
        for (Algorithm algorithm : algorithmList) {
            System.out.println("Algorithm: " + algorithm.getName());
    
            for (TestData testData : dataList) {
                long time = testAlgorithm(algorithm, testData, consumer, rounds);
                LocalTime workTime = LocalTime.ofNanoOfDay(time);
                System.out.println("\t" + testData.getName() + " : " + workTime.format(DateTimeFormatter.ISO_TIME));
                
                if (!top.containsKey(testData)) {
                    top.put(testData, new AlgorithmData());
                }
                AlgorithmData algorithmData = top.get(testData);
                if (algorithmData.time > time) {
                    algorithmData.algorithm = algorithm;
                    algorithmData.data = testData;
                    algorithmData.time = time;
                }
            }
            System.out.println();
        }
    
        System.out.println("TOP");
        for (AlgorithmData algorithmData : top.values()) {
            LocalTime workTime = LocalTime.ofNanoOfDay(algorithmData.time);
            System.out.println(algorithmData.data.getName() + " : " +
                    algorithmData.algorithm.getName() + " : " +
                    workTime.format(DateTimeFormatter.ISO_TIME));
        }
    }
    
    private static long testAlgorithm(Algorithm algorithm, TestData data, BiConsumer<Algorithm, TestData> consumer, int rounds) {
        long start = System.nanoTime();
    
        for (int i = 0; i < rounds; i++) {
            consumer.accept(algorithm, data);
        }
        
        return System.nanoTime() - start;
    }
    
    private static class AlgorithmData {
        Algorithm algorithm;
        TestData data;
        long time = Long.MAX_VALUE;
    }
}
