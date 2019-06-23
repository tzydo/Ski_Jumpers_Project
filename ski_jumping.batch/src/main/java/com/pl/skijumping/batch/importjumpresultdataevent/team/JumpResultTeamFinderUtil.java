package com.pl.skijumping.batch.importjumpresultdataevent.team;

import com.pl.skijumping.batch.matchingword.RegexpPattern;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class JumpResultTeamFinderUtil {
    private JumpResultTeamFinderUtil() {
//
    }

    static String replaceByPattern(String word, String regexpPattern, String replacement) {
        Pattern pattern = Pattern.compile(regexpPattern);
        Matcher matcher = pattern.matcher(word);

        int index = 0;
        while (matcher.find()) {
            word = word.replaceFirst(matcher.group(0), String.format("%s%02d", replacement, index++));
        }
        return word;
    }

    static Set<String> divideIntoPartsByPattern(String word, String endPattern) {
        int countMatchingWords = word.split(endPattern, -1).length - 1;
        Set<String>resultList = new HashSet<>();
        long limit = countMatchingWords / 5;
        for (int i = 4; ; i = i + 5) {
            if (limit-- == 0) break;
            String regexpPattern = String.format("%s(.*?)%s%02d", RegexpPattern.JUMP_RESULT_DATA_BEGINING_PART, endPattern, i);
            word = getMatchedWord(word, resultList, regexpPattern);
        }
        return resultList;
    }

    private static String getMatchedWord(String word, Set<String> resultList, String regexpPattern) {
        Pattern pattern = Pattern.compile(regexpPattern);
        Matcher matcher = pattern.matcher(word);
        if (matcher.find()) {
            resultList.add(matcher.group(0));
            word = word.replaceFirst(regexpPattern, "");
        }
        return word;
    }

}
