/*
 * Data última atualização: 11/11/2026
 * Implementa a classe ClosenessCentrality que computa a métrica Closeness Centrality (CC) de um grafo.
 * Considerando um grafo G com N vértices, a CC de um vértice x é dada por C_B(x) = (N - 1) / sum_y(d(y, x)) tal que y != x. 
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

public class ClosenessCentrality {
    public ClosenessCentrality(Graph G) {
        if (G == null || G.V() == 0) {
            throw new IllegalArgumentException("O grafo fornecido não pode ser nulo ou vazio.");
        }

        this.G = G;
        this.cc = new double[G.V()];

        for (int v = 0; v < G.V(); v++) {
            computeClosenessCentrality(v);
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Uso: java ClosenessCentrality <caminho_grafo>");
            return;
        }

        Graph graph;
        GexfParser parser;
    
        parser = new GexfParser(args[0]);
        graph = parser.getGrafo();
        
        ClosenessCentrality ccMetric = new ClosenessCentrality(graph);
        ccMetric.saveClosenessCentrality(args[1]);
    }

    // Retorna a CC do vértice v
    public double closenessCentrality(int v) {
        return cc[v];
    }

    // Salva o CC de cada vértice em G em um arquivo texto
    public void saveClosenessCentrality(String outputPath) {
        try (PrintWriter writer = new PrintWriter(outputPath)) {
            writer.printf("%-8s | %s\n", "Vértice", "Closeness Centrality");

            for (int v = 0; v < G.V(); v++) {
                String ccStr = String.format("%.5f", cc[v]);
                writer.printf("%-8d | %s\n", v, ccStr);
            }
            
            System.out.println("Closeness Centrality salva " + outputPath);
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }

    // Computa a CC de um vértice v
    private void computeClosenessCentrality(int v) {
        int V = this.G.V();

        // BFS para encontrar a menor distância de v para todo u em G.V() - {v}
        int[] distTo = new int[V];
        boolean[] marked = new boolean[V];
        Queue<Integer> queue = new LinkedList<>();

        marked[v] = true;
        queue.add(v);

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int w : this.G.adj(u)) {
                if (!marked[w]) {
                    distTo[w] = distTo[u] + 1;
                    marked[w] = true;
                    queue.add(w);
                }
            }
        }
        
        // Soma das distâncias
        double sum = 0.0;

        for (int i = 0; i < V; i++) {
            sum += distTo[i];
        }
        
        // Normalização da soma
        if (sum == 0.0) {
            this.cc[v] = 0.0;
        } 
        else {
            this.cc[v] = (double)(V - 1) / sum;
        }
    }
    
    private final Graph G;
    private final double[] cc;  // vetor com a CC de cada vértice
}
