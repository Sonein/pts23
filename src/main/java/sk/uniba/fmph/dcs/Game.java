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
    private int currentPlayer;
    private final int playerCap;
    private int startingPlayer;


    public Game (final Bag bag,final TableArea tableArea,final List<Board> boards,final GameObserver gameObserver) {
        this.bag = bag;
        this.tableArea = tableArea;
        this.boards = new ArrayList<>(boards);
        this.gameObserver = gameObserver;
        this.gameDone = false;
        this.exitCode = 0;
        this.currentPlayer = 0;
        this.startingPlayer = 0;
        this.playerCap = boards.size()-1;
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
        if(this.gameDone){
            this.exitCode = 4;
            return false;
        }
        if(playerId > this.playerCap || playerId < 0){
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
                    this.currentPlayer = this.startingPlayer;
                    this.exitCode = 2;
                }
            }
            if(playerId != this.currentPlayer){
                this.exitCode = 1;
                return false;
            }
            Tile [] toPut = this.tableArea.take(sourceId, idx);
            if(toPut == null){
                this.exitCode = 1;
                return false;
            }
            for (Tile t:
                 toPut) {
                if (t.equals(Tile.STARTING_PLAYER)) {
                    this.startingPlayer = playerId;
                    break;
                }
            }
            this.boards.get(playerId).put(destinationIdx, toPut);
            this.gameObserver.notifyEverybody(state());
            if (this.currentPlayer == this.playerCap){
                this.currentPlayer = 0;
            } else {
                this.currentPlayer++;
            }
            return true;
        }
    }
}
