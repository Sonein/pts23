package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

public class WallLine {
    private Tile[] tiles;
    private final ArrayList<Tile> tileTypes;
    private final Map<Tile, Integer> idxOf = new HashMap<>();
    private final Integer N;
    private final WallLine lineDown, lineUp;

    public WallLine(ArrayList<Tile> tileTypes, WallLine lineDown, WallLine lineUp) {
        this.lineDown = lineDown;
        this.lineUp = lineUp;
        this.tileTypes = tileTypes;
        this.N = this.tileTypes.size();
        this.tiles = new Tile[this.N];
        for (int idx = 0; idx < this.N; idx++) {
            idxOf.put(this.tileTypes.get(idx), idx);
        }
    }

    Boolean canPutTile(Tile tile) {
        // zly tip, asi nenastatne, ale radsej skontrolujme
        if (!this.idxOf.containsKey(tile))
            return false;
        
        return this.tiles[this.idxOf.get(tile)] == null;
    }

    List<Optional<Tile>> getTiles() {
        ArrayList<Optional<Tile>> resultTiles = new ArrayList<>();

        for (int idx = 0; idx < this.N; idx++)
            resultTiles.add(Optional.ofNullable(this.tiles[idx]));

        return resultTiles;
    }

    Points putTile(Tile tile) {
        // taky tile nie je na stene
        if (!this.idxOf.containsKey(tile))
            return new Points(0);
        
        // ak uz je v line tile toho typu, tak tiez nic
        Integer idx = this.idxOf.get(tile);
        if (this.tiles[idx] != null)
            return new Points(0);

        this.tiles[idx] = tile;

        // horizontal
        Integer lIdx = idx, rIdx = idx;
        while (lIdx - 1 >= 0 && this.tiles[lIdx - 1] != null)
            lIdx--;
        while (rIdx + 1 < this.N && this.tiles[rIdx + 1] != null)
            rIdx++;
        int horizontalPoints = rIdx - lIdx + 1;

        // vertical
        WallLine curLine = this;
        lIdx = rIdx = 100;
        while (curLine.lineUp != null) {
            List<Optional<Tile>> upTiles = curLine.lineUp.getTiles();
            if (upTiles.size() <= idx || upTiles.get(idx).isEmpty())
                break;
            
            rIdx++;
            curLine = curLine.lineUp;
        }

        curLine = this;
        while (curLine.lineDown != null) {
            List<Optional<Tile>> downTiles = curLine.lineDown.getTiles();
            if (downTiles.size() <= idx || downTiles.get(idx).isEmpty())
                break;
            
            lIdx--;
            curLine = curLine.lineDown;
        }
        int verticalPoints = rIdx - lIdx + 1;

        //int points = horizontalPoints == 1 && verticalPoints == 1 ? 1 : horizontalPoints + verticalPoints;
        int points = horizontalPoints == 1 && verticalPoints == 1 ? 1 : (horizontalPoints == 1 ? 0 : horizontalPoints) + (verticalPoints == 1 ? 0 : horizontalPoints);

        return new Points(points);
    }

    public String state() {
        String toReturn = "";
        for (final Tile tile : this.tiles) {
            toReturn += tile == null ? "-" : tile.toString();
        }
        return toReturn;
      }
}
