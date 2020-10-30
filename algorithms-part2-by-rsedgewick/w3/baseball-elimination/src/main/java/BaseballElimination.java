package main.java;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;

public class BaseballElimination {
    private final String[] teamNames;
    private final int[] teamWins;
    private final int[] teamLosses;
    private final int[] teamGamesLeftTotal;
    private final int[][] gamesLeftToPlay;
    private final int teamNum;
    private final List<String> teamsInCut = new ArrayList<>();
    private boolean isEliminatedCalled = false;

    public BaseballElimination(String filename) {
        In in = new In(filename);
        teamNum = in.readInt();

        teamNames = new String[teamNum];
        teamWins = new int[teamNum];
        teamLosses = new int[teamNum];
        teamGamesLeftTotal = new int[teamNum];
        gamesLeftToPlay = new int[teamNum][teamNum];
        int counter = 0;
        while (!in.isEmpty()) {
            String line = in.readLine();
            if(line.isEmpty()) {
                continue;
            }
            String[] lineArray = line.split(" ");
            int lineArrayIter = 0;
            String[] newLineArray = new String[4 + teamNum];
            for (String s : lineArray) {
                if (s.isEmpty()) {
                    continue;
                }
                newLineArray[lineArrayIter++] = s;
            }
            teamNames[counter] = newLineArray[0];
            teamWins[counter] = Integer.parseInt(newLineArray[1]);
            teamLosses[counter] = Integer.parseInt(newLineArray[2]);
            teamGamesLeftTotal[counter] = Integer.parseInt(newLineArray[3]);
            for(int team = 0; team < teamNum; team++) {
                gamesLeftToPlay[team][counter] = Integer.parseInt(newLineArray[team + 4]);
            }
            counter++;
        }
    }
    public int numberOfTeams() {
        return teamNum;
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        int teamIdToEliminate = findTeamIdByName(team);

        isEliminatedCalled = true;

        //trivial case w[x] + r[x] < w[i]
        boolean triviallyEliminated = false;
        for(int i = 0; i < teamWins.length; i++) {
            if(i != teamIdToEliminate && teamWins[teamIdToEliminate] + teamGamesLeftTotal[teamIdToEliminate] < teamWins[i]) {
                triviallyEliminated = true;
                teamsInCut.add(teamNames[i]);
            }
        }
        if(triviallyEliminated) {
            return true;
        }

        int numOfGameVertices = (int) (factorial(teamNum - 1) / (factorial(teamNum - 3) * factorial(2)));
        int fnVerticesNum = numOfGameVertices + 2 + teamNum;
        FlowNetwork fn = new FlowNetwork(fnVerticesNum);
        int gameVertexCounter = 1;
        HashMap<Integer, Double> teamEdges = new HashMap<>();
        int allWins = 0;
        //set game vertices
        for(int i = 0; i < teamNum; i++) {
            if(i == teamIdToEliminate) {
                continue;
            }
            for (int j = i + 1; j < teamNum; j++) {
                if(j == teamIdToEliminate) {
                    continue;
                }
                allWins += gamesLeftToPlay[i][j];
                FlowEdge feStart = new FlowEdge(0, gameVertexCounter, gamesLeftToPlay[i][j]);
                fn.addEdge(feStart);

                FlowEdge feGameTeamOne = new FlowEdge(gameVertexCounter, numOfGameVertices + i + 1, Double.POSITIVE_INFINITY);
                fn.addEdge(feGameTeamOne);
                if(!teamEdges.containsKey(numOfGameVertices + i + 1)) {
                    allWins += teamWins[i];
                    double feEndTeamOneCapacity = (teamWins[teamIdToEliminate] + teamGamesLeftTotal[teamIdToEliminate]) - teamWins[i];
                    if(feEndTeamOneCapacity < 0) {
                        return false;
                    }
                    FlowEdge feEndTeamOne = new FlowEdge(numOfGameVertices + i + 1, fnVerticesNum - 1, feEndTeamOneCapacity);
                    fn.addEdge(feEndTeamOne);
                    teamEdges.put(numOfGameVertices + i + 1, feEndTeamOneCapacity);
                }
                FlowEdge feGameTeamTwo = new FlowEdge(gameVertexCounter, numOfGameVertices + j + 1, Double.POSITIVE_INFINITY);
                fn.addEdge(feGameTeamTwo);
                if(!teamEdges.containsKey(numOfGameVertices + j + 1)) {
                    allWins += teamWins[j];
                    double feEndTeamTwoCapacity = (teamWins[teamIdToEliminate] + teamGamesLeftTotal[teamIdToEliminate]) - teamWins[j];
                    if(feEndTeamTwoCapacity < 0) {
                        return false;
                    }
                    FlowEdge feEndTeamTwo = new FlowEdge(numOfGameVertices + j + 1, fnVerticesNum - 1, feEndTeamTwoCapacity);
                    fn.addEdge(feEndTeamTwo);
                    teamEdges.put(numOfGameVertices + j + 1, feEndTeamTwoCapacity);
                }
                gameVertexCounter++;
            }
        }

        FordFulkerson ff = new FordFulkerson(fn, 0, fnVerticesNum - 1);
        int teamsInCutCount = 0;
        for(int v = 0; v < fn.V(); v++) {
            if (ff.inCut(v) && checkTeam(v - (numOfGameVertices + 1))) {
                teamsInCutCount += 1;
                teamsInCut.add(teamNames[v - (numOfGameVertices + 1)]);
            }
        }
        if(teamsInCutCount == 0) {
            return false;
        }

        double averageR =  (double) allWins  / teamsInCutCount;
        return teamWins[teamIdToEliminate] + teamGamesLeftTotal[teamIdToEliminate] < averageR;
    }

    public Iterable<String> certificateOfElimination(String team) {
        if(!isEliminatedCalled) {
            isEliminated(team);
        }
        isEliminatedCalled = false;
        return teamsInCut;
    }

    public int wins(String team) {
        int teamId = findTeamIdByName(team);

        return teamWins[teamId];
    }

    public int losses(String team) {
        int teamId = findTeamIdByName(team);

        return teamLosses[teamId];
    }

    public int remaining(String team) {
        int teamId = findTeamIdByName(team);

        return teamGamesLeftTotal[teamId];
    }

    public int against(String team1, String team2) {
        int team1Id = findTeamIdByName(team1);
        int team2Id = findTeamIdByName(team2);

        return gamesLeftToPlay[team1Id][team2Id];
    }

    public Iterable<String> teams() {
        return Arrays.asList(teamNames);
    }

    private int findTeamIdByName(String teamName) {
        int result = - 1;
        for(int i = 0; i < teamNames.length; i++) {
            if(teamNames[i].equals(teamName)) {
                result = i;
                break;
            }
        }
        if(result == -1) {
            throw new IllegalArgumentException("no such team: " + teamName);
        }
        return result;
    }

    private boolean checkTeam(int i) {
        return i >= 0 && i < teamNames.length;
    }

    private double factorial(int n) {
        double result = 1.0;
        for(int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
