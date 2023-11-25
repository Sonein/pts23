package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bag {

    private List<Tile> tiles;

    private final UsedTyles usedTyles;

    private Bag() {
        this.tiles = new ArrayList<>();
        fill(true);
        this.usedTyles = UsedTyles.getInstance();
    }

    private static class BagHolder {
        private static final Bag INSTANCE = new Bag();
    }

    private void fill(boolean start){
        if(start) {
            for (int i = 0; i < 20; i++) {
                this.tiles.add(Tile.BLACK);
            }
            for (int i = 0; i < 20; i++) {
                this.tiles.add(Tile.GREEN);
            }
            for (int i = 0; i < 20; i++) {
                this.tiles.add(Tile.YELLOW);
            }
            for (int i = 0; i < 20; i++) {
                this.tiles.add(Tile.RED);
            }
            for (int i = 0; i < 20; i++) {
                this.tiles.add(Tile.BLUE);
            }
            Collections.shuffle(this.tiles);
        } else {
            List <Tile> temp = this.usedTyles.takeAll();
            Collections.shuffle(temp);
            this.tiles = new ArrayList<>(temp);
        }
    }

    public static Bag getInstance() {
        return Bag.BagHolder.INSTANCE;
    }

    public List<Tile> take(int count){
        List<Tile> toReturn = new ArrayList<>();
        for(int i = 0; i < count; i++){
            if(this.tiles.isEmpty()){
                fill(false);
            }
            toReturn.add(this.tiles.remove(0));
        }
        return toReturn;
    }

    public String state(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Tile t:
                this.tiles) {
            stringBuilder.append(t.toString());
        }
        stringBuilder.append("\nUsedTyles:\n");
        stringBuilder.append(this.usedTyles.state());
        return stringBuilder.toString();
    }
}
