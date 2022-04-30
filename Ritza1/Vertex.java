package Ritza1;

import java.util.Vector;

public class Vertex {
    private final int id;
    private int key;
    Vertex PI = null;
    String color = "white";

    Vertex(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Vertex(Vertex a) {
        this.id = a.getId();
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
