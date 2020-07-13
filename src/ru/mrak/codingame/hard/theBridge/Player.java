package ru.mrak.codingame.hard.theBridge;

import java.util.*;

class Player {

    private static final int LINES = 4;
    private static final char HOLE = '0';
    private static final char SAFE = '.';

    private static int motorBikeControl;
    private static int minimumMotorBike;
    private static String[] line = new String[LINES];
    private static int bridgeLength;

    private static final int WARNING_UP_LINE = -1;
    private static final int WARNING_DOWN_LINE = 4;

    private static final String BIKE_LINE_REGULAR = "^\\.+.$";
    private static final String MOVE_LINE_REGULAR = "^.\\.+$";

    private static final long MAX_CALC_TIME = 130L;

    private static Deque<Node> stack;
    private static Node winNode;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        motorBikeControl = in.nextInt(); // the amount of motorbikes to control
        minimumMotorBike = in.nextInt(); // the minimum amount of motorbikes that must survive
        System.err.println("motorBikeControl: " + motorBikeControl);
        System.err.println("minimumMotorBike: " + minimumMotorBike);

        for (int i = 0; i < LINES; i++) {
            line[i] = in.next();
            bridgeLength = line[i].length();
            System.err.println(line[i]);
            line[i] = line[i] + "..............................";
        }

        stack = new LinkedList<>();

        Node rootNode = new Node();
        rootNode.state = TreeState.WORK;
        rootNode.iter = 0;

        int S = in.nextInt(); // the motorbikes' speed
        System.err.println("Speed: " + S);

        for (int i = 0; i < motorBikeControl; i++) {
            MotorBike motorBike = new MotorBike();
            motorBike.x = in.nextInt(); // x coordinate of the motorbike
            motorBike.y = in.nextInt(); // y coordinate of the motorbike
            motorBike.activated = in.nextInt() == 1; // indicates whether the motorbike is activated "1" or detroyed "0"
            motorBike.jump = false;
            motorBike.speed = S;
            rootNode.motorBikes[i] = motorBike;

            System.err.println("x: " + motorBike.x);
            System.err.println("y: " + motorBike.y);
            System.err.println("a: " + motorBike.activated);
        }

        long time = System.currentTimeMillis();
        System.err.println("Start time: " + time);

        stack.push(rootNode);

