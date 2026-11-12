/*
 * Data última atualização: 12/11/2026
 * Implementa a classe BetweennessCentrality que computa a métrica Betweenness Centrality (BC) normalizada de um grafo com o algoritmo de Brandes.
 * A Betweenness Centrality de um vértice v indica a soma das proporção de caminhos mais curtos que vão de s até t passando por v, sendo v,s,t vértices de G e s,t diferentes de v. 
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import utils.Stack;

public class BetweennessCentrality {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Uso: java BetweennessCentrality <caminho_grafo>");
            return;
        }

        Graph graph;
        GexfParser parser;
    
        parser = new GexfParser(args[0]);
        graph = parser.getGrafo();
        
        BetweennessCentrality bcMetric = new BetweennessCentrality(graph);
        bcMetric.saveBetweennessCentrality(args[1]);
    }

    // Construtor
    public BetweennessCentrality(Graph G) {
        if (G == null || G.V() == 0) {
            throw new IllegalArgumentException("O grafo fornecido não pode ser nulo ou vazio.");
        }

        this.G = G;
        this.bc = new double[G.V()];

        computeBetweennessCentrality();
    }

    // Retorna a BC do vértice v
    public double betweennessCentrality(int v) {
        return bc[v];
    }

    // Salva o BC de cada vértice em G em um arquivo texto
    public void saveBetweennessCentrality(String outputPath) {
        try (PrintWriter writer = new PrintWriter(outputPath)) {
            writer.printf("%-8s | %s\n", "Vértice", "Betweenness Centrality");

            for (int v = 0; v < G.V(); v++) {
                String bcStr = String.format("%.5f", bc[v]);
                writer.printf("%-8d | %s\n", v, bcStr);
            }
            
            System.out.println("Betweenness Centrality salva " + outputPath);
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }

    // Computa a BC de um grafo G utilizando o algoritmo de Brandes.
    // 
    private void computeBetweennessCentrality() {
        int V = this.G.V();

        for (int s = 0; s < V; s++) {
            Stack<Integer> st = new Stack<>();
            List<List<Integer>> prev = new ArrayList<>(V);
            double[] sigma = new double[V];
            double[] delta = new double[V];
            int[] dist = new int[V];

            for (int u = 0; u < V; u++) {
                prev.add(new ArrayList<>());
                sigma[u] = 0.0;
                dist[u] = -1;
            }

            sigma[s] = 1;
            dist[s] = 0;

            Queue<Integer> queue = new LinkedList<>();
            queue.add(s);

            // BFS
            while (!queue.isEmpty()) {
                int v = queue.poll();
                st.push(v);

                for (int w : this.G.adj(v)) {
                    // Se o vertice for visitado pela primeira vez, o adiciona à fila
                    if (dist[w] < 0) {
                        dist[w] = dist[v] + 1;
                        queue.add(w);
                    }

                    // Se v for parte de um caminho mais curto para w, soma a contagem de caminhos de v para a soma de w e 
                    // adiciona v como prev de w.
                    if (dist[w] == dist[v] + 1) {
                        sigma[w] += sigma[v];
                        prev.get(w).add(v);
                    }
                }
            }

            // Backpropagation
            while (!st.isEmpty()) {
                int v = st.pop();

                for (int u : prev.get(v)) {
                    delta[u] += (sigma[u] / sigma[v]) * (1.0 + delta[v]);
                }

                if (v != s) {
                    this.bc[v] += delta[v] / 2.0; // Dividido por dois por ser um grafo não direcionado
                }
            }
        }
    }
    
    private final Graph G;
    private final double[] bc;  // vetor com a BC de cada vértice
}
