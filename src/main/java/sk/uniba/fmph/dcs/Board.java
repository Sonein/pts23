package sk.uniba.fmph.dcs;

import java.util.*;

public class Board{
    private final Points points;
    private final List<PatternLine> patternLines;
    private final Floor bin;
    private final List<WallLine> wall;
    public Board(List<PatternLine> patternLines, List<WallLine> wall, Floor floor, Points points) {
        this.patternLines = patternLines;
        this.wall = wall;
        this.bin = floor;
        this.points = points;
    }



    public void put(int destinationIdx, Tile[] tyles){
        if(destinationIdx < 0 || destinationIdx > patternLines.size()){
            List<Tile> temp = new ArrayList<>(Arrays.asList(tyles));
            this.bin.put(temp);
            return;
        }
        //insert selected tiles into the selected row of pattern
        patternLines.get(destinationIdx).put(List.of(tyles));
    }
    public FinishRoundResult finishRound(){
        //adds tiles to wall and adds points
        for (int i = 0; i < 5; i++) {
            points.addPoints(patternLines.get(i).finishRound());
        }
        //substract points from floor
        points.addPoints(bin.finishRound());
        //if a row is full, end the game
        return GameFinished.gameFinished(wall);
    }

    public void endGame(){
        points.addPoints(FinalPointsCalculation.getPoints(wall));
    }

    public String state(){
        StringBuilder toReturn = new StringBuilder("Pattern Lines:\n ");
        for (PatternLine row : patternLines ){
            toReturn.append(row.state()).append("\n");
        }
        toReturn.append("Wall:\n ");
        for (WallLine line : wall){
            toReturn.append(line.state()).append("\n");
        }
        toReturn.append("Floor:\n");
        toReturn.append(bin.state()).append("\n");
        toReturn.append(this.points).append("\n");
        return toReturn.toString();
    }
}
