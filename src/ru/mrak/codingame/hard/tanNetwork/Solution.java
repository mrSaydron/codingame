package ru.mrak.codingame.hard.tanNetwork;

import java.util.*;

class Solution {
    
    private static Map<String, StopArea> stopAreaByIdentifier = new HashMap<>();
    
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        String startPoint = in.next();
        String endPoint = in.next();
        int N = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        //System.err.println(startPoint);
        //System.err.println(endPoint);
        //System.err.println(N);
        for (int i = 0; i < N; i++) {
            String stopName = in.nextLine();
            StopArea stopArea = new StopArea(stopName);
            stopAreaByIdentifier.put(stopArea.identifier, stopArea);
            //System.err.println(stopName);
        }
        int M = in.nextInt();
        //System.err.println(M);
        if (in.hasNextLine()) {
            in.nextLine();
        }
        for (int i = 0; i < M; i++) {
            String route = in.nextLine();
            String[] split = route.split(" ");
            StopArea startArea = stopAreaByIdentifier.get(split[0]);
            StopArea endArea = stopAreaByIdentifier.get(split[1]);
            startArea.distanceToStopArea.add(new Pair<>(endArea, distance(startArea, endArea)));
            //System.err.println(route);
        }
    
        StopArea startArea = stopAreaByIdentifier.get(startPoint);
        startArea.distance = 0;
        Deque<StopArea> queue = new LinkedList<>();
        queue.addFirst(startArea);
        while (queue.size() > 0) {
            StopArea stopArea = queue.pollFirst();
            calc(stopArea, queue);
        }
        
        StopArea endArea = stopAreaByIdentifier.get(endPoint);
        String result = "IMPOSSIBLE";
        if (endArea.distance != Double.MAX_VALUE) {
            result = returnPath(startArea, endArea);
        }
        System.out.println(result);
    }
    
    private static String returnPath(StopArea startArea, StopArea endArea) {
        List<StopArea> pathList = new ArrayList<>();
        StopArea work = endArea;
        pathList.add(work);
        while (!work.equals(startArea)) {
            work = work.returnArea;
            pathList.add(work);
        }
        StringJoiner stringJoiner = new StringJoiner("\n");
        for (int i = pathList.size() - 1; i >= 0; i--) {
            stringJoiner.add(pathList.get(i).name);
        }
        return stringJoiner.toString();
    }
    
    private static void calc(StopArea stopArea, Deque<StopArea> queue) {
        for (Pair<StopArea, Double> stopAreaEntry : stopArea.distanceToStopArea) {
            StopArea toArea = stopAreaEntry.getKey();
            Double distance = stopAreaEntry.getValue();
            double toDistance = stopArea.distance + distance;
            if (toArea.distance > toDistance) {
                toArea.distance = toDistance;
                toArea.returnArea = stopArea;
                queue.addLast(toArea);
            }
        }
    }
    
    private static double distance(StopArea one, StopArea two) {
        double x = (two.longitude - one.longitude) * Math.cos((one.latitude + two.latitude) / 2);
        double y = two.latitude - one.latitude;
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) * 6371.0;
    }
    
    private static class StopArea {
        String identifier;
        String name;
        double latitude;
        double longitude;
        
        List<Pair<StopArea, Double>> distanceToStopArea = new ArrayList<>();
        StopArea returnArea;
        
        double distance = Double.MAX_VALUE;
    
        public StopArea(String stopArea) {
            String[] split = stopArea.split(",");
            identifier = split[0];
            name = split[1].replace("\"", "");
            latitude = Math.toRadians(Double.parseDouble(split[3]));
            longitude = Math.toRadians(Double.parseDouble(split[4]));
        }
    
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StopArea stopArea = (StopArea) o;
            return identifier.equals(stopArea.identifier);
        }
    
        @Override
        public int hashCode() {
            return Objects.hash(identifier);
        }
    }
    
    private static class Pair<K,V> {
        private K key;
        private V value;
    
        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    
        public K getKey() {
            return key;
        }
    
        public V getValue() {
            return value;
        }
    }
}
