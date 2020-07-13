package ru.mrak.codingame.hard.genomeSequencing;

import java.util.*;

class Solution {
    
    private static List<Seq> seqList = new ArrayList<>();
    
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        
        for (int i = 0; i < N; i++) {
            String subseq = in.next();
            System.err.println(subseq);
            seqList.add(new Seq(subseq));
        }
    
        StringBuilder string = new StringBuilder();
        ResultString calc = new ResultString();
        int useSeq = 0;
        while (useSeq != seqList.size()) {
            int startIndex = 0;
            for (Integer useIndex : calc.useSeq) {
                seqList.get(useIndex).use = true;
            }
            for (int i = 0; i < seqList.size(); i++) {
                if (!seqList.get(i).use) {
                    startIndex = i;
                    break;
                }
            }
            Seq seq = seqList.get(startIndex);
            seq.use = true;
            calc = calc(seq.seq, startIndex);
            string.append(calc.result);
            useSeq += calc.useSeq.size();
        }
    
        System.err.println(string);
        System.out.println(string.length());
    }
    
    private static ResultString calc(String string, int index) {
        ResultString resultString = null;
        for (int i = 0; i < seqList.size(); i++) {
            Seq seq = seqList.get(i);
            if (!seq.use) {
                seq.use = true;
    
                String add = add(string, seq.seq);
                if (add != null) {
                    ResultString calc = calc(add, i);
                    if (resultString == null || resultString.result.length() > calc.result.length()) {
                        resultString = calc;
                    }
                }
                seq.use = false;
            }
        }
        if (resultString == null) {
            resultString = new ResultString();
            resultString.result = string;
        }
        resultString.useSeq.add(index);
        return resultString;
    }
    
    
    private static String add(String one, String two) {
        Integer bestIndex = null;
        int bestLength = 0;
        find:
        for (int i = -two.length() + 1; i < one.length(); i++) {
            int offsetOne = Math.max(i, 0);
            int offsetTwo = Math.max(-i, 0);
            int compareLength = Math.min(one.length() - offsetOne, two.length() - offsetTwo);
    
            for (int j = 0; j < compareLength; j++) {
                if (one.toCharArray()[offsetOne + j] != two.toCharArray()[offsetTwo + j]) continue find;
            }
            
            if (bestLength < compareLength) {
                bestLength = compareLength;
                bestIndex = i;
            }
        }
        
        String result = null;
        if (bestIndex != null) {
            if (bestIndex >= 0) {
                result = one
                        + ((one.length() - bestIndex) < two.length() ? two.substring(one.length() - bestIndex) : "");
            } else {
                result = two
                        + ((two.length() + bestIndex) < one.length() ? one.substring(two.length() + bestIndex) : "");
            }
        }
        return result;
    }
    
    private static class Seq {
        String seq;
        boolean use;
    
        public Seq(String seq) {
            this.seq = seq;
        }
    }
    
    private static class ResultString {
        String result;
        List<Integer> useSeq = new ArrayList<>();
    }
}
