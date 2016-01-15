package processing;

import util.Constants;

import java.awt.*;
import java.util.List;

public class Tile {
    public final int x, y;
    private Color color;
    private TileType type;
    private List<Coordinate> neighborTileCoordinates;
    private int frame;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.type = TileType.FLOOR;
        this.color = Constants.FLOOR_COLOR;
        this.frame = 0;
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
        frame = 60;
        if (type != TileType.EXPLORE) {
            type = TileType.EXPLORE;
            this.color = Constants.EXPLORE_COLOR_0;
        }
    }

    public void setNeighbors(List<Coordinate> coordinates) {
        this.neighborTileCoordinates = coordinates;
    }

    public List<Coordinate> getNeighbors() {
        return neighborTileCoordinates;
    }

    public void draw(Graphics2D gfx) {
        if (isExplored() && frame > 0) {
            frame--;
            if (frame == 50) {
                color = Constants.EXPLORE_COLOR_1;
            } else if (frame == 40) {
                color = Constants.EXPLORE_COLOR_2;
            } else if (frame == 30) {
                color = Constants.EXPLORE_COLOR_3;
            } else if (frame == 20) {
                color = Constants.EXPLORE_COLOR_4;
            } else if (frame == 10) {
                color = Constants.EXPLORE_COLOR_5;
            }
        }
        gfx.setColor(color);
        gfx.fillRect(
            (Constants.TILESIZE + Constants.MARGIN) * x + Constants.MARGIN,
            (Constants.TILESIZE + Constants.MARGIN) * y + Constants.MARGIN,
            Constants.TILESIZE,
            Constants.TILESIZE
        );
    }
}
