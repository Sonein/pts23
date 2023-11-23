package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;

public class Game implements GameInterface{

    private final int players;
    private final Bag bag;
    private final TableArea tableArea;
    private final List<Board> boards;
    private final GameObserver gameObserver;
    private int currentPlayer = 1;

    public Game (final int playerCount, final Bag bag,final TableArea tableArea,final List<Board> boards,final GameObserver gameObserver) {
        this.players = playerCount;
        this.bag = bag;
        this.tableArea = tableArea;
        this.boards = new ArrayList<>(boards);
        this.gameObserver = gameObserver;
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
        return sb.toString();
    }

    @Override
    public Boolean take(Integer playerId, Integer sourceId, Integer idx, Integer destinationIdx) {
        if(this.currentPlayer != playerId){
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
                    return false;
                } else {
                    this.tableArea.startNewRound();
                }
            }
            Tile [] toPut = this.tableArea.take(sourceId, idx);
            this.boards.get(playerId).put(destinationIdx, toPut);
            this.gameObserver.notifyEverybody(state());
            if(this.currentPlayer == this.players){
                this.currentPlayer = 1;
            } else {
                this.currentPlayer++;
            }
            return true;
        }
    }
}
