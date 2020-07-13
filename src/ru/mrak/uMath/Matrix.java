package ru.mrak.uMath;

import java.util.*;

public class Matrix implements Iterable {
    private double[][] matrix;
    private int rows;
    private int columns;
    
    public Matrix(int rows, int columns, double defaultValue) {
        this.rows = rows;
        this.columns = columns;
        matrix = new double[rows][];
        for (int row = 0; row < rows; row++) {
            matrix[row] = new double[columns];
            Arrays.fill(matrix[row], defaultValue);
        }
    }
    
    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        matrix = new double[rows][];
        for (int row = 0; row < rows; row++) {
            matrix[row] = new double[columns];
        }
    }
    
    public Matrix(Matrix matrix) {
        this.rows = matrix.rows;
        this.columns = matrix.columns;
        this.matrix = new double[rows][];
        for (int row = 0; row < rows; row++) {
            this.matrix[row] = new double[columns];
            System.arraycopy(matrix.matrix[row], 0, this.matrix[row], 0, columns);
        }
    }
    
    public double get(int row, int column) {
        if (row >= 0 && row < rows && column >= 0 && column < columns) {
            return matrix[row][column];
        }
        throw new IndexOutOfBoundsException();
    }
    
    public void set(int row, int column, double value) {
        if (row >= 0 && row < rows && column >= 0 && column < columns) {
            matrix[row][column] = value;
        }
    }
    
    public void set(double... valueList) {
        if (valueList.length != rows * columns) throw new IndexOutOfBoundsException();
        int row = 0;
        int column = 0;
        for (double value : valueList) {
            matrix[row][column] = value;
            column++;
            if (column == columns) {
                row++;
                column = 0;
            }
        }
    }
    
    @Override
    public Iterator iterator() {
        return new Iterator() {
            private int row = 0;
            private int column = 0;
            
            @Override
            public boolean hasNext() {
                return row >= 0 && row < rows && column >= 0 && column < columns;
            }
    
            @Override
            public Object next() {
                Object matrix = Matrix.this.matrix[row][column];
                column++;
                if (column == columns) {
                    row++;
                    column = 0;
                }
                return matrix;
            }
        };
    }
    
    public double determinant() {
        if (columns != rows) throw new IllegalArgumentException();
        double result = 0;
        if (columns == 1) {
            result = matrix[0][0];
        } else if (columns == 2) {
            result = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        } else {
            //forward
            for (int colStart = 0; colStart < rows; colStart++) {
                double turnResult = 1;
                for (int row = 0; row < columns; row++) {
                    int turnColumn = row + colStart;
                    if (turnColumn >= columns) turnColumn -= columns;
                    turnResult *= matrix[row][turnColumn];
                }
                result += turnResult;
            }
            //backward
            for (int colStart = 0; colStart < rows; colStart++) {
                double turnResult = 1;
                for (int row = 0; row < columns; row++) {
                    int turnColumn = colStart - row;
                    if (turnColumn < 0) turnColumn += columns;
                    turnResult *= matrix[row][turnColumn];
                }
                result -= turnResult;
            }
        }
        return result;
    }
    
    public Matrix insertColumn(int columnNumber, Matrix column) {
        Matrix result = new Matrix(this);
        for (int row = 0; row < rows; row++) {
            result.set(row, columnNumber, column.get(row, 0));
        }
        return result;
    }
    
    public int getRows() {
        return rows;
    }
    
    public int getColumns() {
        return columns;
    }
}
