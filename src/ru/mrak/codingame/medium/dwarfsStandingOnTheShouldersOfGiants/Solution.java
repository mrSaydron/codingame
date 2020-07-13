package ru.mrak.codingame.medium.dwarfsStandingOnTheShouldersOfGiants;

import java.util.*;
import java.util.stream.Collectors;

public class Solution {
    private static int maxValue = 0;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(); // the number of relationships of influence
        System.err.println(n);
        Map<Integer, Node> nodes = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int x = in.nextInt(); // a relationship of influence between two people (x influences y)
            int y = in.nextInt();
            System.err.println(x + " " + y);
            Node.add(x, y);
        }

        Node.notParent = Node.nodes.values().stream().filter(node -> node.parents.isEmpty()).collect(Collectors.toList());
        Node.notParent.forEach(Solution::nextNode);

        System.out.println(maxValue);
    }

    private static void nextNode(Node node) {
        node.calc = true;
        if(!node.childs.isEmpty()) {
            int value = node.value + 1;
            if (value > maxValue) {
                maxValue = value;
            }
            for (Node childNode : node.childs) {
                if (childNode.value < value) {
                    childNode.value = value;
                }
                if (childNode.parents.stream().allMatch(parentNode -> parentNode.calc)) {
                    nextNode(childNode);
                }
            }
        }
    }

    private static class Node {
        int id;
        Set<Node> parents = new HashSet<>();
        Set<Node> childs = new HashSet<>();
        int value = 1;
        boolean calc = false;

        static Map<Integer, Node> nodes = new HashMap<>();
        static List<Node> notParent = new ArrayList<>();

        private Node(int id) {
            this.id = id;
        }

        static void add(int parentId, int childId) {
            if (!nodes.containsKey(parentId)) {
                nodes.put(parentId, new Node(parentId));
            }
            if (!nodes.containsKey(childId)) {
                nodes.put(childId, new Node(childId));
            }
            Node parentNode = nodes.get(parentId);
            Node childNode = nodes.get(childId);
            parentNode.childs.add(childNode);
            childNode.parents.add(parentNode);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return id == node.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
