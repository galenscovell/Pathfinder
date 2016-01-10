package processing;

import util.Constants;

import java.awt.*;
import java.util.List;

public class Tile {
    public final int x, y;
    private TileType type;
    private List<Point> neighborTilePoints;

    public Tile(int x, int y) {
        this.x = x * (Constants.TILESIZE + Constants.MARGIN);
        this.y = y * (Constants.TILESIZE + Constants.MARGIN);
        this.type = TileType.EMPTY;
    }

    public boolean isEmpty() {
        return type == TileType.EMPTY;
    }

    public void becomeEmpty() {
        type = TileType.EMPTY;
    }

    public boolean isFloor() {
        return type == TileType.FLOOR;
    }

    public void becomeFloor() {
        type = TileType.FLOOR;
    }

    public boolean isWall() {
        return type == TileType.WALL;
    }

    public void becomeWall() {
        type = TileType.WALL;
    }

    public void setNeighbors(List<Point> points) {
        this.neighborTilePoints = points;
    }

    public List<Point> getNeighbors() {
        return neighborTilePoints;
    }

    public void draw(Graphics2D gfx, Color color) {
        gfx.setColor(color);
        gfx.fillRect(x, y, Constants.TILESIZE, Constants.TILESIZE);
    }
}
