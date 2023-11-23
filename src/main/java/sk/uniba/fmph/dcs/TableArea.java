package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;

public class TableArea{
    private ArrayList<TyleSource> _tyleSources;
    public TableArea(TableCenter tableCenter, List<Factory> factories){
        this._tyleSources = new ArrayList<>();
        this._tyleSources.add(tableCenter);
        this._tyleSources.addAll(factories);
    }

    public Tile[] take(int sourceId, int idx){
        return _tyleSources.get(sourceId).take(idx);
    }

    public boolean isRoundEnd(){
        for(TyleSource tyleSource: _tyleSources){
            if(!tyleSource.isEmpty()) return false;
        }
        return true;
    }

    public void startNewRound(){
        for(TyleSource tyleSource: _tyleSources){
            tyleSource.startNewRound();
        }
    }

    public String state(){
        StringBuilder ans = new StringBuilder();
        ans.append("TyleSources:\n");
        for (TyleSource ts:
             this._tyleSources) {
            ans.append(ts.state()).append("\n");
        }
        return ans.toString();
    }
}