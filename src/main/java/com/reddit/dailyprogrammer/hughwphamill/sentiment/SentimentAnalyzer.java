package com.reddit.dailyprogrammer.hughwphamill.sentiment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SentimentAnalyzer {

    Map<String, Integer> wordScore;

    public SentimentAnalyzer(String[] goodWords, String[] badWords) {
        wordScore = new HashMap<>();
        for (String word : goodWords) {
            wordScore.put(word.toLowerCase(), 1);
        }
        for (String word : badWords) {
            wordScore.put(word.toLowerCase(), -1);
        }
    }

    public int analyze(List<String> comments) {
        return comments.parallelStream().map(c -> c.toLowerCase())
                .map(c ->
                        Arrays.asList(c.split(" ")).parallelStream()
                                .map(w -> wordScore.get(w) == null ? 0 : wordScore.get(w))
                                .reduce(0, Integer::sum))
                .reduce(0, Integer::sum);
    }
}
