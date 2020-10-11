package main.java;

import java.util.ArrayList;
import java.util.HashMap;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class WordNet {
    private final Digraph g;
    private final HashMap<String, HashMap<Integer, String>> synsetNounMap;
    private final ArrayList<String> synsetList;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if(synsets == null || hypernyms == null) {
            throw new IllegalArgumentException("constructor args shouldn be nulls");
        }
        synsetList = new ArrayList<>();
        synsetNounMap = new HashMap<>();
        In synsetsIn = new In(synsets);
        int synsetCount = 0;
        while (!synsetsIn.isEmpty()) {
            String synsetLine = synsetsIn.readLine();
            String[] line = synsetLine.split(",");
            String[] nouns = line[1].split(" ");
            synsetList.add(Integer.parseInt(line[0]), line[1]);

            if(!synsetNounMap.containsKey(nouns[0])) {
                HashMap<Integer, String> synsetMapValue = new HashMap<>();
                for (String noun: nouns) {
                    synsetMapValue.put(Integer.parseInt(line[0]), noun);
                    if (synsetNounMap.containsKey(noun)) {
                        synsetMapValue = synsetNounMap.get(noun);
                        synsetMapValue.put(Integer.parseInt(line[0]), noun);
                    }
                    synsetNounMap.put(noun, synsetMapValue);
                }
            } else {
                HashMap<Integer, String> synsetMapValue = synsetNounMap.get(nouns[0]);
                for (String noun: nouns) {
                    synsetMapValue.put(Integer.parseInt(line[0]), noun);
                    if(!synsetNounMap.containsKey(noun)) {
                        synsetNounMap.put(noun, synsetMapValue);
                    } else {
                        HashMap<Integer, String> tmpSynsetMapValue = synsetNounMap.get(noun);
                        tmpSynsetMapValue.put(Integer.parseInt(line[0]), noun);
                        synsetNounMap.put(noun, tmpSynsetMapValue);
                    }
                }
                synsetNounMap.put(nouns[0], synsetMapValue);
            }
            synsetCount++;
        }

        g = new Digraph(synsetCount);
        In hypernymsIn = new In(hypernyms);
        while (!hypernymsIn.isEmpty()) {
            String hypernymLine = hypernymsIn.readLine();
            String[] line = hypernymLine.split(",");
            int id = Integer.parseInt(line[0]);
            for(int i = 1; i < line.length; i++) {
                g.addEdge(id, Integer.parseInt(line[i]));
            }
        }

        DirectedCycle directedCycle = new DirectedCycle(g);
        if(directedCycle.hasCycle()) {
            throw new IllegalArgumentException("not a DAG");
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return synsetNounMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return synsetNounMap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if(!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("not a noun");
        }
        SAP sap = new SAP(g);
        HashMap<Integer, String> vMap = synsetNounMap.get(nounA);
        HashMap<Integer, String> wMap = synsetNounMap.get(nounB);

        return sap.length(vMap.keySet(), wMap.keySet());
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if(!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("not a noun");
        }
        SAP sap = new SAP(g);
        HashMap<Integer, String> vMap = synsetNounMap.get(nounA);
        HashMap<Integer, String> wMap = synsetNounMap.get(nounB);
        int ancestor = sap.ancestor(vMap.keySet(), wMap.keySet());

        return synsetList.get(ancestor);
    }

    public static void main(String[] args) {
        WordNet wordNet = new WordNet("synsets.txt", "hypernyms.txt");

        StdOut.println("zoophyte isNoun=" + wordNet.isNoun("zoophyte"));
        StdOut.println("AIDS isNoun=" + wordNet.isNoun("AIDS"));
        StdOut.println("ancestor between worm and bird = " + wordNet.sap("worm", "bird"));
        StdOut.println("distance between worm and bird = " + wordNet.distance("worm", "bird"));

        StdOut.println("ancestor between municipality and region = " + wordNet.sap("municipality", "region"));
        StdOut.println("distance between municipality and region = " + wordNet.distance("municipality", "region"));

        StdOut.println("ancestor between municipality and municipality = " + wordNet.sap("municipality", "municipality"));
        StdOut.println("distance between municipality and municipality = " + wordNet.distance("municipality", "municipality"));

        StdOut.println("ancestor between white_marlin and mileage = " + wordNet.sap("white_marlin", "mileage"));
        StdOut.println("distance between white_marlin and mileage = " + wordNet.distance("white_marlin", "mileage"));

        StdOut.println("ancestor between individual and edible_fruit = " + wordNet.sap("individual", "edible_fruit"));
        StdOut.println("distance between individual and edible_fruit = " + wordNet.distance("individual", "edible_fruit"));

        StdOut.println("ancestor between Black_Plague and black_marlin = " + wordNet.sap("Black_Plague", "black_marlin"));
        StdOut.println("distance between Black_Plague and black_marlin = " + wordNet.distance("Black_Plague", "black_marlin"));

        StdOut.println("ancestor between human and animal = " + wordNet.sap("human", "animal"));
        StdOut.println("distance between human and animal = " + wordNet.distance("human", "animal"));

        StdOut.println("ancestor between black_magic and wizard = " + wordNet.sap("black_magic", "wizard"));
        StdOut.println("distance between black_magic and wizard = " + wordNet.distance("black_magic", "wizard"));
    }
}
