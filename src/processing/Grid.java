package processing;

import util.Constants;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Grid {
    private Tile[][] grid;
    private Color closed = new Color(0x2c3e50);
    private Color open = new Color(0x34495e);
    private Color filled = new Color(0x2980b9);

    public Grid() {
        this.grid = new Tile[Constants.TILES_Y][Constants.TILES_X];
        build();
        setTileNeighbors();
    }

    public void draw(Graphics2D gfx) {
        for (Tile[] row : grid) {
            for (Tile tile : row) {
                tile.draw(gfx, filled);
            }
        }
    }

    private void build() {
        // Construct Tile[MAPSIZE][MAPSIZE] grid of all walls
        for (int x = 0; x < Constants.TILES_X; x++) {
            for (int y = 0; y < Constants.TILES_Y; y++) {
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
        return (x < 0 || y < 0 || x >= Constants.TILES_X || y >= Constants.TILES_Y);
    }

    public Map<Integer, Tile> getTiles() {
        // Translate Tile[][] grid to HashMap
        Map<Integer, Tile> tiles = new HashMap<Integer, Tile>();
        for (int x = 0; x < Constants.TILES_X; x++) {
            for (int y = 0; y < Constants.TILES_Y; y++) {
                int key = x * Constants.TILES_X + y;
                tiles.put(key, grid[y][x]);
            }
        }
        return tiles;
    }

}
