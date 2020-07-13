package ru.mrak.other;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Проверяет две строки на одинаковость входящих в него символов
 */
public class CompareTwoString {
    
    public static void main(String[] args) {
        while (true) {
            Scanner in = new Scanner(System.in);
            String strOne = in.nextLine();
            String strTwo = in.nextLine();
    
            System.out.println(compare(strOne, strTwo));
        }
    }
    
    private static boolean compare(String strOne, String strTwo) {
        Map<Character, Integer> compareMap = new HashMap<>();
    
        for (char c : strOne.toCharArray()) {
            if (!compareMap.containsKey(c)) {
                compareMap.put(c, 0);
            }
            compareMap.put(c, compareMap.get(c) + 1);
        }
    
        for (char c : strTwo.toCharArray()) {
            if (!compareMap.containsKey(c)) {
                compareMap.put(c, 0);
            }
            compareMap.put(c, compareMap.get(c) - 1);
        }
        
        return compareMap.values().stream().noneMatch(count -> count != 0);
    }
    
}
