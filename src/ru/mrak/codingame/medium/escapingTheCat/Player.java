package ru.mrak.codingame.medium.escapingTheCat;

import java.util.Scanner;

class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int catSpeed = in.nextInt();

        State state = State.TO_CENTER;
        Point nextPoint = new Point(0, 0);

        Point zeroPoint = new Point(0, 0);
        // game loop
        while (true) {
            int mouseX = in.nextInt();
            int mouseY = in.nextInt();
            Point mousePoint = new Point(mouseX, mouseY);

            int catX = in.nextInt();
            int catY = in.nextInt();

            if (mouseX == 0 && mouseY == 0) {
                state = State.DISTANCE;
            }

            switch (state) {
                case TO_CENTER:break;
                case DISTANCE:
                    Line catLine = new Line(zeroPoint, new Point(catX, catY));
                    double distance = distance(catLine, mousePoint);
                    RightTriangle rightTriangle = new RightTriangle(distance, 10);
                    double resultAngle = rightTriangle.angleA + catLine.angle;
                    nextPoint = crossLint(catLine, new Line(mousePoint, resultAngle));

                    break;
                case ESCAPE:break;
                default:
            }

            System.out.println(nextPoint.x + " " + nextPoint.y);
        }
    }

    enum State {
        TO_CENTER,
        DISTANCE,
        ESCAPE,
        ;
    }

    public static class Point {
        public double x;
        public double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class Line {
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
    }

    public static class RightTriangle {
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
