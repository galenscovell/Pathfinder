package processing;

import java.util.*;

public class Pathfinder {
    private final Tile[][] grid;
    private List<Node> openList, closedList;
    private Node startNode, endNode;
    private final String mode;

    public Pathfinder(Tile startTile, Tile endTile, Tile[][] grid, String mode) {
        this.grid = grid;
        this.mode = mode;
        this.openList = new ArrayList<Node>();
        this.closedList = new ArrayList<Node>();
        this.startNode = new Node(startTile);
        startNode.setCost(0);
        this.endNode = new Node(endTile);
        openList.add(startNode);
    }

    public boolean step() {
        if (!openList.isEmpty()) {
            Node current = getBestNode();

            if (current.getTile() == endNode.getTile()) {
                endNode = current;
                return true;
            }

            openList.remove(current);
            closedList.add(current);

            for (Coordinate coordinate : current.getTile().getNeighbors()) {
                Tile neighborTile = grid[coordinate.y][coordinate.x];

                if (neighborTile != null && !neighborTile.isWall()) {
                    Node neighborNode = new Node(neighborTile);

                    if (inList(neighborTile, openList) || inList(neighborTile, closedList)) {
                        continue;
                    } else {
                        if (!neighborTile.isEnd()) {
                            grid[neighborTile.y][neighborTile.x].becomeExplored();
                        }
                        openList.add(neighborNode);
                    }
                    // If this is a new path or shorter than current, keep it
                    if (current.getCost() + 1 < neighborNode.getCost()) {
                        neighborNode.setParent(current);
                        neighborNode.setCost(current.getCost() + 1);
                    }
                }
            }
        }
        return false;
    }

    private boolean inList(Tile nodeTile, List<Node> nodeList) {
        for (Node node : nodeList) {
            if (node.getTile() == nodeTile) {
                return true;
            }
        }
        return false;
    }

    private Node getBestNode() {
        double minCost = Double.POSITIVE_INFINITY;
        Node bestNode = null;

        for (Node node : openList) {
            double costFromStart = node.getCost();
            double costToEnd = estimateDistance(node, endNode);
            double totalCoat = costFromStart + costToEnd;
            if (minCost > totalCoat) {
                minCost = totalCoat;
                bestNode = node;
            }
        }
        return bestNode;
    }

    private double chebyshev(Node start, Node end) {
        return Math.max(Math.abs(start.getTile().x - end.getTile().x), Math.abs(start.getTile().y - end.getTile().y));
    }

    private double manhattan(Node start, Node end) {
        return Math.abs(start.getTile().x - end.getTile().x) + Math.abs(start.getTile().y - end.getTile().y);
    }

    private double euclidean(Node start, Node end) {
        double xs = (start.getTile().x - end.getTile().x) * (start.getTile().x - end.getTile().x);
        double ys = (start.getTile().y - end.getTile().y) * (start.getTile().y - end.getTile().y);
        return Math.sqrt(xs + ys);
    }

    private double estimateDistance(Node start, Node end) {
        if (mode == "manhattan") {
            return manhattan(start, end);
        } else if (mode == "euclidean") {
            return euclidean(start, end);
        } else {
            return chebyshev(start, end);
        }
    }

    public Stack<Coordinate> tracePath() {
        // Returns ordered stack of points along movement path
        Stack<Coordinate> path = new Stack<Coordinate>();
        // Chase parent of node until start point reached
        Node node = endNode;
        while (node.getParent() != null) {
            if (!grid[node.getTile().y][node.getTile().x].isEnd()) {
                path.push(new Coordinate(node.getTile().x, node.getTile().y));
            }
            node = node.getParent();
        }
        return path;
    }
}

