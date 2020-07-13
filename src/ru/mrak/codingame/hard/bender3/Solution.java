package ru.mrak.codingame.hard.bender3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

class Solution {
    
    private static List<Pair<Integer, Double>> testList = new ArrayList<>();
    private static List<Alg> algList = Arrays.asList(new AlgO1(), new AlgOLogN(), new AlgON(), new AlgONLogN(), new AlgON2(), new AlgON2LogN(), new AlgON3(), new AlgO2N());
    
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        testList = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            int num = in.nextInt();
            int t = in.nextInt();
            testList.add(new Pair<>(num, (double)t));
        }
        
        double minK = Double.MAX_VALUE;
        Alg minAlg = null;
        for (Alg alg : algList) {
            double check = alg.check(testList);
            if (check < minK) {
                minAlg = alg;
                minK = check;
            }
            System.err.println(alg.getName() + ": " + check);
        }
        
        System.out.println(minAlg.getName());
    }
        
    private interface Alg {
        default double check(List<Pair<Integer, Double>> testList) {
            double mistake = 0;
            double[] kList = getK(testList);
            if (checkK(kList, testList)) {
                for (Pair<Integer, Double> integerDoublePair : testList) {
                    mistake += mistake(integerDoublePair, kList);
                }
            } else {
                mistake = Double.MAX_VALUE;
            }
            return mistake;
        }
        
        String getName();
        double[] getK(List<Pair<Integer, Double>> testList);
        double mistake(Pair<Integer, Double> test, double[] kList);
        boolean checkK(double[] kList, List<Pair<Integer, Double>> testList);
    }
    
    private static final double oNEdge = 1;
    
    private static class AlgO1 implements Alg {
        @Override
        public String getName() {
            return "O(1)";
        }
    
        @Override
        public double[] getK(List<Pair<Integer, Double>> testList) {
            double[] kList = new double[2];
        
            int n = testList.size();
            double xSum = testList.stream().mapToDouble(Pair::getKey).sum();
            double ySum = testList.stream().mapToDouble(Pair::getValue).sum();
            double xySum = testList.stream().mapToDouble(pair -> pair.getKey() * pair.getValue()).sum();
            double x2Sum = testList.stream().mapToDouble(pair -> Math.pow(pair.getKey(), 2)).sum();
        
            double a = (n * xySum - xSum * ySum) / (n * x2Sum - Math.pow(xSum, 2));
            double b = (ySum - a * xSum) / n;
            System.err.println();
            System.err.println(String.format("%s a:%f b:%f", getName(), a, b));
        
            kList[0] = a;
            kList[1] = b;
        
            return kList;
        }
    
        @Override
        public double mistake(Pair<Integer, Double> test, double[] kList) {
            double mistake = test.getValue() - (kList[0] * test.getKey() + kList[1]);
            return Math.pow(mistake, 2);
        }
    
        @Override
        public boolean checkK(double[] kList, List<Pair<Integer, Double>> testList) {
            return Math.abs(kList[0]) <= oNEdge;
        }
    }
    
    private static final double log2 = Math.log(2);

    private static class AlgOLogN implements Alg {
        @Override
        public String getName() {
            return "O(log n)";
        }
        
        @Override
        public double[] getK(List<Pair<Integer, Double>> testList) {
            double[] kList = new double[2];
        
            int n = testList.size();
            double logXSum = testList.stream().mapToDouble(pair -> Math.log(pair.key) / log2).sum();
            double ySum = testList.stream().mapToDouble(Pair::getValue).sum();
            double logXySum = testList.stream().mapToDouble(pair -> (Math.log(pair.key) / log2) * pair.getValue()).sum();
            double logX2Sum = testList.stream().mapToDouble(pair -> Math.pow(Math.log(pair.key) / log2, 2)).sum();
        
            double a = (n * logXySum - logXSum * ySum) / (n * logX2Sum - Math.pow(logXSum, 2));
            double b = (ySum - a * logXSum) / n;
            System.err.println();
            System.err.println(String.format("%s a:%f b:%f", getName(), a, b));
        
            kList[0] = a;
            kList[1] = b;
        
            return kList;
        }
    
        @Override
        public double mistake(Pair<Integer, Double> test, double[] kList) {
            double mistake = test.getValue() - (kList[0] * Math.log(test.getKey()) / log2 + kList[1]);
            return Math.pow(mistake, 2);
        }
    
        @Override
        public boolean checkK(double[] kList, List<Pair<Integer, Double>> testList) {
            return kList[0] > 1000;
        }
    }
    
    private static class AlgON implements Alg {
        @Override
        public String getName() {
            return "O(n)";
        }
        
        @Override
        public double[] getK(List<Pair<Integer, Double>> testList) {
            double[] kList = new double[2];
            
            int n = testList.size();
            double xSum = testList.stream().mapToDouble(Pair::getKey).sum();
            double ySum = testList.stream().mapToDouble(Pair::getValue).sum();
            double xySum = testList.stream().mapToDouble(pair -> pair.getKey() * pair.getValue()).sum();
            double x2Sum = testList.stream().mapToDouble(pair -> Math.pow(pair.getKey(), 2)).sum();
            
            double a = (n * xySum - xSum * ySum) / (n * x2Sum - Math.pow(xSum, 2));
            double b = (ySum - a * xSum) / n;
            System.err.println();
            System.err.println(String.format("%s a:%f b:%f", getName(), a, b));
            
            kList[0] = a;
            kList[1] = b;
            
            return kList;
        }
        
        @Override
        public double mistake(Pair<Integer, Double> test, double[] kList) {
            double mistake = test.getValue() - (kList[0] * test.getKey() + kList[1]);
            return Math.pow(mistake, 2);
        }
    
        @Override
        public boolean checkK(double[] kList, List<Pair<Integer, Double>> testList) {
            return Math.abs(kList[0]) > oNEdge;
        }
    }
    
    private static class AlgONLogN implements Alg {
        @Override
        public String getName() {
            return "O(n log n)";
        }
        
        @Override
        public double[] getK(List<Pair<Integer, Double>> testList) {
            double[] kList = new double[2];
            
            int n = testList.size();
            double xSum = testList.stream().mapToDouble(pair -> pair.getKey() * Math.log(pair.getKey())).sum();
            double ySum = testList.stream().mapToDouble(Pair::getValue).sum();
            double xySum = testList.stream().mapToDouble(pair -> pair.getKey() * Math.log(pair.getKey()) * pair.getValue()).sum();
            double x2Sum = testList.stream().mapToDouble(pair -> Math.pow(pair.getKey() * Math.log(pair.getKey()), 2)).sum();
            
            double a = (n * xySum - xSum * ySum) / (n * x2Sum - Math.pow(xSum, 2));
            double b = (ySum - a * xSum) / n;
            System.err.println();
            System.err.println(String.format("%s a:%f b:%f", getName(), a, b));
            
            kList[0] = a;
            kList[1] = b;
            
            return kList;
        }
        
        @Override
        public double mistake(Pair<Integer, Double> test, double[] kList) {
            double mistake = test.getValue() - (kList[0] * test.getKey() * Math.log(test.getKey()) + kList[1]);
            return Math.pow(mistake, 2);
        }
    
        @Override
        public boolean checkK(double[] kList, List<Pair<Integer, Double>> testList) {
            return kList[0] > 8;
        }
    }
    
    private static class AlgON2 implements Alg {
        @Override
        public String getName() {
            return "O(n^2)";
        }
        
        @Override
        public double[] getK(List<Pair<Integer, Double>> testList) {
            double[] kList = new double[3];
            
            int n = testList.size();
            
            double x4Sum = testList.stream().mapToDouble(pair -> Math.pow(pair.getKey(), 4)).sum();
            double x2Sum = testList.stream().mapToDouble(pair -> Math.pow(pair.getKey(), 2)).sum();
            double x2ySum = testList.stream().mapToDouble(pair -> Math.pow(pair.getKey(), 2) * pair.getValue()).sum();
            double ySum = testList.stream().mapToDouble(Pair::getValue).sum();
            
            Matrix matrix = new Matrix(2, 2);
            matrix.set(x4Sum, x2Sum,
                       x2Sum, n);
            
            Matrix right = new Matrix(2, 1);
            right.set(x2ySum, ySum);
    
            Matrix calc = Kramer.calc(matrix, right);
    
            kList[0] = calc.get(0, 0);
            kList[1] = calc.get(1, 0);
    
            System.err.println();
            System.err.println(String.format("%s a:%f b:%f", getName(), kList[0], kList[1]));
            
            return kList;
        }
        
        @Override
        public double mistake(Pair<Integer, Double> test, double[] kList) {
            double mistake = test.getValue() - (kList[0] * Math.pow(test.getKey(), 2) + kList[1]);
            return Math.pow(mistake, 2);
        }
        
        @Override
        public boolean checkK(double[] kList, List<Pair<Integer, Double>> testList) {
            return kList[0] > 0.02;
        }
    }
    
    private static class AlgON2LogN implements Alg {
        @Override
        public String getName() {
            return "O(n^2 log n)";
        }
        
        @Override
        public double[] getK(List<Pair<Integer, Double>> testList) {
            double[] kList = new double[2];
            
            int n = testList.size();
            double xSum = testList.stream().mapToDouble(pair -> Math.pow(pair.getKey(), 2) * Math.log(pair.getKey())).sum();
            double ySum = testList.stream().mapToDouble(Pair::getValue).sum();
            double xySum = testList.stream().mapToDouble(pair -> Math.pow(pair.getKey(), 2) * Math.log(pair.getKey()) * pair.getValue()).sum();
            double x2Sum = testList.stream().mapToDouble(pair -> Math.pow(Math.pow(pair.getKey(), 2) * Math.log(pair.getKey()), 2)).sum();
            
            double a = (n * xySum - xSum * ySum) / (n * x2Sum - Math.pow(xSum, 2));
            double b = (ySum - a * xSum) / n;
            System.err.println();
            System.err.println(String.format("%s a:%f b:%f", getName(), a, b));
            
            kList[0] = a;
            kList[1] = b;
            
            return kList;
        }
        
        @Override
        public double mistake(Pair<Integer, Double> test, double[] kList) {
            double mistake = test.getValue() - (kList[0] * Math.pow(test.getKey(), 2) * Math.log(test.getKey()) + kList[1]);
            return Math.pow(mistake, 2);
        }
        
        @Override
        public boolean checkK(double[] kList, List<Pair<Integer, Double>> testList) {
            return kList[0] > 5;
        }
    }
    
    private static class AlgON3 implements Alg {
        @Override
        public String getName() {
            return "O(n^3)";
        }
        
        @Override
        public double[] getK(List<Pair<Integer, Double>> testList) {
            double[] kList = new double[3];
            
            int n = testList.size();
            
            double x6Sum = testList.stream().mapToDouble(pair -> Math.pow(pair.getKey(), 6)).sum();
            double x3Sum = testList.stream().mapToDouble(pair -> Math.pow(pair.getKey(), 3)).sum();
            double x3ySum = testList.stream().mapToDouble(pair -> Math.pow(pair.getKey(), 3) * pair.getValue()).sum();
            double ySum = testList.stream().mapToDouble(Pair::getValue).sum();
            
            Matrix matrix = new Matrix(2, 2);
            matrix.set(x6Sum, x3Sum,
                       x3Sum, n);
            
            Matrix right = new Matrix(2, 1);
            right.set(x3ySum, ySum);
            
            Matrix calc = Kramer.calc(matrix, right);
            
            kList[0] = calc.get(0, 0);
            kList[1] = calc.get(1, 0);
            
            System.err.println();
            System.err.println(String.format("%s a:%f b:%f", getName(), kList[0], kList[1]));
            
            return kList;
        }
        
        @Override
        public double mistake(Pair<Integer, Double> test, double[] kList) {
            double mistake = test.getValue() - (kList[0] * Math.pow(test.getKey(), 3) + kList[1]);
            return Math.pow(mistake, 2);
        }
        
        @Override
        public boolean checkK(double[] kList, List<Pair<Integer, Double>> testList) {
            return kList[0] > 0.02;
        }
    }
    
    private static class AlgO2N implements Alg {
        @Override
        public String getName() {
            return "O(2^n)";
        }
        
        @Override
        public double[] getK(List<Pair<Integer, Double>> testList) {
            double[] kList = new double[2];
    
            int n = testList.size();
    
            double x2Sum = testList.stream().mapToDouble(pair -> Math.pow(pair.getKey(), 2)).sum();
            double xSum = testList.stream().mapToDouble(pair -> pair.getKey()).sum();
            double xLnYSum = testList.stream().mapToDouble(pair -> pair.getKey() * Math.log(pair.getValue()) / log2).sum();
            double lnYSum = testList.stream().mapToDouble(pair -> Math.log(pair.getValue()) / log2).sum();
    
            double a = (n * xLnYSum - xSum * lnYSum) / (n * x2Sum - Math.pow(xSum, 2));
            double B = (lnYSum - a * xSum) / n;
            double b = Math.pow(2, B);
            System.err.println();
            System.err.println(String.format("%s a:%f b:%f", getName(), a, b));
    
            kList[0] = a;
            kList[1] = b;
            
            return kList;
        }
        
        @Override
        public double mistake(Pair<Integer, Double> test, double[] kList) {
            double mistake = test.getValue() - kList[1] * Math.pow(2, test.getKey() * kList[0]);
            return Math.pow(mistake, 2);
        }
        
        @Override
        public boolean checkK(double[] kList, List<Pair<Integer, Double>> testList) {
            int max = testList.stream().mapToInt(Pair::getKey).max().getAsInt();
            return max < 100 && kList[0] > 0.5;
        }
    }
    
    private static class Pair<K,V> {
        private K key;
        private V value;
        
        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
        
        public K getKey() {
            return key;
        }
        
        public V getValue() {
            return value;
        }
    
        public void setKey(K key) {
            this.key = key;
        }
    
        public void setValue(V value) {
            this.value = value;
        }
    }
    
    public static class Matrix implements Iterable {
        private double[][] matrix;
        private int rows;
        private int columns;
        
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
        
        public int getColumns() {
            return columns;
        }
    }
    
    public static class Kramer {
        public static Matrix calc(Matrix matrix, Matrix right) {
            Matrix result = null;
            double determinant = matrix.determinant();
            if (determinant != 0) {
                result = new Matrix(matrix.getColumns(), 1);
                for (int column = 0; column < matrix.getColumns(); column++) {
                    double determinantVar = matrix.insertColumn(column, right).determinant();
                    result.set(column, 0, determinantVar / determinant);
                }
            }
            return result;
        }
    }
}
