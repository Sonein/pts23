package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;

public class Game implements GameInterface{

    private final Bag bag;
    private final TableArea tableArea;
    private final List<Board> boards;
    private final GameObserver gameObserver;
    private boolean gameDone;
    private int exitCode;

    public Game (final Bag bag,final TableArea tableArea,final List<Board> boards,final GameObserver gameObserver) {
        this.bag = bag;
        this.tableArea = tableArea;
        this.boards = new ArrayList<>(boards);
        this.gameObserver = gameObserver;
        this.gameDone = false;
        this.exitCode = 0;
    }

    private String state(){
        StringBuilder sb = new StringBuilder();
        sb.append("Bag:\n");
        sb.append(this.bag.state()).append("\n");
        sb.append("TableArea:\n");
        sb.append(this.bag.state()).append("\n");
        sb.append("Boards:\n");
        for (Board b:
             this.boards) {
            sb.append(b.state()).append("\n");
        }
        sb.append(this.exitCode).append("\n");
        return sb.toString();
    }

    public int getExitCode(){
        return  this.exitCode;
    }

    @Override
    public Boolean take(Integer playerId, Integer sourceId, Integer idx, Integer destinationIdx) {
        this.exitCode = 0;
        if(gameDone){
            this.exitCode = 4;
            return false;
        }
        if(playerId >= this.boards.size() || playerId < 0){
            this.exitCode = 1;
            return false;
        } else {
            if (this.tableArea.isRoundEnd()){
                FinishRoundResult finishRoundResult = FinishRoundResult.NORMAL;
                for (Board b:
                     this.boards) {
                    if(b.finishRound().equals(FinishRoundResult.GAME_FINISHED)){
                        finishRoundResult = FinishRoundResult.GAME_FINISHED;
                        break;
                    }
                }
                if(finishRoundResult == FinishRoundResult.GAME_FINISHED){
                    this.gameObserver.notifyEverybody(state());
                    for (Board b:
                         this.boards) {
                        b.endGame();
                    }
                    this.gameDone = true;
                    this.exitCode = 4;
                    return false;
                } else {
                    this.tableArea.startNewRound();
                    this.exitCode = 2;
                }
            }
            Tile [] toPut = this.tableArea.take(sourceId, idx);
            if(toPut == null){
                this.exitCode = 1;
                return false;
            }
            this.boards.get(playerId).put(destinationIdx, toPut);
            this.gameObserver.notifyEverybody(state());
            return true;
        }
    }
}
