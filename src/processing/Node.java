package processing;

public class Node {
    private Node parent;
    private Tile tile;
    private double costFromStart, totalCost;

    public Node(Tile tile) {
        this.tile = tile;
    }


    public void setCostFromStart(double val) {
        this.costFromStart = val;
    }

    public void setTotalCost(double val) {
        this.totalCost = val;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }


    public double getCostFromStart() {
        return costFromStart;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public Tile getTile() {
        return tile;
    }

    public Node getParent() {
        return parent;
    }

    public String toString() {
        return "Node: Tile[" + getTile().x + ", " + getTile().y + "]";
    }
}
