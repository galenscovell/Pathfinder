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
        this.x = x;
        this.y = y;
        this.type = TileType.FLOOR;
        this.color = Constants.FLOOR_COLOR;
    }

    public boolean isFloor() {
        return type == TileType.FLOOR;
    }

    public void becomeFloor() {
        if (type != TileType.FLOOR) {
            type = TileType.FLOOR;
            this.color = Constants.FLOOR_COLOR;
        }
    }

    public boolean isWall() {
        return type == TileType.WALL;
    }

    public void becomeWall() {
        if (type != TileType.WALL) {
            type = TileType.WALL;
            this.color = Constants.WALL_COLOR;
        }
    }

    public boolean isStart() {
        return type == TileType.START;
    }

    public void becomeStart() {
        if (type != TileType.START) {
            type = TileType.START;
            this.color = Constants.ENDPOINTS;
        }
    }

    public boolean isEnd() {
        return type == TileType.END;
    }

    public void becomeEnd() {
        if (type != TileType.END) {
            type = TileType.END;
            this.color = Constants.ENDPOINTS;
        }
    }

    public boolean isExplored() {
        return type == TileType.EXPLORE;
    }

    public void becomeExplored() {
        if (type != TileType.EXPLORE) {
            type = TileType.EXPLORE;
            this.color = Constants.EXPLORE_COLOR_1;
        }
    }

    public boolean isPath() {
        return type == TileType.PATH;
    }

    public void becomePath() {
        if (type != TileType.PATH) {
            type = TileType.PATH;
            this.color = Constants.PATH_COLOR;
        }
    }

    public void setNeighbors(List<Point> points) {
        this.neighborTilePoints = points;
    }

    public List<Point> getNeighbors() {
        return neighborTilePoints;
    }

    public void draw(Graphics2D gfx) {
        gfx.setColor(color);
        gfx.fillRect(
                (Constants.TILESIZE + Constants.MARGIN) * x + Constants.MARGIN,
                (Constants.TILESIZE + Constants.MARGIN) * y + Constants.MARGIN,
                Constants.TILESIZE,
                Constants.TILESIZE
        );
    }
}
