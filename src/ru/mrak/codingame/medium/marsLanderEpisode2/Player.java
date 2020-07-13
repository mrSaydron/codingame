package ru.mrak.codingame.medium.marsLanderEpisode2;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class Player {
    private static final Double GRAVITY = -3.711;
    private static Double dT = 1.0;
    private static double targetX;
    private static double targetY;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int surfaceN = in.nextInt(); // the number of points used to draw the surface of Mars.
        //System.err.println(surfaceN);
        List<Point> points = new ArrayList<>(surfaceN);
        for (int i = 0; i < surfaceN; i++) {
            int landX = in.nextInt(); // X coordinate of a surface point. (0 to 6999)
            int landY = in.nextInt(); // Y coordinate of a surface point. By linking all the points together in a sequential fashion, you form the surface of Mars.
            points.add(new Point(landX, landY));
            //System.err.println(landX);
            //System.err.println(landY);
        }

        for (int i = 0; i < surfaceN - 1; i++) {
            if(points.get(i).y == points.get(i + 1).y) {
                targetY = points.get(i).y;
                targetX = (points.get(i).x + points.get(i + 1).x) / 2.0;
            }
        }

        Tree tree = new Tree();

        // game loop
        while (true) {
            int X = in.nextInt();
            //System.err.println(X);
            int Y = in.nextInt();
            //System.err.println(Y);
            int hSpeed = in.nextInt(); // the horizontal speed (in m/s), can be negative.
            //System.err.println(hSpeed);
            int vSpeed = in.nextInt(); // the vertical speed (in m/s), can be negative.
            //System.err.println(vSpeed);
            int fuel = in.nextInt(); // the quantity of remaining fuel in liters.
            //System.err.println(fuel);
            int rotate = in.nextInt(); // the rotation angle in degrees (-90 to 90).
            //System.err.println(rotate);
            int power = in.nextInt(); // the thrust power (0 to 4).
            //System.err.println(power);

            State state = new State(X, Y, hSpeed, vSpeed, fuel, rotate, power);
            Node node = new Node();
            node.state = state;
            node.calcScore(node);
            node.turn = 0;

            tree.changeRoot(node);
            tree.calcDeepEdge();
            Action upAction = tree.getUpAction();
            if(upAction != null) {
                rotate = rotate + upAction.dRot;
                power = power + upAction.dPow;
            }

            Node runner = tree.upScore;
            while (runner.parent != null) {
                System.err.println("Turn=" + runner.turn + "\n" + runner.state);
                runner = runner.parent;
            }
//            System.err.println("X=" + Math.round(tree.upScore.state.x) + "m, "
//                    + "Y=" + Math.round(tree.upScore.state.y) + "m, "
//                    + "HSpeed=" + Math.round(tree.upScore.state.hSpeed) + "m/s, "
//                    + "VSpeed=" + Math.round(tree.upScore.state.vSpeed) + "m/s");
//            System.err.println(tree.upScore.turn);
            System.out.println(rotate + " " + power);
        }
    }

    static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class Tree {
        final static int CALC_DEPTH = 5;

        Node root;
        long lastTurn;
        List<Node> edge;
        Node upScore;

        Map<Double, List<Node>> edgeAStar;

        void changeRoot(Node newRoot) {
            root = newRoot;
            edge = new ArrayList<>();
            edge.add(newRoot);
            upScore = newRoot;
            lastTurn = newRoot.turn;
            edgeAStar = new TreeMap<>();
            List<Node> rootNode = new ArrayList<>();
            rootNode.add(root);
            edgeAStar.put(root.score, rootNode);
        }

        Action getUpAction() {
            if (upScore.parent == null) return null;
            Node runner = upScore;
            while (runner.parent != root) {
                runner = runner.parent;
            }
            return runner.action;
        }

        void calcEdge() {
            List<Node> newEdge = new ArrayList<>();
            upScore = new Node();
            upScore.score = -1 * Double.MAX_VALUE;
            edge.forEach(node -> {
                List<Node> nodes = node.calcAllActions();
                for (Node runner : nodes) {
                    if (runner.score > upScore.score) upScore = runner;
                }
                newEdge.addAll(nodes.stream().filter(nodeF -> nodeF.score > root.score).collect(Collectors.toList()));
            });
            edge = newEdge;
        }

        void calcDeepEdge() {
            while (edge.get(0).turn < lastTurn + CALC_DEPTH) {
                calcEdge();
            }
            lastTurn = lastTurn + CALC_DEPTH;
        }

        void calcAStar() {
            //edgeAStar.ent
        }
    }

    private static double MAX_H_SPEED = 20;
    private static double MAX_V_SPEED = 20;

    private static class Node {
        Node parent;
        List<Node> children;
        State state;
        Action action;
        long turn;

        double score;
        boolean edge;

        void calcScore(Node newNode){
            //ToDO
            double distanceToLand = Math.sqrt(pow(targetX - newNode.state.x, 2) + pow(targetY - newNode.state.y, 2));
            double vSpeedScore = abs(newNode.state.vSpeed) > MAX_V_SPEED ? pow(MAX_V_SPEED - abs(newNode.state.vSpeed), 4) : 0;
            double hSpeesScore = abs(newNode.state.hSpeed) > MAX_H_SPEED ? pow(MAX_H_SPEED - abs(newNode.state.hSpeed), 4) : 0;
            newNode.score = - distanceToLand - vSpeedScore - hSpeesScore;
            if(distanceToLand < 200
                    && newNode.state.rotate == 0
                    && abs(newNode.state.vSpeed) < 40
                    && abs(newNode.state.hSpeed) < 20
                    && newNode.state.y > targetY) {
                newNode.score += 1000;
            }
        }

        Node calc(Action action) {
            State newState = new State(state.rotate + action.dRot, state.power + action.dPow);
            Double vAcceleration = Math.cos(newState.rotate * Math.PI / 180) * newState.power + GRAVITY;
            Double hAcceleration = Math.sin(- newState.rotate * Math.PI / 180) * newState.power;
            newState.hSpeed = state.hSpeed + hAcceleration * dT;
            newState.vSpeed = state.vSpeed + vAcceleration * dT;
            newState.y = state.y + (vAcceleration * pow(dT, 2) / 2 + state.vSpeed * dT);
            newState.x = state.x + (hAcceleration * pow(dT, 2) / 2 + state.hSpeed * dT);
            newState.fuel = state.fuel - newState.power;

            Node node = new Node();
            node.parent = this;
            children.add(node);
            node.state = newState;
            node.action = action;
            node.turn = turn + 1;

            calcScore(node);

            return node;
        }

        List<Node> calc(Set<Action> actions) {
            if(!edge) {
                Set<Action> calculatedActions;
                if(children == null) {
                    children = new ArrayList<>(actions.size());
                    calculatedActions = new HashSet<>();
                } else {
                    calculatedActions = children.stream().map(node -> node.action).collect(Collectors.toSet());
                }
                actions.removeAll(calculatedActions);
                actions.forEach(this::calc);
                return children;
            }
            return null;
        }

        List<Node> calcAllActions() {
            return calc(getActions());
        }

        Set<Action> getActions() {
            return Action.getAllActions().stream().filter(action ->
                    state.power + action.dPow <= 4
                    && state.power + action.dPow >= 0
                    && state.rotate + action.dRot >= -90
                    && state.rotate + action.dRot <= 90
            ).collect(Collectors.toSet());
        }
    }

    private static class State {
        double x;
        double y;
        double hSpeed;
        double vSpeed;
        int fuel;
        int rotate;
        int power;

        State(int rotate, int power) {
            this.rotate = rotate;
            this.power = power;
        }

        public State(int x, int y, int hSpeed, int vSpeed, int fuel, int rotate, int power) {
            this.x = x;
            this.y = y;
            this.hSpeed = hSpeed;
            this.vSpeed = vSpeed;
            this.fuel = fuel;
            this.rotate = rotate;
            this.power = power;
        }

        @Override
        public String toString() {
            return "X=" + Math.round(x) + "m, "
                    + "Y=" + Math.round(y) + "m, "
                    + "HSpeed=" + Math.round(hSpeed) + "m/s, "
                    + "VSpeed=" + Math.round(vSpeed) + "m/s"
                    + "\n"
                    + "Angel=" + rotate + "gradus "
                    + "Power" + power + "m/s2";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return Double.compare(state.x, x) == 0 &&
                    Double.compare(state.y, y) == 0 &&
                    Double.compare(state.hSpeed, hSpeed) == 0 &&
                    Double.compare(state.vSpeed, vSpeed) == 0 &&
                    fuel == state.fuel &&
                    rotate == state.rotate &&
                    power == state.power;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, hSpeed, vSpeed, fuel, rotate, power);
        }
    }

    private static class Action {
        int dRot;
        int dPow;

        private static int[] rots = {-10, 0, 10};
        private static int[] pows = {-1, 0, 1};
        private static Set<Action> actions = new HashSet<>();

        static {
            for (int dR : rots) {
                for (int dP : pows) {
                    actions.add(new Action(dR, dP));
                }
            }
        }

        public Action(int dRot, int dPow) {
            this.dRot = dRot;
            this.dPow = dPow;
        }

        static Set<Action> getAllActions() {
            return actions;
        }
    }
}
