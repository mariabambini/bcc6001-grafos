/*
 * Data última atualização: 08/11/2026
 * Implementa a classe GEXFParser que lê um arquivo do tipo GEXF e monta um Graph usando o construtor Graph(int V) e o método addEdge(v, w).
 */

import java.io.File;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* Classe de parse. */
public class GexfParser {

    private Graph graph;
    private HashMap<String, Integer> indexID;
    private String[] labels;

    public GexfParser(String caminhoArquivo) throws Exception {
        indexID = new HashMap<String, Integer>();
        read(caminhoArquivo);
    }

    private void read(String filePath) throws Exception {
        File file = new File(filePath);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();

        NodeList nodeList = document.getElementsByTagName("node");
        int nodeCount = nodeList.getLength();
        labels = new String[nodeCount];

        for (int i = 0; i < nodeCount; i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element nodeElement = (Element) node;
                String id = nodeElement.getAttribute("id");
                String label = nodeElement.getAttribute("label");

                indexID.put(id, i);
                labels[i] = label;
            }
        }

        graph = new Graph(nodeCount);

        NodeList edgeList = document.getElementsByTagName("edge");
        int edgeCount = edgeList.getLength();

        for (int i = 0; i < edgeCount; i++) {
            Node edge = edgeList.item(i);
            if (edge.getNodeType() == Node.ELEMENT_NODE) {
                Element edgeElement = (Element) edge;
                String origin = edgeElement.getAttribute("source");
                String dest = edgeElement.getAttribute("target");

                int v = indexID.get(origin);
                int w = indexID.get(dest);

                graph.addEdge(v, w);
            }
        }
    }

    /* Métodos da classe */

    public Graph getGrafo() {
        return graph;
    }

    public String getLabel(int indice) {
        return labels[indice];
    }

    public int getIndice(String id) {
        return indexID.get(id);
    }

    public static void main(String[] args) throws Exception {
        GexfParser reader = new GexfParser(args[0]);
        Graph Graph = reader.getGrafo();

        System.out.println("\n" + Graph.V() + " vertices, " + Graph.E() + " arestas");

        for (int v = 0; v < Graph.V(); v++) {
            System.out.print(reader.getLabel(v) + " (" + v + "): ");
            java.util.Iterator<Integer> iterator = Graph.adj(v).iterator();
            while (iterator.hasNext()) {
                int w = iterator.next();
                System.out.print(w + " ");
            }
            System.out.println();
        }
    }
}
