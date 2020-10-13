package ru.mrak.aGeometry;

public class RightTriangle {
    public double angleA;
    public double angleB;

    public double sideA;
    public double sideB;
    public double sideC;//гипотенуза

    public RightTriangle(double sideA, double sideC) {
        this.sideA = sideA;
        this.sideC = sideC;

        sideB = Math.sqrt(sideC * sideC - sideA * sideA);
        angleA = Math.asin(sideA / sideC);
        angleB = Math.asin(sideB / sideC);
    }
}
