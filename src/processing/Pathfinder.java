package processing;

import java.util.*;

public class Pathfinder {
    private Tile[][] grid;
    private Tile start, end;
    private List<Node> open;
    private List<Tile> closed;
    private Node startNode, endNode;
    private Node finalNode;
    private final String mode;

    public Pathfinder(Tile start, Tile end, Tile[][] grid, String mode) {
        this.start = start;
        this.end = end;
        this.grid = grid;
        this.mode = mode;

        this.open = new ArrayList<Node>();
        this.closed = new ArrayList<Tile>();
        this.startNode = new Node(start);
        startNode.cost = 0;
        this.endNode = new Node(end);
        open.add(startNode);
    }

    private static class Node {
        Node parent;
        Tile self;
        double cost;

        public Node(Tile tile) {
            this.self = tile;
            this.cost = Double.POSITIVE_INFINITY;
        }
    }

    public boolean step() {
        if (!open.isEmpty()) {
            // Consider node with best score in open list
            Node a = chooseNode(open, endNode);
            // If node Cell is end Cell, trace path and finish
            if (a.self == end) {
                this.finalNode = a;
                return true;
            } else {
                // Don't repeat ourselves
                open.remove(a);
                closed.add(a.self);

                // Consider current node's neighbors
                for (Coordinate coordinate : a.self.getNeighbors()) {
                    Tile neighbor = grid[coordinate.y][coordinate.x];
                    // Ignore walls, water and other blocked tiles
                    if (neighbor == null || neighbor.isWall() || closed.contains(neighbor)) {
                        continue;
                    }
                    // If Cell is already in open list, ignore it
                    boolean inOpen = false;
                    for (Node n : open) {
                        if (n.self == neighbor) {
                            inOpen = true;
                        }
                    }
                    Node adjacent = new Node(neighbor);
                    // Otherwise add it as new unexplored node
                    if (!inOpen) {
                        if (!neighbor.isEnd()) {
                            grid[neighbor.y][neighbor.x].becomeExplored();
                        }
                        open.add(adjacent);
                    }
                    // If this is a new path or shorter than current, keep it
                    if (a.cost + 1 < adjacent.cost) {
                        adjacent.parent = a;
                        adjacent.cost = a.cost + 1;
                    }
                }
            }
        }
        return false;
    }

    private Node chooseNode(List<Node> open, Node end) {
        double minCost = Double.POSITIVE_INFINITY;
        Node bestNode = null;

        for (Node n : open) {
            double costFromStart = n.cost;
            double costToEnd = estimateDistance(n, end);
            double totalCost = costFromStart + costToEnd;
            if (minCost > totalCost) {
                minCost = totalCost;
                bestNode = n;
            }
        }
        return bestNode;
    }

    private double chebyshev(Node n, Node end) {
        return Math.max(Math.abs(n.self.x - end.self.x), Math.abs(n.self.y - end.self.y));
    }

    private double manhattan(Node n, Node end) {
        return Math.abs(n.self.x - end.self.x) + Math.abs(n.self.y - end.self.y);
    }

    private double euclidean(Node n, Node end) {
        double xs = (n.self.x - end.self.x) * (n.self.x - end.self.x);
        double ys = (n.self.y - end.self.y) * (n.self.y - end.self.y);
        return Math.sqrt(xs + ys);
    }

    private double estimateDistance(Node n, Node end) {
        if (mode == "manhattan") {
            return manhattan(n, end);
        } else if (mode == "euclidean") {
            return euclidean(n, end);
        } else {
            return chebyshev(n, end);
        }
    }

    public Stack<Coordinate> tracePath() {
        // Returns ordered stack of points along movement path
        Stack<Coordinate> path = new Stack<Coordinate>();
        // Chase parent of node until start point reached
        Node n = finalNode;
        while (n.parent != null) {
            if (!grid[n.self.y][n.self.x].isEnd()) {
                path.push(new Coordinate(n.self.x, n.self.y));
            }
            n = n.parent;
        }
        return path;
    }
}

