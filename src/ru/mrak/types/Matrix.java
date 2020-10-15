package ru.mrak.types;

import java.util.Arrays;
import java.util.Iterator;

public class Matrix <T> implements Iterable {
    private Object[][] matrix;
    private int rows;
    private int columns;
    
    public Matrix(int rows, int columns, T defaultValue) {
        this.rows = rows;
        this.columns = columns;
        matrix = new Object[rows][];
        for (int row = 0; row < rows; row++) {
            matrix[row] = new Object[columns];
            Arrays.fill(matrix[row], defaultValue);
        }
    }
    
    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        matrix = new Object[rows][];
        for (int row = 0; row < rows; row++) {
            matrix[row] = new Object[columns];
        }
    }
    
    public Matrix(Matrix<T> matrix) {
        this.rows = matrix.rows;
        this.columns = matrix.columns;
        this.matrix = new Object[rows][];
        for (int row = 0; row < rows; row++) {
            this.matrix[row] = new Object[columns];
            System.arraycopy(matrix.matrix[row], 0, this.matrix[row], 0, columns);
        }
    }
    
    public T get(int row, int column) {
        if (row >= 0 && row < rows && column >= 0 && column < columns) {
            return (T)matrix[row][column];
        }
        throw new IndexOutOfBoundsException();
    }
    
    public void set(int row, int column, T value) {
        if (row >= 0 && row < rows && column >= 0 && column < columns) {
            matrix[row][column] = value;
        }
    }
    
    public void set(T... valueList) {
        if (valueList.length != rows * columns) throw new IndexOutOfBoundsException();
        int row = 0;
        int column = 0;
        for (Object value : valueList) {
            matrix[row][column] = value;
            column++;
            if (column == columns) {
                row++;
                column = 0;
            }
        }
    }
    
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int row = 0;
            private int column = 0;
            
            @Override
            public boolean hasNext() {
                return row >= 0 && row < rows && column >= 0 && column < columns;
            }
    
            @Override
            public T next() {
                Object matrix = Matrix.this.matrix[row][column];
                column++;
                if (column == columns) {
                    row++;
                    column = 0;
                }
                return (T) matrix;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                result.append(get(row, col));
                result.append("\t");
            }
            result.append("\n");
        }
        return result.toString();
    }
}
