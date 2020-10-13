package ru.mrak.codingame.medium.scrabble;

//import javafx.util.Pair;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Solution {
//    private static final int MAX_CHARS = 3;
//
//    public static void main(String args[]) {
//        Scanner in = new Scanner(System.in);
//        int N = in.nextInt();
//        //int N = Data.N;
//        System.err.println(N);
//        if (in.hasNextLine()) {
//            in.nextLine();
//        }
//        Map<String, List<Pair<String, Integer>>> dictionary = new HashMap<>();
//
//        String[] strs = Data.DIC.split("\n");
//        for (int i = 0; i < N; i++) {
//            String W = in.nextLine();
//            //String W = strs[i];
//            if (W.length() > 7) {
//                continue;
//            }
//            System.err.println(W);
//            char[] wordSort = W.toCharArray();
//            Arrays.sort(wordSort);
//            StringBuilder keyAp = new StringBuilder(MAX_CHARS);
//            for (int j = 0; j < (MAX_CHARS < wordSort.length ? MAX_CHARS : wordSort.length); j++) {
//                keyAp.append(wordSort[j]);
//            }
//            String key = keyAp.toString();
//            if (!dictionary.containsKey(key)) {
//                dictionary.put(key, new ArrayList<>());
//            }
//            dictionary.get(key).add(new Pair<>(W, i));
//        }
//        String LETTERS = in.nextLine();
//        //String LETTERS = Data.LETTERS;
//        System.err.println(LETTERS);
//        List<Character> letters = new ArrayList<>(LETTERS.length());
//        for (char c: LETTERS.toCharArray()) {
//            letters.add(c);
//        }
//        letters.sort(Character::compareTo);
//
//        List<Pair<String, Integer>> findWords = new ArrayList<>();
//        for (int i = 1; i <= MAX_CHARS; i++) {
//            final int chars = i;
//            List<Character> turn = new ArrayList<>(chars);
//            for (int j = 0; j < i; j++) {
//                turn.add(' ');
//            }
//            enumeration(letters, chars, 0, turn, 0, (inArray) -> {
//                StringBuilder keyAp = new StringBuilder(MAX_CHARS);
//                for (int j = 0; j < chars; j++) {
//                    keyAp.append(inArray.get(j));
//                }
//                String key = keyAp.toString();
//                if (dictionary.containsKey(key)) {
//                    findWords.addAll(dictionary.get(key));
//                }
//            });
//        }
//
//        List<Pair<String, Integer>> resultWords = findWords.stream().filter(word -> {
//            List<Character> chars = new ArrayList<>(word.getKey().length());
//            for (char c: word.getKey().toCharArray()) chars.add(c);
//            for (Character c : letters) {
//                chars.remove(c);
//            }
//            return chars.size() == 0;
//        }).sorted((word1, word2) -> word1.getValue() - word2.getValue())
//                .collect(Collectors.toList());
//
//        int max = 0;
//        String result = null;
//        for (Pair<String, Integer> word: resultWords) {
//            int points = pointsSumm(word.getKey());
//            if(max < points) {
//                max = points;
//                result = word.getKey();
//            }
//        }
//
//        System.out.println(result);
//    }
//
//    private static <T> void enumeration(List<T> array, int elements, int lvl, List<T> turn, int number, Consumer<List<T>> consumer) {
//        for (int i = number; i < array.size() - elements + (lvl + 1); i++) {
//            turn.set(lvl, array.get(i));
//            if (elements == lvl + 1) {
//                consumer.accept(turn);
//            } else {
//                enumeration(array, elements, lvl + 1, turn, i + 1, consumer);
//            }
//        }
//    }
//
//    private static int pointsSumm(String word) {
//        Map<Integer, Set<Character>> points = new HashMap<>();
//        points.put(1, new HashSet<>(Arrays.asList('e', 'a', 'i', 'o', 'n', 'r', 't', 'l', 's', 'u')));
//        points.put(2, new HashSet<>(Arrays.asList('d', 'g')));
//        points.put(3, new HashSet<>(Arrays.asList('b', 'c', 'm', 'p')));
//        points.put(4, new HashSet<>(Arrays.asList('f', 'h', 'v', 'w', 'y')));
//        points.put(5, new HashSet<>(Arrays.asList('k')));
//        points.put(8, new HashSet<>(Arrays.asList('j', 'x')));
//        points.put(10, new HashSet<>(Arrays.asList('q', 'z')));
//
//        char[] chars = word.toCharArray();
//        int sum = 0;
//        for (char aChar : chars) {
//            for (Integer point : points.keySet()) {
//                if (points.get(point).contains(aChar)) {
//                    sum += point;
//                }
//            }
//        }
//        return sum;
//    }
}
