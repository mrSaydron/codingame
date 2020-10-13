package ru.mrak.aGeometry;

import org.junit.Assert;
import org.junit.Test;

public class MethodsTest {

    @Test
    public void cross1() {
        Line line1 = new Line(new Point(0, 0), new Point(1, 1));
        Line line2 = new Line(new Point(0, 1), new Point(1, 0));

        Point point = Methods.crossLint(line1, line2);
        System.out.println(point);
    }

    @Test
    public void cross2() {
        Line line1 = new Line(new Point(0, 0), new Point(1, 0));
        Line line2 = new Line(new Point(0, 1), new Point(1, 0));

        Point point = Methods.crossLint(line1, line2);
        System.out.println(point);
    }

    @Test
    public void cross3() {
        Line line1 = new Line(new Point(0, 0), new Point(0, 1));
        Line line2 = new Line(new Point(0, 1), new Point(1, 0));

        Point point = Methods.crossLint(line1, line2);
        System.out.println(point);
    }
}