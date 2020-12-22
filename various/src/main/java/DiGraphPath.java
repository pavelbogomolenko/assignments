package main.java;

public class DiGraphPath {

    private DiGraph G;
    private final int[] path;
    private final boolean[] visited;

    public DiGraphPath(DiGraph diGraph, int s) {
        this.G = diGraph;
        visited = new boolean[diGraph.vertices()];
        path = new int[diGraph.vertices()];
        dfs(s);
    }

    public boolean pathExists(int e) {
        return false;
    }

    private void dfs(int v) {
        if(visited[v]) {
            return;
        }

        visited[v] = true;
        for (int adj: G.adj(v)) {
            if(!visited[adj]) {
                path[adj] = v;
                dfs(adj);
            }
        }
    }

    public static void main(String[] args) {
        DiGraph diGraph = new DiGraph(5);
        diGraph.addEdge(0, 1);
        diGraph.addEdge(1, 2);
        diGraph.addEdge(2, 3);
        diGraph.addEdge(3, 4);

        DiGraphPath diGraphPath = new DiGraphPath(diGraph, 0);
        diGraphPath.pathExists(4);
    }
}
