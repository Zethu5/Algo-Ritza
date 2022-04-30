package Ritza1;

import java.util.Vector;

public class Graph {
    private Vector<Vertex> vertexes;
    private Vector<Edge> edges;

    Graph() {
        this.vertexes = new Vector<Vertex>();
        this.edges = new Vector<Edge>();
    }

    public void addVertex(Vertex v) {
        this.vertexes.add(v);
    }

    public void removeVertex(Vertex v) {
        // remove all edges that contain the vertex
        this.edges.removeIf(e -> e.getVertexes()[0].getId() == v.getId() || e.getVertexes()[1].getId() == v.getId());
        this.vertexes.remove(v);
    }

    public void addEdge(Edge e) {
        this.edges.add(e);
    }

    public void removeEdge(Edge e) {
        this.edges.remove(e);
    }

    public Vector<Vertex> getVertexes() {
        if (!this.vertexes.isEmpty()) {
            return this.vertexes;
        }

        return null;
    }

    public Vector<Edge> getEdges() {
        if (!this.edges.isEmpty()) {
            return this.edges;
        }

        return null;
    }

    public boolean vertexExists(Vertex vertex) {
        for (Vertex v : this.vertexes) {
            if (v.getId() == vertex.getId())
                return true;
        }

        return false;
    }

    public boolean edgeExists(Edge e) {
        for (Edge edge : this.edges) {
            if (edge.equals(e)) {
                return true;
            }
        }

        return false;
    }

    public Edge getEdge(Vertex a, Vertex b) {
        Edge e = new Edge(a,b,0);
        for (Edge edge : this.edges) {
            if (edge.equals(e)) {
                return edge;
            }
        }

        return null;
    }

    public Vertex extractMin(Graph g) {
        Vertex min_vertex = g.getVertexes().firstElement();

        for (Vertex v : g.getVertexes()) {
            if (v.getKey() < min_vertex.getKey()) {
                min_vertex = v;
            }
        }

        if (min_vertex != null) {
            g.getVertexes().remove(min_vertex);
            return min_vertex;
        }

        return null;
    }

    public Vector<Integer> adj(Graph g, Vertex v) {
        Vector<Integer> ret = new Vector<Integer>();

        for (Edge e : g.getEdges()) {
            if (e.getVertexes()[0].getId() == v.getId()) {
                ret.add(e.getVertexes()[1].getId());
            } else if (e.getVertexes()[1].getId() == v.getId()) {
                ret.add(e.getVertexes()[0].getId());
            }
        }

        return ret;
    }

    public int getVertexIndex(int vertex_id) {
        int counter = 0;
        for (Vertex v : this.getVertexes()) {
            if (v.getId() == vertex_id) {
                return counter;
            }

            counter++;
        }

        return -1;
    }

    public int weightFunction(Vertex a, Vertex b) {
        Integer weight = null;

        for (Edge e : this.getEdges()) {
            if (e.getVertexes()[0].getId() == a.getId() && e.getVertexes()[1].getId() == b.getId()) {
                weight = e.getWeight();
            } else if (e.getVertexes()[1].getId() == a.getId() && e.getVertexes()[0].getId() == b.getId()) {
                weight = e.getWeight();
            }
        }

        return weight;
    }

    // function for task 1
    public Graph prim(Vertex r) {
        Graph g = this;
        Vector<Vertex> vertexSaver = new Vector<Vertex>();
        vertexSaver.addAll(this.vertexes);

        Graph minimalTree = new Graph();

        for (int i = 0; i < g.getVertexes().size(); i++) {
            g.getVertexes().get(i).setKey(Integer.MAX_VALUE);
        }

        r.setKey(0);
        r.PI = null;

        while (g.getVertexes() != null) {
            Vertex u = extractMin(g);
            minimalTree.addVertex(u);

            if (u.PI != null) {
                minimalTree.addEdge(new Edge(u.PI, u, u.getKey()));
            }

            for (int vertex_id : adj(g, u)) {
                if (g.getVertexes() != null && getVertexIndex(vertex_id) != -1) {
                    Vertex v = g.getVertexes().get(getVertexIndex(vertex_id));
                    int weight_edge = weightFunction(u, v);

                    if (weight_edge < v.getKey()) {
                        v.PI = u;
                        v.setKey(weightFunction(u, v));
                    }
                }
            }
        }

        this.vertexes.addAll(vertexSaver);
        return minimalTree;
    }

