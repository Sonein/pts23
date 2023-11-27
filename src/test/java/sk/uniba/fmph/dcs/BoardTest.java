package sk.uniba.fmph.dcs;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BoardTest {

    @Test
    public void testPut(){
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

        List<WallLine> mockWallLines = new ArrayList<>();
        List<PatternLine> mockPatternLines = new ArrayList<>();
        Floor floor = new Floor(UsedTyles.getInstance(), pointPattern);
        for(int i = 0; i < 5; i++){
            mockWallLines.add(new WallLine(tileTypes, null, null));
            mockPatternLines.add(new PatternLine(i+1, mockWallLines.get(i), floor));
        }

        Board board = new Board(mockPatternLines, mockWallLines, floor, new Points(0));

        board.put(-1, new Tile[] {Tile.BLACK});
        assertEquals("Incorrect put goes to floor", "L", floor.state());

        board.put(0, new Tile[] {Tile.BLACK});
        assertEquals("Correct put went to patternLine", "L", mockPatternLines.get(0).state());
    }

    @Test
    public void testFinishRound(){
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

        List<WallLine> mockWallLines = new ArrayList<>();
        List<PatternLine> mockPatternLines = new ArrayList<>();
        Floor floor = new Floor(UsedTyles.getInstance(), pointPattern);
        for(int i = 0; i < 5; i++){
            mockWallLines.add(new WallLine(tileTypes, null, null));
            mockPatternLines.add(new PatternLine(i+1, mockWallLines.get(i), floor));
        }

        Board board = new Board(mockPatternLines, mockWallLines, floor, new Points(0));

        assertEquals("Round has not ended", FinishRoundResult.NORMAL, board.finishRound());
        assertTrue("points are zero", board.state().contains("[value=0]"));

        board.put(0, new Tile[] {Tile.BLACK});
        assertEquals("Round is normal, game has not ended", FinishRoundResult.NORMAL, board.finishRound());
        assertTrue("points got one point because 1 went to wallLine", board.state().contains("[value=1]"));

        mockWallLines.get(0).putTile(Tile.BLUE);
        mockWallLines.get(0).putTile(Tile.GREEN);
        mockWallLines.get(0).putTile(Tile.RED);
        mockWallLines.get(0).putTile(Tile.YELLOW);
        mockWallLines.get(0).putTile(Tile.BLACK);
        assertEquals("Game has ended", FinishRoundResult.GAME_FINISHED, board.finishRound());
        assertTrue("Game has ended but points were not counted", board.state().contains("[value=1]"));

        board.endGame();
        assertTrue("Final points were calculated", board.state().contains("[value=13]"));
    }
}
