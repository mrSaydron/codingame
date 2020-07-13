package ru.mrak.test.testData;

public interface TestData {
    default String getName() {
        return this.getClass().getSimpleName();
    }
}
