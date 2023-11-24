package sk.uniba.fmph.dcs;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameTest {

    @Test
    public void testExitCode0() {
        ArrayList<Tile> tileTypes = new ArrayList<>();
        tileTypes.add(Tile.BLUE);
        tileTypes.add(Tile.GREEN);
        tileTypes.add(Tile.RED);
        tileTypes.add(Tile.YELLOW);
        tileTypes.add(Tile.BLACK);
        var pointPattern = new ArrayList<Points>();
        pointPattern.add(new Points(1));
        pointPattern.add(new Points(2));
        pointPattern.add(new Points(2));
        Factory factory = new Factory();

        WallLine wallLine = new WallLine(tileTypes, null, null);
        Floor floor = new Floor(UsedTyles.getInstance(), pointPattern);
        PatternLine patternLine = new PatternLine(2, wallLine, floor);

        Board board = new Board(new ArrayList<>(Collections.singleton(patternLine)), new ArrayList<>(Collections.singleton(wallLine)), floor, new Points(0));
        TableArea tableArea = new TableArea(TableCenter.getInstance(), new ArrayList<>(Collections.singleton(factory)));
        Game game = new Game(Bag.getInstance(), tableArea, new ArrayList<>(Collections.singleton(board)), GameObserver.getInstance());

        assertTrue(game.take(0, 0, 0, 0));
        assertEquals("Game should accept without change to exitcode", game.getExitCode(), 0);

        assertTrue(game.take(0, 1, 0, 0));
        assertEquals("Game should accept without change to exitcode", game.getExitCode(), 0);
    }

    @Test
    public void testExitCode1(){
        ArrayList<Tile> tileTypes = new ArrayList<>();
        tileTypes.add(Tile.BLUE);
        tileTypes.add(Tile.GREEN);
        tileTypes.add(Tile.RED);
        tileTypes.add(Tile.YELLOW);
        tileTypes.add(Tile.BLACK);
        var pointPattern = new ArrayList<Points>();
        pointPattern.add(new Points(1));
        pointPattern.add(new Points(2));
        pointPattern.add(new Points(2));
        Factory factory = new Factory();

        WallLine wallLine = new WallLine(tileTypes, null, null);
        Floor floor = new Floor(UsedTyles.getInstance(), pointPattern);
        PatternLine patternLine = new PatternLine(2, wallLine, floor);

        Board board = new Board(new ArrayList<>(Collections.singleton(patternLine)), new ArrayList<>(Collections.singleton(wallLine)), floor, new Points(0));
        TableArea tableArea = new TableArea(TableCenter.getInstance(), new ArrayList<>(Collections.singleton(factory)));
        Game game = new Game(Bag.getInstance(), tableArea, new ArrayList<>(Collections.singleton(board)), GameObserver.getInstance());

        assertFalse(game.take(1, 0, 0, 0));
        assertEquals("Nonexistent player", game.getExitCode(), 1);

        assertFalse(game.take(0, 2, 0, 0));
        assertEquals("Nonexistent TyleSource", game.getExitCode(), 1);

        assertFalse(game.take(0, 1, 5, 0));
        assertEquals("Nonexistent element in Factory", game.getExitCode(), 1);
    }

    @Test
    public void testExitCode2(){
        ArrayList<Tile> tileTypes = new ArrayList<>();
        tileTypes.add(Tile.BLUE);
        tileTypes.add(Tile.GREEN);
        tileTypes.add(Tile.RED);
        tileTypes.add(Tile.YELLOW);
        tileTypes.add(Tile.BLACK);
        var pointPattern = new ArrayList<Points>();
        pointPattern.add(new Points(1));
        pointPattern.add(new Points(2));
        pointPattern.add(new Points(2));
        Factory factory = new Factory();

        List<WallLine> mockWallLines = new ArrayList<>();
        List<PatternLine> mockPatternLines = new ArrayList<>();
        Floor floor = new Floor(UsedTyles.getInstance(), pointPattern);
        for(int i = 0; i < 5; i++){
            mockWallLines.add(new WallLine(tileTypes, null, null));
            mockPatternLines.add(new PatternLine(2, mockWallLines.get(i), floor));
        }

        Board board = new Board(mockPatternLines, mockWallLines, floor, new Points(0));
        TableArea tableArea = new TableArea(TableCenter.getInstance(), new ArrayList<>(Collections.singleton(factory)));
        Game game = new Game(Bag.getInstance(), tableArea, new ArrayList<>(Collections.singleton(board)), GameObserver.getInstance());

        factory.take(0);
        while(!TableCenter.getInstance().isEmpty()){
            TableCenter.getInstance().take(0);
        }

        assertTrue(game.take(0, 0, 0, 0));
        assertEquals("New round has started", game.getExitCode(), 2);
    }

    @Test
    public void testExitCode4(){
        ArrayList<Tile> tileTypes = new ArrayList<>();
        tileTypes.add(Tile.BLUE);
        tileTypes.add(Tile.GREEN);
        tileTypes.add(Tile.RED);
        tileTypes.add(Tile.YELLOW);
        tileTypes.add(Tile.BLACK);
        var pointPattern = new ArrayList<Points>();
        pointPattern.add(new Points(1));
        pointPattern.add(new Points(2));
        pointPattern.add(new Points(2));
        Factory factory = new Factory();

        List<WallLine> mockWallLines = new ArrayList<>();
        List<PatternLine> mockPatternLines = new ArrayList<>();
        Floor floor = new Floor(UsedTyles.getInstance(), pointPattern);
        for(int i = 0; i < 5; i++){
            mockWallLines.add(new WallLine(tileTypes, null, null));
            mockPatternLines.add(new PatternLine(2, mockWallLines.get(i), floor));
        }

        Board board = new Board(mockPatternLines, mockWallLines, floor, new Points(0));
        TableArea tableArea = new TableArea(TableCenter.getInstance(), new ArrayList<>(Collections.singleton(factory)));
        Game game = new Game(Bag.getInstance(), tableArea, new ArrayList<>(Collections.singleton(board)), GameObserver.getInstance());

        factory.take(0);
        while(!TableCenter.getInstance().isEmpty()){
            TableCenter.getInstance().take(0);
        }

        mockWallLines.get(0).putTile(Tile.BLUE);
        mockWallLines.get(0).putTile(Tile.GREEN);
        mockWallLines.get(0).putTile(Tile.RED);
        mockWallLines.get(0).putTile(Tile.YELLOW);
        mockWallLines.get(0).putTile(Tile.BLACK);

        assertFalse(game.take(0, 0, 0, 0));
        assertEquals("Game has ended", game.getExitCode(), 4);

        assertFalse(game.take(0, 0, 0, 0));
        assertEquals("Game has already ended", game.getExitCode(), 4);
    }
}
