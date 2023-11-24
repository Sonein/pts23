package sk.uniba.fmph.dcs;

import java.util.*;

public class PatternLine {

    private int lastIndex = 0;
    private final int capacity;
    private Tile[] tiles;
    private final UsedTilesGiveInterface usedTyles;
    private final WallLine wallLine;
    private final Floor floor;
    

    public PatternLine(int capacity, final WallLine wallLine, final Floor floor) {
        this.capacity = capacity;
        this.tiles = new Tile[capacity];
        this.usedTyles = UsedTyles.getInstance();
        this.wallLine = wallLine;
        this.floor = floor;
    }

    public void put(Collection<Tile> tiles){
        for (Tile tile:
             tiles) {
            if(tile.equals(Tile.STARTING_PLAYER)){
                this.floor.put(Collections.singleton(tile));
            } else if (this.lastIndex < this.capacity) {
                if (this.tiles[0] == null || this.tiles[0].equals(tile)) {
                    this.tiles[this.lastIndex] = tile;
                    this.lastIndex++;
                } else {
                    this.floor.put(Collections.singleton(tile));
                }
            } else {
                this.floor.put(Collections.singleton(tile));
            }
        }
    }

    public Points finishRound(){
        if(this.lastIndex == this.capacity && this.wallLine.canPutTile(this.tiles[0])){
            List<Tile> temp = new ArrayList<>(Arrays.asList(this.tiles).subList(1, this.lastIndex));
            this.usedTyles.give(temp);
            Tile tileToGive = this.tiles[0];
            this.tiles = new Tile[this.capacity];
            return this.wallLine.putTile(tileToGive);
        } else {
            return new Points(0);
        }
    }

    public String state(){
        StringBuilder toReturn = new StringBuilder();
        for (final Tile tile : this.tiles) {
            toReturn.append(tile == null ? "-" : tile.toString());
        }
        return toReturn.toString();
    }
}
