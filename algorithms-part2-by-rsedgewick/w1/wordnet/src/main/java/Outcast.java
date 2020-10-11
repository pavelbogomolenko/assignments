package main.java;

import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wordNet;

    public Outcast(WordNet wordnet) {
        if(wordnet == null) {
            throw new IllegalArgumentException();
        }
        wordNet = wordnet;
    }

    public String outcast(String[] nouns) {
        int maxDist = 0;
        String maxDistNoun = "";
        int dist = 0;
        for (int i = 0; i < nouns.length; i++) {
            for (int j = 0; j < nouns.length; j++) {
                dist += wordNet.distance(nouns[i], nouns[j]);
            }
            if(dist > maxDist) {
                maxDist = dist;
                maxDistNoun = nouns[i];
            }
            dist = 0;
        }
        return maxDistNoun;
    }

    public static void main(String[] args) {
        WordNet w = new WordNet("synsets.txt", "hypernyms.txt");
        Outcast o = new Outcast(w);
        String[] nouns0 = {"horse", "zebra", "cat", "bear", "table"};
        StdOut.println(o.outcast(nouns0));

        String[] nouns1 = {"apple", "pear", "peach", "banana", "lime", "lemon", "blueberry", "strawberry", "mango", "watermelon", "potato"};
        StdOut.println(o.outcast(nouns1));

        String[] nouns2 = {"lumber", "wood", "tree", "leaf", "nail", "house", "building", "edifice", "structure"};
        StdOut.println(o.outcast(nouns2));

        String[] nouns3 = {"Dylan", "folk", "Guthrie", "idol", "Minneapolis", "music", "musical", "playing", "public", "recognition", "review", "thunderbird"};
        StdOut.println(o.outcast(nouns3));
    }
}
