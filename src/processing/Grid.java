package processing;

import util.Constants;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Grid {
    private Tile[][] grid;

    public Grid() {
        this.grid = new Tile[Constants.ROWS][Constants.COLUMNS];
        build();
        setTileNeighbors();
    }

    public void draw(Graphics2D gfx) {
        for (Tile[] row : grid) {
            for (Tile tile : row) {
                tile.draw(gfx);
            }
        }
    }

    private void build() {
        // Construct Tile[Constants.ROWS][Constants.COLUMNS] grid of all floors
        for (int x = 0; x < Constants.COLUMNS; x++) {
            for (int y = 0; y < Constants.ROWS; y++) {
                grid[y][x] = new Tile(x, y);
            }
        }
    }

    private void setTileNeighbors() {
        // Set each tiles neighboring points
        for (Tile[] row : grid) {
            for (Tile tile : row) {
                List<Point> points = new ArrayList<Point>();
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        if (tile.x + dx == tile.x && tile.y + dy == tile.y || isOutOfBounds(tile.x + dx, tile.y + dy)) {
                            continue;
                        }
                        points.add(new Point(tile.x + dx, tile.y + dy));
                    }
                }
                tile.setNeighbors(points);
            }
        }
    }

    private boolean isOutOfBounds(int x, int y) {
        return (x < 0 || y < 0 || x >= Constants.COLUMNS || y >= Constants.ROWS);
    }

    public Map<Integer, Tile> getTiles() {
        // Translate Tile[][] grid to HashMap
        Map<Integer, Tile> tiles = new HashMap<Integer, Tile>();
        for (int x = 0; x < Constants.COLUMNS; x++) {
            for (int y = 0; y < Constants.ROWS; y++) {
                int key = x * Constants.COLUMNS + y;
                tiles.put(key, grid[y][x]);
            }
        }
        return tiles;
    }

}
