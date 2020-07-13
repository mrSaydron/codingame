package ru.mrak.other;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class EquationBifurcation {
    private interface Function <T> {
        T calc(T value);
    }
    
    private static class BifFunction implements EquationBifurcation.Function<Double> {
        private double r;
        public BifFunction(double r) {
            this.r = r;
        }
    
        @Override
        public Double calc(Double value) {
            return r * value * (1 - value);
        }
    }
    
    public static void main(String[] args) {
        int lastCount = 0;
        for (double r = 2.99; r < 4.0; r += 0.0001) {
            //System.out.println("r: " + r);
            Function<Double> bifFunction = new BifFunction(r);
            Double x = 0.2;
            Set<Long> result = new LinkedHashSet<>();
            for (int i = 0; i < 2000; i++) {
                x = bifFunction.calc(x);
                if (i > 1000) {
                    result.add(Math.round(x * 1000));
                }
            }
            if (lastCount != result.size()) {
                System.out.println("result count: " + result.size() + " r: " + r);
                lastCount = result.size();
            }
            //result.forEach(System.out::println);
            //System.out.println();
        }
    }
}
