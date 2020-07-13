package ru.mrak.codingame.hard.thereIsNoSpoon2;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

class Player {
    
    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;
    
    private static Set<Connect> connects = new HashSet<>();
    private static boolean isTest = false;
    
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("test")) isTest = true;
        
        Scanner in = new Scanner(System.in);
        int width = in.nextInt(); // the number of cells on the X axis
        int height = in.nextInt(); // the number of cells on the Y axis
        if (in.hasNextLine()) {
            in.nextLine();
        }
        System.err.println(width + " " + height);
        Array2 field = new Array2(width, height);
        for (int row = 0; row < height; row++) {
            String line = in.nextLine(); // width characters, each either a number or a '.'
            System.err.println(line);
            char[] chars = line.toCharArray();
            for (int col = 0; col < width; col++) {
                field.getPoint(row, col).setNodeCount(chars[col]);
            }
        }
        
        long timeStart = System.currentTimeMillis();
        findConnectors(field);
    
        //Поиск по шаблону
        System.err.println("-----------");
        System.err.println(field.toString());
    
        int calcCount = 0;
        Set<Point> points = new HashSet<>();
        for (Point point : field) {
            if (point.nodeCount > 0) points.add(point);
        }
        int overPoints = points.size();
        IterResult iterResult;
        do {
            iterResult = calcTemplate(points, field);
            points.removeAll(iterResult.calcPoints);
            calcCount += iterResult.calcPoints.size();
        } while (iterResult.calcPoints.size() != 0);
        boolean calc = (calcCount == overPoints);
    
        System.err.println("-----------template result");
        System.err.println(field.toString());
        
        //Поиск перебором
        if (!calc) {
            Deque<Point> queue = new LinkedList<>();
            int allPoints = 0;
            Point firstPoint = null;
            for (Point point : field) {
                if (point.nodeCount > 0) {
                    allPoints++;
                    if (firstPoint == null) firstPoint = point;
                }
            }
            queue.addFirst(firstPoint);
            calc = calc(queue, 1, allPoints, field);
        }
    
        System.err.println("-----------result");
        System.err.println(field.toString());
        
        if (calc) {
            List<Connect> resultList = new ArrayList<>();
            for (Point point : field) {
                if (point.nodeCount > 0) {
                    Connect leftConnect = point.connects[LEFT];
                    if (leftConnect != null && leftConnect.count > 0) resultList.add(leftConnect);
                    
                    Connect downConnect = point.connects[DOWN];
                    if (downConnect != null && downConnect.count > 0) resultList.add(downConnect);
                }
            }
            System.err.println("time: " + (System.currentTimeMillis() - timeStart)/1000.0 + "s");
            for (Connect connect : resultList) {
                System.out.println(connect);
            }
        } else {
            throw new RuntimeException();
        }
    }
    
    private static int getLeftNodeCount(Point point) {
        int sum = Arrays.stream(point.connects)
                .filter(Objects::nonNull)
                .filter(connect -> connect.count > 0)
                .mapToInt(connect -> connect.count)
                .sum();
        return point.nodeCount - sum;
    }
    
    private static class IterResult {
        List<Point> calcPoints;
        List<Point> nextPoints;
    
        public IterResult(List<Point> calcPoints, List<Point> nextPoints) {
            this.calcPoints = calcPoints;
            this.nextPoints = nextPoints;
        }
    }
    
    private static List<OldConnect> setConnect(Connect connect, int count) {
        return setConnect(connect, count, false);
    }
    
    private static class OldConnect {
        private Connect connect;
        private int count;
        private boolean isTemplate;
    
        public OldConnect(Connect connect) {
            this.connect = connect;
            this.count = connect.count;
            this.isTemplate = connect.isTemplate;
        }
        
        public void reset() {
            connect.count = count;
            connect.isTemplate = isTemplate;
        }
    }
    
    private static List<OldConnect> setConnect(Connect connect, int count, boolean isTemplate) {
        List<OldConnect> result = new ArrayList<>();
        result.add(new OldConnect(connect));
        
        int oldCount = connect.count;
        if (connect.count < 0) connect.count = 0;
        connect.count += count;
        if (connect.count > 2) throw new RuntimeException();
        if (connect.count > 0) connect.isTemplate = isTemplate;
        if (connect.count > 0 && oldCount != connect.count)  {
            if (connect.pointOne.row == connect.pointTwo.row) {
                int colMin;
                int colMax;
                if (connect.pointOne.col < connect.pointTwo.col) {
                    colMin = connect.pointOne.col;
                    colMax = connect.pointTwo.col;
                } else {
                    colMin = connect.pointTwo.col;
                    colMax = connect.pointOne.col;
                }
                if (colMax - colMin == 1) return result;
                connects.removeIf(checkConnect -> {
                    boolean isCross = checkConnect.pointOne.col == checkConnect.pointTwo.col
                            && checkConnect.pointOne.col > colMin && checkConnect.pointOne.col < colMax
                            && ((connect.pointOne.row - checkConnect.pointOne.row) * (connect.pointOne.row - checkConnect.pointTwo.row) < 0);
                    if (isCross) {
                        checkConnect.count = 0;
                        result.add(new OldConnect(checkConnect));
                    }
                    return isCross;
                });
            } else {
                int rowMin;
                int rowMax;
                if (connect.pointOne.row < connect.pointTwo.row) {
                    rowMin = connect.pointOne.row;
                    rowMax = connect.pointTwo.row;
                } else {
                    rowMin = connect.pointTwo.row;
                    rowMax = connect.pointOne.row;
                }
                if (rowMax - rowMin == 1) return result;
                connects.removeIf(checkConnect -> {
                    boolean isCross = checkConnect.pointOne.row == checkConnect.pointTwo.row
                            && checkConnect.pointOne.row > rowMin && checkConnect.pointOne.row < rowMax
                            && ((connect.pointOne.col - checkConnect.pointOne.col) * (connect.pointOne.col - checkConnect.pointTwo.col) < 0);
                    if (isCross) {
                        checkConnect.count = 0;
                        result.add(new OldConnect(checkConnect));
                    }
                    return isCross;
                });
            }
        }
        return result;
    }
    
    private static IterResult calcTemplate(Collection<Point> points, Array2 field) {
        List<Point> nextPoints = new ArrayList<>();
        List<Point> calcPoints = new ArrayList<>();
        for (Point point : points) {
            int leftNodeCount = getLeftNodeCount(point);
            List<Connect> connects = new ArrayList<>();
            List<Point> aroundPoints = new ArrayList<>();
            List<Integer> leftNodeFromConnectPointList = new ArrayList<>();
            for (Connect connect : point.connects) {
                if (connect != null && connect.count != 0) {
                    Point nPoint = connect.pointOne.equals(point) ? connect.pointTwo : connect.pointOne;
                    int leftPoint = getLeftNodeCount(nPoint);
                    int leftConnect = 2 - Math.max(connect.count, 0);
                    leftConnect = Math.min(leftConnect, leftPoint);
                    if (leftConnect > 0) {
                        connects.add(connect);
                        aroundPoints.add(nPoint);
                        leftNodeFromConnectPointList.add(leftConnect);
                    }
                }
            }
            
            if (leftNodeCount == 0) {
                calcPoints.add(point);
                for (Connect connect : connects) {
                    if (connect.count < 0) setConnect(connect,0);
                }
                
                if (isTest) {
                    System.err.println("-----------template---");
                    System.err.println("point: " + point.row + " " + point.col);
                    System.err.println(field.toString());
                }
                
                continue;
            }
            if (connects.size() == 1) {
                setConnect(connects.get(0), leftNodeCount);
                nextPoints.add(aroundPoints.get(0));
                calcPoints.add(point);
                
                if (isTest) {
                    System.err.println("-----------template---");
                    System.err.println("point: " + point.row + " " + point.col);
                    System.err.println(field.toString());
                }
                
                continue;
            }
            if ((connects.size() * 2) == leftNodeCount) {
                for (int i = 0; i < connects.size(); i++) {
                    setConnect(connects.get(i), 2);
                    nextPoints.add(aroundPoints.get(i));
                }
                calcPoints.add(point);
    
                if (isTest) {
                    System.err.println("-----------template---");
                    System.err.println("point: " + point.row + " " + point.col);
                    System.err.println(field.toString());
                }
                
                continue;
            }
            boolean hasOneConnect = leftNodeFromConnectPointList.stream().anyMatch(connect -> connect == 1);
            if ((connects.size() * 2 - 1) == leftNodeCount && hasOneConnect) {
                for (int i = 0; i < connects.size(); i++) {
                    if (leftNodeFromConnectPointList.get(i) == 1) setConnect(connects.get(i), 1);
                    else setConnect(connects.get(i), 2);
                    nextPoints.add(aroundPoints.get(i));
                }
                calcPoints.add(point);
    
                if (isTest) {
                    System.err.println("-----------template---");
                    System.err.println("point: " + point.row + " " + point.col);
                    System.err.println(field.toString());
                }
                
                continue;
            }
            int sum = leftNodeFromConnectPointList.stream().reduce(0, Integer::sum);
            if (sum == leftNodeCount) {
                for (int i = 0; i < connects.size(); i++) {
                    setConnect(connects.get(i), leftNodeFromConnectPointList.get(i));
                    nextPoints.add(aroundPoints.get(i));
                }
                calcPoints.add(point);
    
                if (isTest) {
                    System.err.println("-----------template---");
                    System.err.println("point: " + point.row + " " + point.col);
                    System.err.println(field.toString());
                }
                
                continue;
            }
            if (leftNodeCount == 4 && connects.size() == 3 && leftNodeFromConnectPointList.stream().filter(left -> left == 1).count() == 2) {
                for (int i = 0; i < connects.size(); i++) {
                    if (leftNodeFromConnectPointList.get(i) == 1) setConnect(connects.get(i), 1);
                    else setConnect(connects.get(i), 2);
                }
                calcPoints.add(point);
    
                if (isTest) {
                    System.err.println("-----------template---");
                    System.err.println("point: " + point.row + " " + point.col);
                    System.err.println(field.toString());
                }
                
                continue;
            }
            if (leftNodeCount == 7 && connects.size() == 4) {
                for (Connect connect : connects) {
                    if (connect.count <= 0) setConnect(connect, 1, true);
                }
    
                if (isTest) {
                    System.err.println("-----------template---");
                    System.err.println("point: " + point.row + " " + point.col);
                    System.err.println(field.toString());
                }
                
                continue;
            }
            if (leftNodeCount == 5 && connects.size() == 3) {
                for (Connect connect : connects) {
                    if (connect.count <= 0) setConnect(connect, 1, true);
                }
    
                if (isTest) {
                    System.err.println("-----------template---");
                    System.err.println("point: " + point.row + " " + point.col);
                    System.err.println(field.toString());
                }
                
                continue;
            }
            if (leftNodeCount == 3 && connects.size() == 2) {
                for (Connect connect : connects) {
                    if (connect.count <= 0) setConnect(connect, 1, true);
                }
    
                if (isTest) {
                    System.err.println("-----------template---");
                    System.err.println("point: " + point.row + " " + point.col);
                    System.err.println(field.toString());
                }
                
                continue;
            }
        }
    
        if (isTest) {
            System.err.println("-----------template---");
            System.err.println(field.toString());
        }
        
        return new IterResult(calcPoints, nextPoints);
    }
    
    private static boolean calc(Deque<Point> queue, int pointsCount, int allPoints, Array2 field) {
        Point point = queue.pollFirst();
        List<Point> saveQueue = new LinkedList<>(queue);
    
        if (isTest) {
            System.err.println("-----------calc-------");
            System.err.println("pointsCount: " + pointsCount);
            System.err.println("point: " + (point != null ? point.row + " " + point.col : "null"));
            System.err.println(field.toString());
        }
        
        if (point == null) {
            return false;
        }
        int leftNodeCount = getLeftNodeCount(point);
        if (pointsCount == allPoints && leftNodeCount == 0) {
            return true;
        }
        if (point.isCalc) {
            return calc(queue, pointsCount, allPoints, field);
        }
        point.isCalc = true;
        
        if (pointsCount == allPoints && leftNodeCount != 0) {
            point.isCalc = false;
            return false;
        }
        if (leftNodeCount == 0) {
            List<Point> points = Arrays.stream(point.connects)
                    .filter(Objects::nonNull)
                    .filter(connect -> connect.count > 0)
                    .map(connect -> connect.pointOne.equals(point) ? connect.pointTwo : connect.pointOne)
                    .filter(nextPoint -> !nextPoint.isCalc)
                    .collect(Collectors.toList());
            queue.addAll(points);
            
            boolean calc = calc(queue, pointsCount + 1, allPoints, field);
            if (calc) return true;
            point.isCalc = false;
            return false;
        }
        
        Connect[] connects = Arrays.stream(point.connects)
                .filter(Objects::nonNull)
                .filter(connect -> connect.count < 0 || connect.isTemplate)
                .toArray(Connect[]::new);
        if (leftNodeCount > 0 && connects.length == 0) {
            point.isCalc = false;
            return false;
        }
        
        int[] leftNodeFromConnectPointList = Arrays.stream(connects)
                .map(connect -> connect.pointOne.equals(point) ? connect.pointTwo : connect.pointOne)
                .mapToInt(Player::getLeftNodeCount)
                .toArray();
        
        ValueGen valueGen = new ValueGen(connects.length, leftNodeCount);
        int[] values;
        next:
        while ((values = valueGen.next()) != null) {
            for (int i = 0; i < values.length; i++) {
                if (values[i] > leftNodeFromConnectPointList[i]) continue next;
                if (connects[i].isTemplate && values[i] > 1) continue next;
            }
            
            List<OldConnect> oldConnectList = new ArrayList<>();
            for (int i = 0; i < values.length; i++) {
                List<OldConnect> oldConnects = setConnect(connects[i], values[i]);
                if (oldConnects != null) oldConnectList.addAll(oldConnects);
            }
    
            List<Point> points = Arrays.stream(point.connects)
                    .filter(Objects::nonNull)
                    .filter(connect -> connect.count > 0)
                    .map(connect -> connect.pointOne.equals(point) ? connect.pointTwo : connect.pointOne)
                    .filter(nextPoint -> !nextPoint.isCalc)
                    .collect(Collectors.toList());
            queue.addAll(points);
            
            boolean calc = calc(queue, pointsCount + 1, allPoints, field);
            if (calc) return true;
            
            queue = new LinkedList<>(saveQueue);
            for (OldConnect oldConnect : oldConnectList) {
                oldConnect.reset();
            }
        }
        point.isCalc = false;
        return false;
    }
    
    private static void findConnectors(Array2 field) {
        for (Point point : field) {
            if (point.nodeCount > 0) {
                for (int col = 1; col < field.width; col++) {
                    Point connectPoint = field.getPoint(point.row, point.col + col);
                    if (connectPoint == null) break;
                    if (connectPoint.nodeCount > 0) {
                        Connect connect = new Connect(point, connectPoint, -1);
                        point.connects[RIGHT] = connect;
                        connectPoint.connects[LEFT] = connect;
                        connects.add(connect);
                        break;
                    }
                }
                for (int row = 1; row < field.height; row++) {
                    Point connectPoint = field.getPoint(point.row + row, point.col);
                    if (connectPoint == null) break;
                    if (connectPoint.nodeCount > 0) {
                        Connect connect = new Connect(point, connectPoint, -1);
                        point.connects[DOWN] = connect;
                        connectPoint.connects[UP] = connect;
                        connects.add(connect);
                        break;
                    }
                }
            }
        }
    }
    
    
    private static class Array2 implements Iterable<Point> {
        int width;
        int height;
        
        Point[][] points;
        
        public Array2(int width, int height) {
            this.width = width;
            this.height = height;
            
            points = new Point[height][];
            for (int row = 0; row < height; row++) {
                points[row] = new Point[width];
                for (int col = 0; col < width; col++) {
                    points[row][col] = new Point(row, col);
                }
            }
        }
        
        public Point getPoint(int row, int col) {
            Point result = null;
            if (row >= 0 && row < height && col >= 0 && col < width) {
                result = points[row][col];
            }
            return result;
        }
        
        @Override
        public Iterator<Point> iterator() {
            return new Iterator<Point>() {
                int row = 0;
                int col = 0;
                
                @Override
                public boolean hasNext() {
                    return row < height && col < width;
                }
                
                @Override
                public Point next() {
                    Point point = points[row][col];
                    col++;
                    if (col == width) {
                        col = 0;
                        row++;
                    }
                    return point;
                }
            };
        }
        
        @Override
        public String toString() {
            List<String> strings = new ArrayList<>();
            for (int row = 0; row < height; row++) {
                StringBuilder upString = new StringBuilder();
                StringBuilder downString = new StringBuilder();
                for (int col = 0; col < width; col++) {
                    int nodeCount = points[row][col].nodeCount;
                    upString.append(nodeCount > 0 ? nodeCount : " ");
                    Connect rightConnect = points[row][col].connects[RIGHT];
                    
                    String rightString = " ";
                    if (rightConnect != null && rightConnect.count == 1) {
                        rightString = "-";
                    } else if (rightConnect != null && rightConnect.count == 2) {
                        rightString = "=";
                    }
                    upString.append(rightString);
                    
                    Connect dConnect = points[row][col].connects[DOWN];
                    String dString = " ";
                    if (dConnect != null && dConnect.count == 1) {
                        dString = "|";
                    } else if (dConnect != null && dConnect.count == 2) {
                        dString = "\u0965";
                    }
                    downString.append(dString);
                    downString.append(" ");
                }
                strings.add(upString.toString());
                strings.add(downString.toString());
            }
            return String.join("\n", strings);
        }
    }
    
    private static class Point implements Cloneable {
        int row;
        int col;
        int nodeCount;
        Connect[] connects = new Connect[4];
        boolean isCalc;
        
        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }
        
        public void setNodeCount(char nodeCount) {
            if (nodeCount == '.') {
                this.nodeCount = 0;
            } else {
                this.nodeCount = Integer.parseInt(nodeCount + "");
            }
        }
        
        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return row == point.row &&
                    col == point.col;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }
    
    private static class Connect implements Cloneable {
        Point pointOne;
        Point pointTwo;
        int count;
        boolean isTemplate;
        
        public Connect(Point pointOne, Point pointTwo, int count) {
            this.pointOne = pointOne;
            this.pointTwo = pointTwo;
            this.count = count;
        }
        
        @Override
        public String toString() {
            return pointOne.col + " " + pointOne.row + " " + pointTwo.col + " " + pointTwo.row + " " + count;
        }
        
        @Override
        protected Object clone() throws CloneNotSupportedException {
            Connect clone = (Connect) super.clone();
            clone.pointOne = (Point) this.pointOne.clone();
            clone.pointTwo = (Point) this.pointTwo.clone();
            return clone;
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Connect connect = (Connect) o;
            return pointOne.equals(connect.pointOne) &&
                    pointTwo.equals(connect.pointTwo);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(pointOne, pointTwo);
        }
    }
    
    private static class ValueGen {
        private int volume;
        private int sum;
        private int maxValue = 2;
        
        private int[] values;
        
        public ValueGen(int volume, int sum) {
            this.volume = volume;
            this.sum = sum;
        }
        
        public boolean setValue(int index, int value) {
            if (((volume - index) * maxValue) < value) return false;
            for (int i = index; i < values.length; i++) {
                if (value >= maxValue) {
                    values[i] = maxValue;
                    value -= maxValue;
                } else {
                    values[i] = value;
                    break;
                }
            }
            return true;
        }
        
        public int[] next() {
            if (values == null) {
                this.values = new int[volume];
                boolean setValue = setValue(0, sum);
                if (!setValue) values = null;
                return values;
            }
            int index = -1;
            values[volume - 1] = 0;
            for (int i = volume - 2; i >= 0; i--) {
                if (values[i] > 0) {
                    values[i] -= 1;
                    if (setValue(i + 1, sum - getCurrentSum())) {
                        index = i;
                        break;
                    } else {
                        values[i] = 0;
                    }
                }
            }
            if (index < 0) {
                values = null;
            }
            return values;
        }
        
        private int getCurrentSum() {
            return Arrays.stream(values).sum();
        }
    }
}