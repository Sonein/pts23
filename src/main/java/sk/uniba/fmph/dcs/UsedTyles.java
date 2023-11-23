package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UsedTyles implements UsedTilesGiveInterface{
    private ArrayList<Tile> _usedTyles;
    public UsedTyles(){
        _usedTyles = new ArrayList<>();
    }

    @Override
    public void give(Collection<Tile> tiles){
        _usedTyles.addAll(tiles);
    }

    public String state(){
        StringBuilder ans = new StringBuilder();
        int n = _usedTyles.size();
        for (Tile tile : _usedTyles) ans.append(tile.toString());
        return ans.toString();
    }

    public List<Tile> takeAll(){
        List<Tile> ans = new ArrayList<>(this._usedTyles);
        _usedTyles.clear();
        return ans;
    }
}
