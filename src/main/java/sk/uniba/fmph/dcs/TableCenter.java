package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collections;

public class TableCenter implements TyleSource{
    private static TableCenter instance = new TableCenter();
    private TableCenter(){
        this._tyles = new ArrayList<>();
        this._tyles.add(Tile.STARTING_PLAYER);
    }
    public static TableCenter getInstance() {
        return instance;
    }
    private ArrayList<Tile> _tyles;

    @Override
    public Tile[] take(int idx) {
        if(idx >= this._tyles.size()){
            return null;
        }
        Tile taken = _tyles.get(idx);
        ArrayList<Tile> ans = new ArrayList<>();
        for(Tile tile: _tyles){
            if (tile.equals(taken)) ans.add(tile);
        }
        _tyles.removeAll(Collections.singleton(taken));
        if(_tyles.contains(Tile.STARTING_PLAYER)){
            ans.add(Tile.STARTING_PLAYER);
            _tyles.remove(Tile.STARTING_PLAYER);
        }
        Tile[] result = new Tile[ans.size()];
        return ans.toArray(result);
    }

    @Override
    public boolean isEmpty() {
        return _tyles.isEmpty();
    }

    @Override
    public void startNewRound() {
        _tyles = new ArrayList<>();
        _tyles.add(Tile.STARTING_PLAYER);
    }

    @Override
    public String state() {
        StringBuilder ans = new StringBuilder();
        for (Tile tile : _tyles) ans.append(tile.toString());
        return ans.toString();
    }

    public void add(Tile[] tyles){
        Collections.addAll(_tyles, tyles);
    }
}