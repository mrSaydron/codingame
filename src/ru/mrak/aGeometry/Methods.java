package ru.mrak.aGeometry;

public class Methods {

    /**
     * Расстояние от точки до прямой
     */
    public static double distance(Line line, Point point) {
        return Math.abs(line.a * point.x + line.b * point.y + line.c)
                / Math.sqrt(line.a * line.a + line.b * line.b);
    }

    /**
     * Точка пересечения двух прямых
     */
    public static Point crossLint(Line line1, Line line2) {
        double x;
        double y;

        if (line1.a == 0) {
            y = -line1.c / line1.b;
            x = -(line2.b * y + line2.c) / line2.a;
        } else if (line2.a == 0) {
            y = -line2.c / line2.b;
            x = -(line1.b * y + line1.c) / line1.a;
        } else if (line1.b == 0) {
            x = -line1.c / line1.a;
            y = -(line2.a * x + line2.c) / line2.b;
        } else if (line2.b == 0) {
            x = -line2.c / line2.a;
            y = -(line1.a * x + line1.c) / line1.b;
        } else {
            x = (line2.c / line2.b - line1.c / line1.b) / (line1.a / line1.b - line2.a / line2.b);
            y = -(line2.a * x + line2.c) / line2.b;
        }

        return new Point(x, y);
    }
}