    public Vector<Edge> dfs(Graph mst, Vertex vertex, Vector<Edge> circle) {
        vertex.setColor("grey");

        for (Integer vertex_id : adj(mst, vertex)) {
            Vertex v = mst.getVertexes().get(getVertexIndex(vertex_id));

            if (v.getColor() == "grey" && v != vertex.PI) {
                v.PI = vertex;
                circle.add(new Edge(v, v.PI, weightFunction(v, v.PI)));

                while (vertex != v) {
                    circle.add(new Edge(vertex, vertex.PI, weightFunction(vertex, vertex.PI)));
                    vertex = vertex.PI;
                }

                return circle;
            } else if (v.getColor() == "white") {
                v.PI = vertex;
                dfs(mst, v, circle);
            }
        }

        return circle;
    }

    public void updateMST(Edge e) {
        Graph mst = this;
        mst.addEdge(e);

        // reset the graph
        mst.getVertexes().forEach(v -> v.setColor("white"));
        mst.getVertexes().forEach(v -> v.PI = null);

        // doesn't matter which vertex we take, so we took the first one
        Vertex vertex = mst.getVertexes().firstElement();
        Vector<Edge> circle = new Vector<Edge>();
        Vector<Edge> returned_circle = new Vector<Edge>(dfs(mst, vertex, circle));

        int max = Integer.MIN_VALUE;
        Edge max_weight_edge = null;

        for (Edge edge : returned_circle) {
            if (edge.getWeight() >= max) {
                max = edge.getWeight();
                max_weight_edge = edge;
            }
        }

        for (Edge edge : mst.getEdges()) {
            if (edge.equals(max_weight_edge)) {
                mst.removeEdge(edge);
                break;
            }
        }
    }

    public void createRandomGraph(int num_vertexes) {
        // don't create a graph on an existent one
        if (this.vertexes.size() > 0 || this.edges.size() > 0) {
            return;
        }

        // add all vertexes
        for (int i = 0; i < num_vertexes; i++) {
            this.addVertex(new Vertex(i));
        }

        // add to each vertex 3-5 edges (to have 50 or more edges)
        // and also that all the vertices in the graph will be connected
        for (int i = 0; i < this.vertexes.size(); i++) {
            int rnd_num_edges = (int) (3 + (Math.random() * (6 - 3)));
            int rnd_vertex_index;

            for (int j = 0; j < rnd_num_edges; j++) {
                // get random vertex from the 20 we created that is not
                // the one we are using now
                rnd_vertex_index = i;

                while (rnd_vertex_index == i) {
                    rnd_vertex_index = (int) (0 + (Math.random() * (20 - 0)));
                }

                // set random weight to the edge from -2 to 2
                int rnd_edge_weight = (int) ((-2) + (Math.random() * (3 - (-2))));

                // add new edge to graph
                Edge edge = new Edge(this.vertexes.get(i), this.vertexes.get(rnd_vertex_index), rnd_edge_weight);

                if (!edgeExists(edge)) {
                    this.addEdge(edge);
                }
            }
        }
    }

    public void printGraph() {
        for(int i = 0; i < this.vertexes.size(); i++) {
            System.out.println("VERTEX " + i + ":");
            for(int j = 0; j < this.vertexes.size(); j++) {
                if(i != j) {
                    // the weight doesn't matter when checking if the edge exists
                    Edge edge = new Edge(this.vertexes.get(i), this.vertexes.get(j),0);
                    if(this.edgeExists(edge)) {
                        Edge edge_to_print = this.getEdge(this.vertexes.get(i),this.vertexes.get(j));
                        System.out.print(edge_to_print.getVertexes()[0].getId() + " <---> " + edge_to_print.getVertexes()[1].getId() + " [weight:"+ edge_to_print.getWeight() + "] | ");
                    }
                }
            }
            System.out.println("\n");
        }
    }
}
