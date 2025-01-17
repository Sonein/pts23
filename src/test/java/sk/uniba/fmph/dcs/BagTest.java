package sk.uniba.fmph.dcs;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BagTest {

    @Test
    public void testShuffledTilesOnStart(){
        Bag bag = Bag.getInstance();
        assertEquals("Inside equals to 100:", 100 + "\nUsedTyles:\n".length(), bag.state().length());
        System.out.println("Should be shuffled:" + bag.state());
    }

    @Test
    public void testTakeNoRefill(){
        Bag bag = Bag.getInstance();
        bag.take(4);
        assertEquals("Inside equals to 96:", 100 + "\nUsedTyles:\n".length() - 4, bag.state().length());
    }

    @Test
    public void testTakeNeedRefill(){
        Bag bag = Bag.getInstance();
        List<Tile> tiles = new ArrayList<>();
        tiles.add(Tile.BLACK);
        tiles.add(Tile.BLACK);
        tiles.add(Tile.BLACK);
        tiles.add(Tile.BLACK);
        tiles.add(Tile.BLACK);
        UsedTyles.getInstance().give(tiles);
        bag.take(104);
        assertEquals("Inside equals to 1:", 1 + "\nUsedTyles:\n".length(), bag.state().length());
        assertTrue("Has newly added tile:", bag.take(1).contains(Tile.BLACK));
    }
}
