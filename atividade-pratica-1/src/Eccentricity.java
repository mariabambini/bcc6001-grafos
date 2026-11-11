import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import utils.StdOut;


/*
*   Classe que calcula a excentricidade de um dado vértice V.
*   A excentricidade é o maior caminho entre esse vértice V e qualquer outro vértice do Grafo.
*/ 
public class Eccentricity {
    private final Graph G;
    private final int[] ecc;             // ecc[v] = excentricidade do vértice v
    private int radius;                  // menor excentricidade no grafo
    private int diameter;                // maior excentricidade no grafo

    public Eccentricity(Graph G) {
        this.G = G;
        int V = G.V();
        this.ecc = new int[V];
        this.radius = Integer.MAX_VALUE;
        this.diameter = 0;

        for (int v = 0; v < V; v++) {
            computeBFS(v);
        }
    }

    private void computeBFS(int source) {
        int V = G.V();
        int[] distTo = new int[V];
        for (int i = 0; i < V; i++) {
            distTo[i] = -1; // não alcançado
        }

        Queue<Integer> queue = new ArrayDeque<>();
        distTo[source] = 0;
        queue.add(source);

        int maxDist = 0;
        int reachableCount = 0;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            reachableCount++;

            for (int neighbor : G.adj(current)) {
                if (distTo[neighbor] == -1) {
                    distTo[neighbor] = distTo[current] + 1;
                    if (distTo[neighbor] > maxDist) {
                        maxDist = distTo[neighbor];
                    }
                    queue.add(neighbor);
                }
            }
        }

        this.ecc[source] = maxDist;

        if (maxDist < this.radius) {
            this.radius = maxDist;
        }
        if (maxDist > this.diameter) {
            this.diameter = maxDist;
        }
    }

    //  Retorna a excentricidade do vértice v.
    public int eccentricity(int v) {
        return ecc[v];
    }

    public int radius() {
        return radius;
    }

    public int diameter() {
        return diameter;
    }

    public List<Integer> center() {
        List<Integer> centerNodes = new ArrayList<>();
        for (int v = 0; v < G.V(); v++) {
            if (ecc[v] == radius) {
                centerNodes.add(v);
            }
        }
        return centerNodes;
    }

    public void printEccentricities(String[] labels) {
        StdOut.printf("%-10s | %-15s\n", "Vértice", "Excentricidade");
        StdOut.println("------------------------------------------------------------");

        for (int v = 0; v < G.V(); v++) {
            String label = (labels != null && v < labels.length && labels[v] != null) 
                           ? labels[v] 
                           : String.valueOf(v);
            
            String eccStr = String.valueOf(ecc[v]);
            StdOut.printf("%-10d | %-15s\n", v, eccStr);
        }
    }

    public static void main(String[] args) throws Exception {
        Graph graph;
        GexfParser parser;
        String[] labels = null;
       
        parser = new GexfParser("/home/andre/Documentos/Git/bcc6001-grafos/atividade-pratica-1/data/input/LesMiserables.gexf");
        graph = parser.getGrafo();
        
        Eccentricity eccentricityMetric = new Eccentricity(graph);
        eccentricityMetric.printEccentricities(labels);
    }
}
