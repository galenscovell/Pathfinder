package processing;

import util.Constants;

import java.util.Map;

public class Repository {
    private final Map<Integer, Tile> tiles;

    public Repository(Map<Integer, Tile> tiles) {
        this.tiles = tiles;
    }

    public Map<Integer, Tile> getTiles() {
        return tiles;
    }

    public Tile findTile(int x, int y) {
        return tiles.get(x * Constants.COLUMNS + y);
    }
}
