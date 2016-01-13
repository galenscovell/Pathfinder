package processing;

public class Node {
    private Node parent;
    private Tile tile;
    private double cost;

    public Node(Tile tile) {
        this.tile = tile;
        this.cost = Double.POSITIVE_INFINITY;
    }

    public Tile getTile() {
        return tile;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public double getCost() {
        return cost;
    }

    public Node getParent() {
        return parent;
    }

    public String toString() {
        return "Node: Tile[" + getTile().x + ", " + getTile().y + "]";
    }
}
