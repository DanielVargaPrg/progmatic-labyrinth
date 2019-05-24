/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;

/**
 *
 * @author Dano
 */
public class RandomPlayer implements Player{

    @Override
    public Direction nextMove(Labyrinth l) {
        int random = (int) (Math.random() * l.possibleMoves().size());
        
        return l.possibleMoves().get(random);
    }
    
}
