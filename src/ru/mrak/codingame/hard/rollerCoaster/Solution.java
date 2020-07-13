package ru.mrak.codingame.hard.rollerCoaster;

import java.util.*;

class Solution {
    
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int L = in.nextInt();
        int C = in.nextInt();
        int N = in.nextInt();
        System.err.println(L + " " + C + " " + N);
        List<Integer> queue = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            int cap = in.nextInt();
            queue.add(cap);
            System.err.println(cap);
        }
    
        Map<Integer, Save> cache = new HashMap<>();
        
        long sum = 0;
        int start = 0;
        int index;
        int turnSum;
        for (int i = 0; i < C; i++) {
            if (cache.containsKey(start)) {
                Save save = cache.get(start);
                sum += save.capacity;
                start = save.endIndex;
                continue;
            }
            turnSum = 0;
            index = start;
            int capacity = L;
            while (true) {
                Integer group = queue.get(index);
                if (capacity >= group) {
                    capacity -= group;
                    turnSum += group;
                } else {
                    cache.put(start, new Save(start, turnSum, index));
                    start = index;
                    sum += turnSum;
                    break;
                }
                index++;
                if (index == queue.size()) index = 0;
                if (start == index) {
                    cache.put(start, new Save(start, turnSum, index));
                    start = index;
                    sum += turnSum;
                    break;
                }
            }
        }
        
        System.out.println(sum);
    }
    
    private static class Save {
        int startIndex;
        int capacity;
        int endIndex;
    
        public Save(int startIndex, int capacity, int endIndex) {
            this.startIndex = startIndex;
            this.capacity = capacity;
            this.endIndex = endIndex;
        }
    }
}
