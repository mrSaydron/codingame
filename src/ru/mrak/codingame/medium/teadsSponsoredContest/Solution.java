package ru.mrak.codingame.medium.teadsSponsoredContest;

import java.util.*;
import java.util.stream.Collectors;

class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(); // the number of adjacency relations
        System.err.println(n);
        for (int i = 0; i < n; i++) {
            int xi = in.nextInt(); // the ID of a person which is adjacent to yi
            int yi = in.nextInt(); // the ID of a person which is adjacent to xi
            Node.add(xi, yi);
            System.err.println(xi + " " + yi);
        }

        List<Node> nodes
                = Node.nodes.values()
                .stream()
                .filter(node -> node.links.size() == 1)
                .peek(node -> node.counted = true)
                .collect(Collectors.toList());

        int i = 0;
        while (true) {
            i++;
            nodes = nodes.stream()
                    .flatMap(node -> node.links.stream())
                    .filter(node -> !node.counted)
                    .distinct()
                    .filter(node -> node.links.stream().filter(link -> !link.counted).count() <= 1)
                    .peek(node -> node.counted = true)
                    .collect(Collectors.toList());
            if (nodes.size() == 1 || nodes.isEmpty()) break;
        }

        System.out.println(i);
    }

    private static class Node {
        static Map<Integer, Node> nodes = new HashMap<>();

        int id;
        Set<Node> links = new HashSet<>();
        boolean counted = false;

        private Node(int id) {
            this.id = id;
        }

        void addLink(Node linkNode) {
            links.add(linkNode);
        }

        static void add(int id, int link) {
            Node node = getNode(id);
            Node linkNode = getNode(link);
            node.addLink(linkNode);
            linkNode.addLink(node);
        }

        static Node getNode(int id) {
            Node node;
            if(!nodes.containsKey(id)) {
                node = new Node(id);
                nodes.put(id, node);
            } else {
                node = nodes.get(id);
            }
            return node;
        }
    }

}
