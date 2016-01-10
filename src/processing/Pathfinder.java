package processing;

import java.util.*;

public class Pathfinder {

    private static class Node {
        Node parent;
        Tile self;
        double cost;

        public Node(Tile s) {
            this.self = s;
            this.cost = Double.POSITIVE_INFINITY;
        }
    }

    public Stack<Point> findPath(Tile start, Tile end, Repository repo) {
        List<Node> open = new ArrayList<Node>();
        List<Tile> closed = new ArrayList<Tile>();
        Node startNode = new Node(start);
        startNode.cost = 0;
        Node endNode = new Node(end);
        open.add(startNode);

        while (!open.isEmpty()) {
            // Consider node with best score in open list
            Node a = chooseNode(open, endNode);
            // If node Cell is end Cell, trace path and finish
            if (a.self == end) {
                return tracePath(a);
            } else {
                // Don't repeat ourselves
                open.remove(a);
                closed.add(a.self);

                // Consider current node's neighbors
                for (Point point : a.self.getNeighbors()) {
                    Tile neighbor = repo.findTile(point.x, point.y);
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
        return null;
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

    private double estimateDistance(Node n, Node end) {
        // Euclidean Manhattan distance between nodes
        double xs = (n.self.x - end.self.x) * (n.self.x - end.self.x);
        double ys = (n.self.y - end.self.y) * (n.self.y - end.self.y);
        return Math.sqrt(xs + ys);
    }

    private Stack<Point> tracePath(Node n) {
        // Returns ordered stack of points along movement path
        Stack<Point> path = new Stack<Point>();
        // Chase parent of node until start point reached
        while (n.parent != null) {
            path.push(new Point(n.self.x, n.self.y));
            n = n.parent;
        }
        return path;
    }
}

