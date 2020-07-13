package ru.mrak.codingame.hard.bender2;

import java.util.*;

class Solution {
    
    private static Map<Integer, Room> roomByNumber = new HashMap<>();
    private static int maxMoney = 0;
    
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        System.err.println(N);
        for (int i = 0; i < N; i++) {
            String room = in.nextLine();
            //System.err.println(room);
            new Room(room);
        }
        new Room(-1, 0);
        Deque<Integer> queue = new LinkedList<>();
        queue.addLast(0);
        Room startRoom = roomByNumber.get(0);
        startRoom.maxMoney = startRoom.money;
        while (queue.size() > 0) {
            Integer roomNumber = queue.pollFirst();
            Room room = roomByNumber.get(roomNumber);
            calc(room, queue);
        }
        
        System.out.println(maxMoney);
    }
    
    private static void calc(Room room, Deque<Integer> queue) {
        if (room.number == -1) {
            maxMoney = Math.max(room.maxMoney, maxMoney);
            return;
        }
        
        for (Integer roomNumber : room.nextRoomList) {
            Room nextRoom = roomByNumber.get(roomNumber);
            if (nextRoom.maxMoney < (nextRoom.money + room.maxMoney)) {
                nextRoom.maxMoney = nextRoom.money + room.maxMoney;
                queue.addLast(roomNumber);
                
            }
        }
    }
    
    private static class Room {
        int number;
        int money;
        List<Integer> nextRoomList = new ArrayList<>();
        
        int maxMoney = -1;
    
        public Room(String room) {
            String[] split = room.split(" ");
            number = Integer.parseInt(split[0]);
            money = Integer.parseInt(split[1]);
    
            for (int i = 2; i < 4; i++) {
                int nextRoom;
                if (split[i].equals("E")) nextRoom = -1;
                else nextRoom = Integer.parseInt(split[i]);
                nextRoomList.add(nextRoom);
            }
            
            roomByNumber.put(number, this);
        }
    
        public Room(int number, int money) {
            this.number = number;
            this.money = money;
    
            roomByNumber.put(number, this);
        }
    }
}
