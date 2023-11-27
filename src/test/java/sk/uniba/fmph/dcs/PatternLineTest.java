package sk.uniba.fmph.dcs;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PatternLineTest {

    @Test
    public void testPatternLineState() {
        ArrayList<Tile> tileTypes = new ArrayList<>();
        tileTypes.add(Tile.BLUE);
        tileTypes.add(Tile.GREEN);
        tileTypes.add(Tile.RED);
        tileTypes.add(Tile.YELLOW);
        tileTypes.add(Tile.BLACK);
        ArrayList<Points> pointPattern = new ArrayList<Points>();
        pointPattern.add(new Points(1));
        pointPattern.add(new Points(2));
        pointPattern.add(new Points(2));

        WallLine wallLine = new WallLine(tileTypes, null, null);
        Floor floor = new Floor(UsedTyles.getInstance(), pointPattern);
        PatternLine patternLine = new PatternLine(1, wallLine, floor);
        assertEquals("Empty PatternLine", "-", patternLine.state());
        patternLine.put(Collections.singleton(Tile.BLACK));
        assertEquals("PatternLine with one B", "L", patternLine.state());
    }

    @Test
    public void testPatternLinePut(){
        ArrayList<Tile> tileTypes = new ArrayList<>();
        tileTypes.add(Tile.BLUE);
        tileTypes.add(Tile.GREEN);
        tileTypes.add(Tile.RED);
        tileTypes.add(Tile.YELLOW);
        tileTypes.add(Tile.BLACK);
        ArrayList<Points> pointPattern = new ArrayList<Points>();
        pointPattern.add(new Points(1));
        pointPattern.add(new Points(2));
        pointPattern.add(new Points(2));

        WallLine wallLine = new WallLine(tileTypes, null, null);
        Floor floor = new Floor(UsedTyles.getInstance(), pointPattern);
        PatternLine patternLine = new PatternLine(2, wallLine, floor);
        patternLine.put(Collections.singleton(Tile.BLACK));
        assertEquals("PatternLine with one B", "L-", patternLine.state());

        List<Tile> tiles = new ArrayList<>();
        tiles.add(Tile.YELLOW);
        tiles.add(Tile.YELLOW);

        patternLine.put(tiles);
        assertEquals("PatternLine has not changed", "L-", patternLine.state());
        assertEquals("Tiles were dumped to floor", "II", floor.state());

        tiles = new ArrayList<>();
        tiles.add(Tile.BLACK);
        tiles.add(Tile.BLACK);

        patternLine.put(tiles);
        assertEquals("PatternLine has changed", "LL", patternLine.state());
        assertEquals("Rest was dumped to floor", "IIL", floor.state());
    }

    @Test
    public void testPatternLineFinnishRound() {
        ArrayList<Tile> tileTypes = new ArrayList<>();
        tileTypes.add(Tile.BLUE);
        tileTypes.add(Tile.GREEN);
        tileTypes.add(Tile.RED);
        tileTypes.add(Tile.YELLOW);
        tileTypes.add(Tile.BLACK);
        ArrayList<Points> pointPattern = new ArrayList<Points>();
        pointPattern.add(new Points(1));
        pointPattern.add(new Points(2));
        pointPattern.add(new Points(2));

        //patternline that can put to wall

        WallLine wallLine = new WallLine(tileTypes, null, null);
        Floor floor = new Floor(UsedTyles.getInstance(), pointPattern);
        PatternLine patternLine = new PatternLine(2, wallLine, floor);

        List<Tile> tiles = new ArrayList<>();
        tiles.add(Tile.YELLOW);
        tiles.add(Tile.YELLOW);
        patternLine.put(tiles);
        Points pp = patternLine.finishRound();
        assertNotEquals("Points are not zero", new Points(0), pp);
        assertEquals("UsedTyles containsa the other yellow tile", "I", UsedTyles.getInstance().state());

        //patterline that cannot put to wall

        wallLine = new WallLine(tileTypes, null, null);
        floor = new Floor(UsedTyles.getInstance(), pointPattern);
        patternLine = new PatternLine(2, wallLine, floor);

        wallLine.putTile(Tile.YELLOW);
        patternLine.put(Collections.singleton(Tile.YELLOW));
        pp = patternLine.finishRound();
        assertEquals("Because the patternline was not full", new Points(0), pp);
        assertEquals("UsedTyles did not change", "I", UsedTyles.getInstance().state());
        patternLine.put(Collections.singleton(Tile.YELLOW));
        pp = patternLine.finishRound();
        assertEquals("Points are zero", new Points(0), pp);
        assertEquals("UsedTyles still did not change", "I", UsedTyles.getInstance().state());
    }
}
