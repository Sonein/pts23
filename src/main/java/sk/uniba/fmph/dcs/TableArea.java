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
        ans.append("TableCenter:\n");
        ans.append(this._tyleSources.get(0).state()).append("\n");
        ans.append("Factories:\n");
        for (int i=1; i < _tyleSources.size(); i++){
            ans.append(this._tyleSources.get(i).state()).append("\n");
        }
        return ans.toString();
    }
}