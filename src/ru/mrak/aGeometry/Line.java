package ru.mrak.aGeometry;

/**
 * Клаас описывающий прямую
 */
public class Line {
    double a;
    double b;
    double c;

    public double angle;

    /**
     * По двум точкам
     */
    public Line(Point p1, Point p2) {
        a = p1.y - p2.y;
        b = p2.x - p1.x;
        c = p1.x * p2.y - p2.x * p1.y;

        if (Math.abs(a) <= Math.abs(b)) {
            angle = Math.atan(a / (-b));
        } else {
            angle = Math.PI / 2.0 - Math.atan(b / (-a));
        }

        if (angle < 0) {
            angle += Math.PI;
        }
    }

    /**
     * По точке и углу
     */
    public Line(Point p, double angle) {
        angle %= 2 * Math.PI;
        this.angle = angle >= 0 ? angle : Math.PI - angle;

        if (angle > Math.PI / 4.0 && angle < Math.PI * 3.0 / 4.0) {
            a = 1;
            b = Math.tan(Math.PI / 2.0 - angle);
        } else {
            a = -Math.tan(angle);
            b = 1;
        }

        c = -(a * p.x + b * p.y);
    }

    /**
     * Возвращает Y по переданному X
     */
    public double getByX(double x) {
        return x * a / (-b) + c / (-b);
    }
}
