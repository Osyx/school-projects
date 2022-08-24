import se.kth.id1020.Edge;
import se.kth.id1020.Graph;
import se.kth.id1020.DataSource;

import java.util.Iterator;

/**
 * Created by Oscar on 16-12-09.
 */
public class Paths {

    public static void main(String[] args) {
        Graph graph = DataSource.load();
        Paths paths = new Paths();
        Node[] newGraphStructure = paths.createDataStructure(graph);
        DepthFirstSearch depthFirstSearch = new DepthFirstSearch(newGraphStructure);
        System.out.println("Number of subtrees: " + depthFirstSearch.countSubtrees(newGraphStructure));
        paths.getDistance(graph, "Parses", "Renyn", true);
        paths.getDistance(graph, "Parses", "Renyn", false);
        paths.getPath(graph, "Renyn", "Parses", true);
        paths.getPath(graph, "Renyn", "Parses", false);
    }

    private Node[] createDataStructure(Graph givenGraph) {
        Node[] newDataStructure = new Node[givenGraph.numberOfVertices()];
        Iterator<Edge> edgeIterator = givenGraph.edges().iterator();
        Edge currentEdge = edgeIterator.next();
        for (int i = 0; i < givenGraph.numberOfEdges() - 1; i++) {
            if(newDataStructure[currentEdge.from] == null) {
                newDataStructure[currentEdge.from] = new Node(currentEdge.to);
            } else {
                Node currentFrom = newDataStructure[currentEdge.from];
                while(currentFrom.getNext() != null)
                    currentFrom = currentFrom.getNext();
                if (currentFrom.getNeighbor() != currentEdge.to)
                    currentFrom.add(currentEdge.to);
            }
            currentEdge = edgeIterator.next();
        }
        return newDataStructure;
    }

    private void getDistance(Graph graph, String fromName, String toName, boolean weighted) {
        Dijkstra dijkstra = new Dijkstra(graph, fromName, weighted);
        System.out.println("Distance to " + toName + " from " + fromName + ":\n" + dijkstra.getDistTo(toName));
    }

    private void getPath(Graph graph, String fromName, String toName, boolean weighted) {
        Dijkstra dijkstra = new Dijkstra(graph, fromName, weighted);
        for(String name : dijkstra.getPath(fromName, toName)) {
            if (name.matches(fromName)) {
                System.out.println(name);
                break;
            }
            System.out.print(name + "->");
        }
    }

    class Node {
        int neighbor;
        Node next;

        Node(int neighbor) {
            this.neighbor = neighbor;
        }

        void add(int neighbor) {
            addNext(new Node(neighbor));
        }

        void addNext(Node next) {
            this.next = next;
        }

        int getNeighbor() {
            return neighbor;
        }

        Node getNext() {
            return next;
        }
    }

}