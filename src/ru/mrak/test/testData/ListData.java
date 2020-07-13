package ru.mrak.test.testData;

import java.util.List;

public interface ListData extends TestData {
    <T extends Comparable> List<T> getList();
}
