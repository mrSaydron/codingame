package ru.mrak.codingame.easy.aContributionByPlopx;

import java.util.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Solution {

    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        String expression = "{([]){}()}"; //in.next();
        String oldString = "";
        System.err.println(expression);
        while (!expression.equals(oldString)) {
            oldString = expression;
            expression = expression.replaceFirst("\\([^\\(^\\)^\\[^\\]^\\{^\\}]*\\)|\\[[^\\(^\\)^\\[^\\]^\\{^\\}]*\\]|\\{[^\\(^\\)^\\[^\\]^\\{^\\}]*\\}", "");
        }
        System.err.println(expression);
        if(expression.contains("(")
            || expression.contains(")")
            || expression.contains("{")
            || expression.contains("}")
            || expression.contains("[")
            || expression.contains("]")) {
            System.out.println("false");
        } else {
            System.out.println("true");
        }
    }
}
