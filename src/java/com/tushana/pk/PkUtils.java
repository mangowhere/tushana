/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.pk;

import com.tushana.entity.Game;
import com.tushana.entity.Player;
import com.tushana.entity.game.GameReader;
import com.tushana.entity.player.PlayerReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mango
 */
public class PkUtils {
    private static PlayerReader playerReader=new PlayerReader();
    private static GameReader gameReader=new GameReader();
    public static List <Pk> findPkByNameId (String game_id){
        Game game=gameReader.getById(game_id);
        if(game!=null){
        return fromPlayerToPk(findPlayerByGameIdAndTurn(game_id,game.turn));
                }
        else {
        return new ArrayList();
        }
    }
    private static List <Player> findPlayerByGameIdAndTurn(String game_id,int turn){
        return  playerReader.findByGameIdAndTurn(game_id, turn);
    }
    private static List<Pk> fromPlayerToPk(List<Player> players){
        List <Pk> pk_list = new ArrayList<Pk>();
        int size=players.size()/2;
        for(int i=0;i<size;i++){
            Pk pk=new Pk();
            pk.left=players.get(i*2);
            pk.right=players.get(i*2+1);
            pk_list.add(pk);
        }
        return pk_list;
    }
    
}
