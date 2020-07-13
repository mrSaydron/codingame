package ru.mrak.codingame.easy.bulkEmailGenerator;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Solution {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        System.err.println(N);
        if (in.hasNextLine()) {
            in.nextLine();
        }

        Pattern inBracket = Pattern.compile("(?s)\\((.*?)\\)");
        Pattern splitPattern = Pattern.compile("((\\w|\\s)+)|(^|\\|)()(\\||$)");
        String[] emailSplit = new String[N];
        for (int i = 0; i < N; i++) {
            emailSplit[i] = in.nextLine();
        }
        String email = String.join("\n", emailSplit);
        System.err.println(email);

        int j = 0;
        Matcher inMatcher = inBracket.matcher(email);
        while(inMatcher.find()) {
            Matcher splitMatcher = splitPattern.matcher(inMatcher.group(1));
            List<String> subStrings = new ArrayList<>();
            while (splitMatcher.find()) {
                subStrings.add((splitMatcher.group(1) != null ? splitMatcher.group(1) : "")
                        + (splitMatcher.group(4) != null ? splitMatcher.group(4) : ""));
            }
            String subString = subStrings.get(j % subStrings.size());
            email = email.replaceFirst("(?s)\\(.*?\\)", subString);
            j++;
        }
        System.out.println(email);
    }
}