        while (true) {

            long maxCalcTime = System.currentTimeMillis() + MAX_CALC_TIME;
            Node winNode = queueCalc(rootNode, maxCalcTime);
            rootNode = nextNode(winNode, rootNode);
            //System.err.println(actions);
            if (rootNode == null) {
                break;
            }
            System.out.println(rootNode.action);

            System.err.println("Turn time: " + (System.currentTimeMillis() - time));
            time = System.currentTimeMillis();

//            for (Node node : actions) {
//                System.out.println(node.action);

//                System.err.println("Calc");
//                for (int i = 0; i < node.motorBikes.length; i++) {
//                    System.err.println("Bike: " + i + " Speed: " + node.motorBikes[i].speed);
//                    System.err.println("Bike: " + i + " x: " + node.motorBikes[i].x);
//                    System.err.println("Bike: " + i + " y: " + node.motorBikes[i].y);
//                    System.err.println("Bike: " + i + " a: " + node.motorBikes[i].activated);
//                }
//
//                System.err.println("System");
//                System.err.println("Speed: " + in.nextInt());
//                for (int i = 0; i < motorBikeControl; i++) {
//                    System.err.println("Bike: " + i + " x: " + in.nextInt());
//                    System.err.println("Bike: " + i + " y: " + in.nextInt());
//                    System.err.println("Bike: " + i + " a: " + in.nextInt());
//                }
//            }
        }
        while (true) {
            System.out.println(Action.WAIT);
        }
    }

    private static class MotorBike {
        int speed;
        int x;
        int y;
        boolean activated;
        boolean jump;

        public MotorBike() {
        }

        public MotorBike(MotorBike motorBike, Action action) {
            if (motorBike.activated) {
                switch (action) {
                    case SPEED:
                        this.speed = motorBike.speed + 1;
                        this.x = motorBike.x + motorBike.speed + 1;
                        this.y = motorBike.y;
                        this.jump = false;
                        break;
                    case SLOW:
                        this.speed = motorBike.speed - 1;
                        this.x = motorBike.x + motorBike.speed - 1;
                        this.y = motorBike.y;
                        this.jump = false;
                        break;
                    case JUMP:
                        this.speed = motorBike.speed;
                        this.x = motorBike.x + motorBike.speed;
                        this.y = motorBike.y;
                        this.jump = true;
                        break;
                    case WAIT:
                        this.speed = motorBike.speed;
                        this.x = motorBike.x + motorBike.speed;
                        this.y = motorBike.y;
                        this.jump = false;
                        break;
                    case UP:
                        this.speed = motorBike.speed;
                        this.x = motorBike.x + motorBike.speed;
                        this.y = motorBike.y - 1;
                        this.jump = false;
                        break;
                    case DOWN:
                        this.speed = motorBike.speed;
                        this.x = motorBike.x + motorBike.speed;
                        this.y = motorBike.y + 1;
                        this.jump = false;
                        break;
                }
            } else {
                this.speed = 0;
                this.x = motorBike.x;
                this.y = motorBike.y;
                this.jump = false;
            }
            this.activated = motorBike.activated;
        }
    }

    private enum Action {
        WAIT,
        SLOW,
        DOWN,
        UP,
        JUMP,
        SPEED
    }

    private enum TreeState {
        STOP,
        WORK
    }

    private static class Node {
        Node parent;
        Node[] children;
        TreeState state;
        Action action;

        MotorBike[] motorBikes = new MotorBike[motorBikeControl];

        int iter;
    }

    private static Node[] calcNextTurn(Node node) {
        if (node.state.equals(TreeState.WORK)) {
            node.children = new Node[6];
            for (int actionNumber = 0; actionNumber < Action.values().length; actionNumber++) {
                Action currentAction = Action.values()[actionNumber];
                Node newNode = new Node();
                for (int i = 0; i < node.motorBikes.length; i++) {
                    newNode.motorBikes[i] = new MotorBike(node.motorBikes[i], currentAction);
                    newNode.motorBikes[i].activated = checkMotor(node.motorBikes[i], newNode.motorBikes[i]);
                }
                newNode.state = checkNode(newNode.motorBikes);
                newNode.parent = node;
                newNode.action = currentAction;
                newNode.iter = node.iter + 1;
                node.children[actionNumber] = newNode;
            }
        }
        return node.children;
    }

    private static Node recursiveCalc(Node rootNode, long stopCalc) {
        if (System.currentTimeMillis() > stopCalc) {
            return rootNode;
        }
        Node[] nodes = calcNextTurn(rootNode);
        if (nodes == null || nodes.length == 0) {
            return null;
        }
        for (Node node : nodes) {
            Node checkNode = checkWin(node);
            if (checkNode != null) {
                return checkNode;
            }
        }
        for (Node node : nodes) {
            Node resultNode = recursiveCalc(node, stopCalc);
            if (resultNode != null) {
                return resultNode;
            }
        }
        return null;
    }

    private static Node queueCalc(Node rootNode, long stopCalc) {
        if (winNode != null) {
            return winNode;
        }
        //while (true) {
        while (System.currentTimeMillis() < stopCalc) {
            Node pop = stack.pop();
            if(!chectBranche(rootNode, pop)) {
                continue;
            }
            Node[] nodes = calcNextTurn(pop);
            if (nodes == null || nodes.length == 0) {
                continue;
            }
            for (Node node : nodes) {
                Node checkNode = checkWin(node);
                if (checkNode != null) {
                    return checkNode;
                }
                if (node.state.equals(TreeState.WORK)) {
                    stack.push(node);
                }
            }
        }
        return stack.getFirst();
    }

    private static boolean checkMotor(MotorBike oldBike, MotorBike newBike) {
        boolean result = true;
        if (newBike.jump) {
            if (line[newBike.y].charAt(newBike.x) != SAFE) {
                result = false;
            }
        } else {
            if (newBike.y == WARNING_UP_LINE || newBike.y == WARNING_DOWN_LINE) {
                result = false;
            } else {
                String bikeLine = line[oldBike.y].substring(oldBike.x, newBike.x + 1);
                String moveLine = line[newBike.y].substring(oldBike.x, newBike.x + 1);
                if (!bikeLine.matches(BIKE_LINE_REGULAR) || !moveLine.matches(MOVE_LINE_REGULAR)) {
                    result = false;
                }
            }
        }
        return result;
    }

    private static TreeState checkNode(MotorBike[] motorBikes) {
        int activeBike = 0;
        TreeState treeState = TreeState.WORK;
        for (MotorBike motorBike : motorBikes) {
            if (motorBike.y == WARNING_UP_LINE || motorBike.y == WARNING_DOWN_LINE) {
                treeState = TreeState.STOP;
                break;
            }
            if (motorBike.activated) {
                activeBike++;
            }
            if (motorBike.activated && motorBike.speed <= 0) {
                treeState = TreeState.STOP;
                break;
            }
        }
        if (activeBike < minimumMotorBike) {
            treeState = TreeState.STOP;
        }
        return treeState;
    }

    private static Node checkWin(Node node) {
        if (node.state.equals(TreeState.WORK)) {
            for (MotorBike motorBike : node.motorBikes) {
                if (motorBike.activated && motorBike.x >= bridgeLength) {
                    winNode = node;
                    return node;
                }
            }
        }
        return null;
    }

    private static List<Node> treeUp(Node leaf, Node rootNode) {
        List<Node> result = new ArrayList<>();
        while (leaf != rootNode) {
            result.add(leaf);
            leaf = leaf.parent;
        }
        Collections.reverse(result);
        return result;
    }

    private static Node nextNode(Node leaf, Node rootNode) {
        Node result = null;
        while (leaf != rootNode) {
            result = leaf;
            leaf = leaf.parent;
        }
        return result;
    }

    private static boolean chectBranche(Node rootNode, Node node) {
        if (node == rootNode) {
            return true;
        }
        while (node.parent != null) {
            if (node.parent == rootNode) {
                return true;
            }
            node = node.parent;
        }
        return false;
    }

}
