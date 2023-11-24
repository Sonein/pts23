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
        assertEquals("Empty PatternLine", patternLine.state(), "-");
        patternLine.put(Collections.singleton(Tile.BLACK));
        assertEquals("PatternLine with one B", patternLine.state(), "L");
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
        assertEquals("PatternLine with one B", patternLine.state(), "L-");

        List<Tile> tiles = new ArrayList<>();
        tiles.add(Tile.YELLOW);
        tiles.add(Tile.YELLOW);

        patternLine.put(tiles);
        assertEquals("PatternLine has not changed", patternLine.state(), "L-");
        assertEquals("Tiles were dumped to floor", floor.state(), "II");

        tiles = new ArrayList<>();
        tiles.add(Tile.BLACK);
        tiles.add(Tile.BLACK);

        patternLine.put(tiles);
        assertEquals("PatternLine has changed", patternLine.state(), "LL");
        assertEquals("Rest was dumped to floor", floor.state(), "IIL");
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
        assertNotEquals("Points are not zero", pp, new Points(0));
        assertEquals("UsedTyles containsa the other yellow tile", UsedTyles.getInstance().state(), "I");

        //patterline that cannot put to wall

        wallLine = new WallLine(tileTypes, null, null);
        floor = new Floor(UsedTyles.getInstance(), pointPattern);
        patternLine = new PatternLine(2, wallLine, floor);

        wallLine.putTile(Tile.YELLOW);
        patternLine.put(Collections.singleton(Tile.YELLOW));
        pp = patternLine.finishRound();
        assertEquals("Because the patternline was not full", pp, new Points(0));
        assertEquals("UsedTyles did not change", UsedTyles.getInstance().state(), "I");
        patternLine.put(Collections.singleton(Tile.YELLOW));
        pp = patternLine.finishRound();
        assertEquals("Points are zero", pp, new Points(0));
        assertEquals("UsedTyles still did not change", UsedTyles.getInstance().state(), "I");
    }
}
