import edu.princeton.cs.algs4.IndexMinPQ;
import se.kth.id1020.Edge;
import se.kth.id1020.Graph;
import se.kth.id1020.Vertex;

/**
 * Created by Oscar on 16-12-11.
 */
public class Dijkstra {
    private Edge[] edgeTo;
    private  double[] distTo;
    private IndexMinPQ<Double> priorityQueue;
    private Graph graph;


    Dijkstra(Graph graph, String name, boolean weighted) {
        this.graph = graph;
        int nodeInt = -1;
        for (Vertex vertex: graph.vertices()) {
            if (vertex.label.matches(name))
                nodeInt = vertex.id;
        }
        edgeTo = new Edge[graph.numberOfVertices()];
        distTo = new double[graph.numberOfVertices()];
        priorityQueue = new IndexMinPQ<Double>(graph.numberOfVertices());

        for (int i = 0; i < graph.numberOfVertices(); i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        distTo[nodeInt] = 0.0;

        priorityQueue.insert(nodeInt, 0.0);
        while(!priorityQueue.isEmpty())
            relax(priorityQueue.delMin(), weighted);
    }

    private void relax(int currentVertex, boolean weighted) {
        for (Edge currentEdge : graph.adj(currentVertex)) {
            int currentTo = currentEdge.to;
            double weight = 1;
            if (weighted)   weight = currentEdge.weight;
            if(distTo[currentTo] > distTo[currentVertex] + weight) {
                distTo[currentTo] = distTo[currentVertex] + weight;
                edgeTo[currentTo] = currentEdge;
                if (priorityQueue.contains(currentTo)) priorityQueue.changeKey(currentTo, distTo[currentTo]);
                else priorityQueue.insert(currentTo, distTo[currentTo]);
            }
        }
    }

    String[] getPath(String from , String to) {
        int[] ints = getNameInt(from, to);
        String[] path = new String[30];
        Edge current = edgeTo[ints[1]];
        path[0] = graph.vertex(current.to).label;
        int i = 1;
        while(current.from != ints[0]) {
            path[i] = graph.vertex(current.from).label;
            current = edgeTo[current.from];
            i++;
        }
        path[i] = graph.vertex(current.from).label;
        return path;
    }

    double getDistTo(String name) {
        return distTo[getNameInt("", name)[1]];
    }

    private int[] getNameInt(String from, String to) {
        int fromInt = -1;
        int toInt = -1;
        for (Vertex vertex: graph.vertices()) {
            if (vertex.label.matches(from))
                fromInt = vertex.id;
            if(vertex.label.matches(to))
                toInt = vertex.id;
        }
        int[] ints = {fromInt, toInt};
        return ints;
    }
}
