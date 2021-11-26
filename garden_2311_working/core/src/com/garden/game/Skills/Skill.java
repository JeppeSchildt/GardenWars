package com.garden.game.Skills;

import com.garden.game.player.Player;

import java.util.ArrayList;

public class Skill {
    public Player player;
    public int turns;
    public boolean learned;
    public boolean hasTurnWork;
    public ArrayList<Integer> adjacent; // Edges from this node.
    int incoming; // Edges ending at this node. Not used

    public Skill (int turns, Player player){
     this.turns = turns;
     this.player = player;
     this.learned = false;
     adjacent = new ArrayList<>();

    }


    public void skillLearned(){
        learned = true;
    }

    public void turnWork() {

    }

    public void nextTurn() {
        turns--;
    }

}
