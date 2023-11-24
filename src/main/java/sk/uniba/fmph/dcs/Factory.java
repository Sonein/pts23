package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;

public class Factory implements TyleSource{

    private List<Tile> tiles;
    private final TableCenter tableCenter;
    private final Bag bag;

    public Factory () {
        this.tiles = new ArrayList<>();
        this.tableCenter = TableCenter.getInstance();
        this.bag = Bag.getInstance();
        startNewRound();
    }

    @Override
    public Tile[] take(int idx) {
        List<Tile> toReturn = new ArrayList<>();
        if (idx >= 4 || this.tiles.isEmpty() || idx < 0) {
            return null;
        }
        Tile toTake = this.tiles.get(idx);
        for (Tile t:
             this.tiles) {
            if(t.equals(toTake)){
                toReturn.add(t);
            }
        }
        while (this.tiles.remove(toTake));
        this.tableCenter.add(this.tiles.toArray(Tile[]::new));
        this.tiles.clear();
        return toReturn.toArray(Tile[]::new);
    }

    @Override
    public boolean isEmpty() {
        return this.tiles.isEmpty();
    }

    @Override
    public void startNewRound() {
        this.tiles = new ArrayList<>(this.bag.take(4));
    }

    @Override
    public String state() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Tile t:
             this.tiles) {
            stringBuilder.append(t.toString());
        }
        return stringBuilder.toString();
    }
}
