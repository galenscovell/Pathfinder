package processing;

import ui.SimulationPanel;
import util.Constants;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Grid {
    private Tile[][] grid;
    private Pathfinder pathfinder;
    private SimulationPanel rootPanel;
    private Tile selectedEnd;

    public Grid(SimulationPanel rootPanel) {
        this.rootPanel = rootPanel;
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

    public Tile getTile(int x, int y) {
        int posY = y / (Constants.TILESIZE + Constants.MARGIN);
        int posX = x / (Constants.TILESIZE + Constants.MARGIN);
        if (!isOutOfBounds(posX, posY)) {
            return grid[posY][posX];
        } else {
            return null;
        }
    }

    public void selectEndTile(Tile tile) {
        if (selectedEnd != null) {
            selectedEnd.becomeFloor();
        }
        selectedEnd = tile;
        selectedEnd.becomeEnd();
    }

    public void clearAll() {
        for (Tile[] row : grid) {
            for (Tile tile : row) {
                tile.becomeFloor();
            }
        }
        selectedEnd = null;
        grid[20][3].becomeStart();
    }

    public void clearScan() {
        for (Tile[] row : grid) {
            for (Tile tile : row) {
                if (tile.isExplored()) {
                    tile.becomeFloor();
                }
            }
        }
    }

    public boolean scanStart(String mode) {
        if (selectedEnd != null) {
            this.pathfinder = new Pathfinder(grid[20][3], selectedEnd, grid, mode);
            return true;
        } else {
            return false;
        }
    }

    public void scanStep() {
        if (pathfinder.step()) {
            rootPanel.stopScan(pathfinder.tracePath());
        }
    }

    private void build() {
        // Construct Tile[Constants.ROWS][Constants.COLUMNS] grid of all floors
        for (int x = 0; x < Constants.COLUMNS; x++) {
            for (int y = 0; y < Constants.ROWS; y++) {
                grid[y][x] = new Tile(x, y);
            }
        }
        grid[20][3].becomeStart();
    }

    private void setTileNeighbors() {
        // Set each tiles neighboring points
        for (Tile[] row : grid) {
            for (Tile tile : row) {
                List<Coordinate> coordinates = new ArrayList<Coordinate>();
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        if (tile.x + dx == tile.x && tile.y + dy == tile.y || isOutOfBounds(tile.x + dx, tile.y + dy)) {
                            continue;
                        }
                        coordinates.add(new Coordinate(tile.x + dx, tile.y + dy));
                    }
                }
                tile.setNeighbors(coordinates);
            }
        }
    }

    private boolean isOutOfBounds(int x, int y) {
        return (x < 0 || y < 0 || x >= Constants.COLUMNS || y >= Constants.ROWS);
    }
}
