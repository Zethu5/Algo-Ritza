package Ritza1;

public class Main {

    public static void main(String[] args) {
        // =================== PRINT GRAPH ===================

        Graph test = new Graph();
        int num_vertexes = 20;
        test.createRandomGraph(num_vertexes);

        System.out.println("============================================== GRAPH: ==============================================");
        System.out.print("VERTEXES: ");
        test.getVertexes().forEach(v -> System.out.print(v.getId() + " "));
        System.out.print("\n");
        System.out.print("NUMBER OF EDGES: " + test.getEdges().size() + "\n");
        System.out.println("[INFO] EDGES ARE INTENTIONALLY PRINTED TWICE FOR BOTH VERTICES FOR BETTER READABILITY\n");
        test.printGraph();

        // =================== PRINT GRAPH ===================

        // =================== FIND & PRINT MST ===================

        System.out.println("============================================== FIND & PRINT MST: ==============================================");

        int random_index = (int) (0 + (Math.random() * (num_vertexes - 0)));
        System.out.println("RANDOM VERTEX INDEX SELECTED: " + random_index);
        Graph mst = test.prim(test.getVertexes().elementAt(random_index));
        System.out.println("MST FOUND:");
        mst.getEdges().forEach(e -> System.out.print(e.getVertexes()[0].getId() + " <---> " + e.getVertexes()[1].getId() + " [weight:" + e.getWeight() + "] | "));
        System.out.println("\n");

        // =================== FIND & PRINT MST ===================

        // =================== ADD EDGE TO GRAPH THAT WONT CHANGE MST ===================

        System.out.println("============================================== ADD EDGE TO GRAPH THAT WONT CHANGE MST: ==============================================");

        // Adding a new edge with the max weight of 2 so that the MST won't change
        int random_vertex_index_1 = (int) (0 + (Math.random() * (num_vertexes - 0)));
        int random_vertex_index_2 = (int) (0 + (Math.random() * (num_vertexes - 0)));
        Edge newEdge = new Edge(mst.getVertexes().get(random_vertex_index_1),mst.getVertexes().get(random_vertex_index_2),2);

        while(mst.edgeExists(newEdge) || random_vertex_index_1 == random_vertex_index_2) {
            random_vertex_index_1 = (int) (0 + (Math.random() * (num_vertexes - 0)));
            random_vertex_index_2 = (int) (0 + (Math.random() * (num_vertexes - 0)));
            newEdge = new Edge(mst.getVertexes().get(random_vertex_index_1),mst.getVertexes().get(random_vertex_index_2),2);
        }

        System.out.print("Added edge: " + newEdge.getVertexes()[0].getId() + " <---> " + newEdge.getVertexes()[1].getId() + ", weight: " + 2 + "\n");
        mst.updateMST(newEdge);
        System.out.print("MST AFTER CHANGE: \n");
        mst.getEdges().forEach(e -> System.out.print(e.getVertexes()[0].getId() + " <---> " + e.getVertexes()[1].getId() + " [weight:" + e.getWeight() + "] | "));
        System.out.println("\n");

        // =================== ADD EDGE TO GRAPH THAT WONT CHANGE MST ===================

        // =================== ADD EDGE TO GRAPH THAT WILL CHANGE MST ===================

        System.out.println("============================================== ADD EDGE TO GRAPH THAT WILL CHANGE MST: ==============================================");

        // Adding the same edge with the min weight of -2 so that the MST will change
        newEdge = new Edge(mst.getVertexes().get(random_vertex_index_1),mst.getVertexes().get(random_vertex_index_2),-2);

        System.out.print("Added edge: " + newEdge.getVertexes()[0].getId() + " <---> " + newEdge.getVertexes()[1].getId() + ", weight: " + -2 + "\n");
        mst.updateMST(newEdge);
        System.out.print("MST AFTER CHANGE: \n");
        mst.getEdges().forEach(e -> System.out.print(e.getVertexes()[0].getId() + " <---> " + e.getVertexes()[1].getId() + " [weight:"+ e.getWeight() + "] | "));
        System.out.println("\n");

        // =================== ADD EDGE TO GRAPH THAT WILL CHANGE MST ===================
    }
}