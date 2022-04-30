package Ritza1;

import java.util.Vector;

public class Edge {
    final private Vertex a,b;
    private int weight;

    Edge(Vertex a,Vertex b,int weight)
    {
        this.a = new Vertex(a);
        this.b = new Vertex(b);
        this.weight = weight;
    }

    public Edge(Edge edge) {
        this.a = new Vertex(edge.getVertexes()[0].getId());
        this.b = new Vertex(edge.getVertexes()[1].getId());
        this.weight = edge.getWeight();
    }

    public Vertex[] getVertexes() {
        Vertex[] ver = new Vertex[2];
        ver[0] = this.a;
        ver[1] = this.b;

        return ver;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean equals(Edge e) {
        if(e.getVertexes()[0].getId() == this.getVertexes()[0].getId() && e.getVertexes()[1].getId() == this.getVertexes()[1].getId()) {
            return true;
        } else if(e.getVertexes()[0].getId() == this.getVertexes()[1].getId() && e.getVertexes()[1].getId() == this.getVertexes()[0].getId()) {
            return true;
        }

        return false;
    }
}