package ru.mrak.aGeometry;

import org.junit.Assert;
import org.junit.Test;

public class LineTest {

    @Test
    public void createLine1() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 0);

        Line line = new Line(p1, p2);

        Assert.assertEquals(0, line.angle * 180 / Math.PI, 0.0);
    }

    @Test
    public void createLine2() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 1);

        Line line = new Line(p1, p2);

        Assert.assertEquals(45, line.angle * 180 / Math.PI, 0);
    }

    @Test
    public void createLine3() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0, 1);

        Line line = new Line(p1, p2);

        Assert.assertEquals(90, line.angle * 180 / Math.PI, 0);
    }

    @Test
    public void createLine4() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(-1, 1);

        Line line = new Line(p1, p2);

        Assert.assertEquals(135, line.angle * 180 / Math.PI, 0);
    }

    @Test
    public void createLine5() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(-1, 0);

        Line line = new Line(p1, p2);

        Assert.assertEquals(0, line.angle * 180 / Math.PI, 0);
    }

    @Test
    public void createLine6() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(-1, -1);

        Line line = new Line(p1, p2);

        Assert.assertEquals(45, line.angle * 180 / Math.PI, 0);
    }

    @Test
    public void createLine7() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0, -1);

        Line line = new Line(p1, p2);

        Assert.assertEquals(90, line.angle * 180 / Math.PI, 0);
    }

    @Test
    public void createLine8() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, -1);

        Line line = new Line(p1, p2);

        Assert.assertEquals(135, line.angle * 180 / Math.PI, 0);
    }

    @Test
    public void createLine9() {
        Point p = new Point(0, 0);

        Line line = new Line(p, 45 * Math.PI / 180);

        Assert.assertEquals(100, line.getByX(100), 0.0001);
    }

    @Test
    public void createLine10() {
        Point p = new Point(0, 0);

        Line line = new Line(p, 135 * Math.PI / 180);

        Assert.assertEquals(-100, line.getByX(100), 0.0001);
    }

    @Test
    public void createLine11() {
        Point p = new Point(0, 10);

        Line line = new Line(p, 0 * Math.PI / 180);

        Assert.assertEquals(10, line.getByX(100), 0.0001);
    }
}