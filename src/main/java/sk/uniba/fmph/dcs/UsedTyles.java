package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UsedTyles implements UsedTilesGiveInterface{
    private ArrayList<Tile> _usedTyles;
    private UsedTyles(){
        _usedTyles = new ArrayList<>();
    }

    private static class UsedTylesHolder {
        private static final UsedTyles INSTANCE = new UsedTyles();
    }

    public static UsedTyles getInstance() {
        return UsedTyles.UsedTylesHolder.INSTANCE;
    }

    @Override
    public void give(Collection<Tile> tiles){
        _usedTyles.addAll(tiles);
    }

    public String state(){
        StringBuilder ans = new StringBuilder();
        for (Tile tile : _usedTyles) ans.append(tile.toString());
        return ans.toString();
    }

    public List<Tile> takeAll(){
        List<Tile> ans = new ArrayList<>(this._usedTyles);
        _usedTyles.clear();
        return ans;
    }
}
