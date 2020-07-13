package ru.mrak.codingame.easy.expandThePolynomial;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        //String poly = in.next();
        String poly = "(2x+3)(x-2)";
        System.err.println(poly);

        String res = collectExpression(getExpression(poly)
                .stream()
                .map(Solution::divideExpression)
                .reduce(Solution::multiply).orElseThrow(RuntimeException::new));

        System.out.println(res);
    }

    private static List<String> getExpression(String poly) {
        Pattern pattern = Pattern.compile("\\(.*?\\)\\^?\\d*");
        Matcher matcher = pattern.matcher(poly);
        List<String> result = new ArrayList<>();
        while(matcher.find()) {
            result.add(matcher.group());
        }
        result = result.stream().flatMap(str -> {
            Pattern patternPow = Pattern.compile("(\\(.*\\))\\^(\\d+)");
            Matcher matcherPow = patternPow.matcher(str);
            if(matcherPow.find()) {
                String expression = matcherPow.group(1);
                String pow = matcherPow.group(2);
                String[] res = new String[Integer.parseInt(pow)];
                Arrays.fill(res, expression);
                return Arrays.stream(res);
            } else {
                return Stream.of(str);
            }
        }).collect(Collectors.toList());
        return result;
    }

    private static Map<Integer, Integer> divideExpression(String expression) {
        Pattern patternDivide = Pattern.compile("((-?)(\\d*)x|(-?)(\\d+))(\\^(\\d*))?");
        Matcher matcherDivide = patternDivide.matcher(expression);
        Map<Integer, List<Integer>> res = new HashMap<>();
        while (matcherDivide.find()) {
            String signX = matcherDivide.group(2);
            String valueX = matcherDivide.group(3);
            String powX = matcherDivide.group(7);

            String signN = matcherDivide.group(4);
            String valueN = matcherDivide.group(5);

            Integer pow;
            int number;
            if(valueX != null) {
                pow = powX != null ? Integer.parseInt(powX) : 1;
                number = (signX.equals("") ? 1 : -1) * (valueX.equals("") ? 1 : Integer.parseInt(valueX));
            } else {
                pow = 0;
                number = (signN.equals("") ? 1 : -1) * (valueN.equals("") ? 1 : Integer.parseInt(valueN));
            }

            if(!res.containsKey(pow)) {
                res.put(pow, new ArrayList<>());
            }
            res.get(pow).add(number);
        }
        return simplify(res);
    }

    private static Map<Integer, Integer> simplify(Map<Integer, List<Integer>> original) {
        Map<Integer, Integer> res = new HashMap<>();
        for (Integer pow: original.keySet()) {
            res.put(pow, original.get(pow).stream().mapToInt(i -> i).sum());
        }
        return res;
    }

    private static Map<Integer, Integer> multiply(Map<Integer, Integer> nOne, Map<Integer, Integer> nTwo) {
        Map<Integer, List<Integer>> res = new HashMap<>();
        for (Integer powOne: nOne.keySet()) {
            for (Integer powTwo: nTwo.keySet()) {
                int pow = powOne + powTwo;
                if(!res.containsKey(pow)) {
                    res.put(pow, new ArrayList<>());
                }
                res.get(pow).add(nOne.get(powOne) * nTwo.get(powTwo));
            }
        }
        return simplify(res);
    }

    private static String collectExpression(Map<Integer, Integer> expression) {
        return expression.keySet()
                .stream()
                .filter(pow -> expression.get(pow) != 0)
                .sorted(Comparator.reverseOrder())
                .map(pow -> (pow == 0 ? expression.get(pow) : (expression.get(pow) == -1 ? "-" : (expression.get(pow) == 1 ? "" : expression.get(pow))))
                        + (pow == 0 ? "" : (pow == 1 ? "x" : "x^" + pow)))
                .reduce((s1, s2) -> s1 + (s2.charAt(0) == '-' ? "" : "+") + s2)
                .orElse("");
    }

}
