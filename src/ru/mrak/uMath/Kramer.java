package ru.mrak.uMath;

public class Kramer {
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
    
    public static void main(String[] args) {
        Matrix matrix = new Matrix(2, 2);
        matrix.set(506, 66, 66, 11);
        
        Matrix right = new Matrix(2, 1);
        right.set(2315.1, 392.3);
    
        Matrix result = calc(matrix, right);
        System.out.println(result);
    }
}
