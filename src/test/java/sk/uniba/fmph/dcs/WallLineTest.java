package sk.uniba.fmph.dcs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import org.junit.Test;

public class WallLineTest {
  @Test
  public void testCanPutTiles() {
    ArrayList<Tile> tileTypes = new ArrayList<>();
    tileTypes.add(Tile.BLACK);
    tileTypes.add(Tile.BLUE);
    tileTypes.add(Tile.GREEN);
    tileTypes.add(Tile.RED);
    tileTypes.add(Tile.YELLOW);
    
    WallLine wallLine = new WallLine(tileTypes, null, null);
    wallLine.putTile(Tile.BLACK);

    assertFalse("Already present.", wallLine.canPutTile(Tile.BLACK)); // nemali by sme vediet dat
    assertFalse("Not in tileTypes.", wallLine.canPutTile(Tile.STARTING_PLAYER)); // nie je v tileTypes
    assertTrue("Should be insertable.", wallLine.canPutTile(Tile.BLUE)); // mali by sme vediet dat
  }

  @Test
  public void testGetTiles() {
    ArrayList<Tile> tileTypes = new ArrayList<>();
    tileTypes.add(Tile.RED);
    tileTypes.add(Tile.YELLOW);
    tileTypes.add(Tile.BLACK);
    tileTypes.add(Tile.BLUE);
    tileTypes.add(Tile.GREEN);
    
    WallLine wallLine = new WallLine(tileTypes, null, null);
    ArrayList<Optional<Tile>> empty = new ArrayList<>();
    Tile[] tiles = new Tile[5];
    for (int i = 0; i < 5; i++)
        empty.add(Optional.ofNullable(tiles[i]));
    List<Optional<Tile>> wallLineTiles = wallLine.getTiles();
    assertEquals("Empty lines.", empty, wallLineTiles);

    wallLine.putTile(Tile.BLACK);
    assertFalse("Is not empty anymore", wallLine.getTiles().equals(empty));

    tiles[2] = Tile.BLACK;
    ArrayList<Optional<Tile>> wantTiles = new ArrayList<>();
    for (int i = 0; i < 5; i++)
        wantTiles.add(Optional.ofNullable(tiles[i]));
    assertEquals("Contains something.", wallLine.getTiles(), wantTiles);
  }

  @Test
  public void testPutTiles() {
    ArrayList<Tile> tileTypes = new ArrayList<>();
    tileTypes.add(Tile.BLUE);
    tileTypes.add(Tile.GREEN);
    tileTypes.add(Tile.RED);
    tileTypes.add(Tile.YELLOW);
    tileTypes.add(Tile.BLACK);

    WallLine wallLine0 = new WallLine(tileTypes, null, null);
    wallLine0.putTile(Tile.BLUE);
    Points points = wallLine0.putTile(Tile.GREEN);
    assertEquals("Should make 2 points.", new Points(2), points);

    WallLine wallLine = new WallLine(tileTypes, null, null);

    assertTrue("Testing if tiles is actually put. Not present yet.", wallLine.canPutTile(Tile.GREEN));
    wallLine.putTile(Tile.GREEN);
    assertFalse("Testing if tiles is actually put. Already put.", wallLine.canPutTile(Tile.GREEN));

    assertTrue("Testing if tiles is actually put. Not present yet.", wallLine.canPutTile(Tile.BLACK));
    wallLine.putTile(Tile.BLACK);
    assertFalse("Testing if tiles is actually put. Already put.", wallLine.canPutTile(Tile.BLACK));

    ArrayList<Tile> tileTypes2 = new ArrayList<>();
    tileTypes2.add(Tile.YELLOW);
    tileTypes2.add(Tile.BLACK);
    tileTypes2.add(Tile.BLUE);
    tileTypes2.add(Tile.GREEN);
    tileTypes2.add(Tile.RED);
    WallLine wallLine2 = new WallLine(tileTypes2, wallLine, wallLine);

    Points points1 = wallLine2.putTile(Tile.YELLOW);
    assertEquals("Testing point gain from put. Nothing adjadcent.", new Points(1), points1);

    Points points2 = wallLine2.putTile(Tile.BLACK);
    assertEquals("Testing point gain from put. Vertical and horizontal adjacent.", new Points(4), points2);
  }

  @Test
  public void testState() {
    ArrayList<Tile> tileTypes = new ArrayList<>();
    tileTypes.add(Tile.GREEN);
    tileTypes.add(Tile.BLACK);
    tileTypes.add(Tile.BLUE);
    tileTypes.add(Tile.RED);
    tileTypes.add(Tile.YELLOW);
    WallLine wallLine = new WallLine(tileTypes, null, null);

    assertEquals("State when WallLine is empty.", "-----", wallLine.state());
    wallLine.putTile(Tile.GREEN);
    wallLine.putTile(Tile.BLUE);
    wallLine.putTile(Tile.RED);
    assertEquals("State with some pieces put.", "G-BR-", wallLine.state());
  }
}
