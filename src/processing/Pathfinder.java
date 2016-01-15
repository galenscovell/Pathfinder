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
        this.endNode = new Node(endTile);

        startNode.setCostFromStart(0);
        startNode.setTotalCost(startNode.getCostFromStart() + heuristic(startNode, endNode));
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

                if (mode != "euclidean") {
                    Tile currentTile = current.getTile();
                    int diffX = Math.abs(currentTile.x - neighborTile.x);
                    int diffY = Math.abs(currentTile.y - neighborTile.y);

                    if (diffX > 0 && diffY > 0) {
                        continue;
                    }
                }

                if (neighborTile != null && !neighborTile.isWall()) {
                    Node neighborNode = new Node(neighborTile);

                    if (!inList(neighborTile, closedList)) {
                        neighborNode.setTotalCost(current.getCostFromStart() + heuristic(neighborNode, endNode));

                        if (!inList(neighborTile, openList)) {
                            if (!neighborTile.isEnd()) {
                                grid[neighborTile.y][neighborTile.x].becomeExplored();
                            }
                            neighborNode.setParent(current);
                            openList.add(neighborNode);
                        } else {
                            if (neighborNode.getCostFromStart() < current.getCostFromStart()) {
                                neighborNode.setCostFromStart(neighborNode.getCostFromStart());
                                neighborNode.setParent(neighborNode.getParent());
                            }
                        }
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
            double totalCost = node.getCostFromStart() + heuristic(node, endNode);
            if (minCost > totalCost) {
                minCost = totalCost;
                bestNode = node;
            }
        }
        return bestNode;
    }

    private double heuristic(Node start, Node end) {
        double dx = start.getTile().x - end.getTile().x;
        double dy = start.getTile().y - end.getTile().y;

        if (mode == "manhattan") {
            return manhattan(dx, dy);
        } else if (mode == "euclidean") {
            return euclidean(dx, dy);
        } else {
            return chebyshev(dx, dy);
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


    public double chebyshev(double dx, double dy) {
        return Math.max(Math.abs(dx), Math.abs(dy));
    }

    public double manhattan(double dx, double dy) {
        return Math.abs(dx) + Math.abs(dy);
    }

    public double euclidean(double dx, double dy) {
        return Math.sqrt(dx * dx + dy * dy);
    }
}

