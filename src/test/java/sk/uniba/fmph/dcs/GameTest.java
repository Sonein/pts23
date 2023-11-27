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
        assertEquals("Game should accept without change to exitcode", 0, game.getExitCode());

        assertTrue(game.take(0, 1, 0, 0));
        assertEquals("Game should accept without change to exitcode", 0, game.getExitCode());
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
        assertEquals("Nonexistent player", 1, game.getExitCode());

        assertFalse(game.take(0, 2, 0, 0));
        assertEquals("Nonexistent TyleSource", 1, game.getExitCode());

        assertFalse(game.take(0, 1, 5, 0));
        assertEquals("Nonexistent element in Factory", 1, game.getExitCode());
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
            mockPatternLines.add(new PatternLine(i+1, mockWallLines.get(i), floor));
        }

        Board board = new Board(mockPatternLines, mockWallLines, floor, new Points(0));
        TableArea tableArea = new TableArea(TableCenter.getInstance(), new ArrayList<>(Collections.singleton(factory)));
        Game game = new Game(Bag.getInstance(), tableArea, new ArrayList<>(Collections.singleton(board)), GameObserver.getInstance());

        factory.take(0);
        while(!TableCenter.getInstance().isEmpty()){
            TableCenter.getInstance().take(0);
        }

        assertTrue(game.take(0, 0, 0, 0));
        assertEquals("New round has started", 2, game.getExitCode());
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
            mockPatternLines.add(new PatternLine(i+1, mockWallLines.get(i), floor));
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
        assertEquals("Game has ended", 4, game.getExitCode());

        assertFalse(game.take(0, 0, 0, 0));
        assertEquals("Game has already ended", 4, game.getExitCode());
    }

    @Test
    public void testCorrectPlayersTurn(){
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

        List<WallLine> mockWallLines1 = new ArrayList<>();
        List<PatternLine> mockPatternLines1 = new ArrayList<>();
        List<WallLine> mockWallLines2 = new ArrayList<>();
        List<PatternLine> mockPatternLines2 = new ArrayList<>();
        Floor floor1 = new Floor(UsedTyles.getInstance(), pointPattern);
        Floor floor2 = new Floor(UsedTyles.getInstance(), pointPattern);
        for(int i = 0; i < 5; i++){
            mockWallLines1.add(new WallLine(tileTypes, null, null));
            mockPatternLines1.add(new PatternLine(i+1, mockWallLines1.get(i), floor1));
            mockWallLines2.add(new WallLine(tileTypes, null, null));
            mockPatternLines2.add(new PatternLine(i+1, mockWallLines2.get(i), floor2));
        }

        List<Board> boards = new ArrayList<>();
        boards.add(new Board(mockPatternLines1, mockWallLines1, floor1, new Points(0)));
        boards.add(new Board(mockPatternLines2, mockWallLines2, floor2, new Points(0)));
        TableArea tableArea = new TableArea(TableCenter.getInstance(), new ArrayList<>(Collections.singleton(factory)));
        Game game = new Game(Bag.getInstance(), tableArea, boards, GameObserver.getInstance());

        assertTrue("Player 0 takes", game.take(0, 1,0,0));
        assertFalse("Not Player 0 turn", game.take(0, 0,0,0));
        assertTrue("Player 1 takes and becomes new starting player", game.take(1, 0,0,0));

        while(!TableCenter.getInstance().isEmpty()){
            TableCenter.getInstance().take(0);
        }

        assertFalse("Player 0 is not starting in this round", game.take(0, 0,0,0));
        assertTrue("Player 1 is starting", game.take(1, 0,0,0));
        assertTrue("Player 0 takes", game.take(0, 1,0,0));
    }
}
