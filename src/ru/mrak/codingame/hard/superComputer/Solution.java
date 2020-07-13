package ru.mrak.codingame.hard.superComputer;

import java.util.*;

class Solution {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        List<Task> taskList = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            int J = in.nextInt();
            int D = in.nextInt();
            taskList.add(new Task(J, J + D - 1));
        }
        int day = 0;
        int count = 0;
        taskList.sort(Comparator.comparing(task -> task.end));
    
        for (Task task : taskList) {
            if (task.start >= day) {
                count++;
                day = task.end + 1;
            }
        }
        System.out.println(count);
    }
    
    private static class Task {
        int start;
        int end;
    
        public Task(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
