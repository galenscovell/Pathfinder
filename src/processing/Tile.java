package processing;

import util.Constants;

import java.awt.*;
import java.util.List;

public class Tile {
    public final int x, y;
    private Color color;
    private TileType type;
    private List<Point> neighborTilePoints;

    public Tile(int x, int y) {
        this.x = (Constants.TILESIZE + Constants.MARGIN) * x + Constants.MARGIN;
        this.y = (Constants.TILESIZE + Constants.MARGIN) * y + Constants.MARGIN;
        this.type = TileType.FLOOR;
        this.color = Constants.FLOOR_COLOR;
    }

    public boolean isFloor() {
        return type == TileType.FLOOR;
    }

    public void becomeFloor() {
        type = TileType.FLOOR;
        this.color = Constants.FLOOR_COLOR;
    }

    public boolean isWall() {
        return type == TileType.WALL;
    }

    public void becomeWall() {
        type = TileType.WALL;
        this.color = Constants.WALL_COLOR;
    }

    public void setNeighbors(List<Point> points) {
        this.neighborTilePoints = points;
    }

    public List<Point> getNeighbors() {
        return neighborTilePoints;
    }

    public void draw(Graphics2D gfx) {
        gfx.setColor(color);
        gfx.fillRect(x, y, Constants.TILESIZE, Constants.TILESIZE);
    }
}
