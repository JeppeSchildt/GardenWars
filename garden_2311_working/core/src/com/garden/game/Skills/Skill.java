package com.garden.game.Skills;

import com.garden.game.player.Player;

public class Skill {
    public Player player;
    public int turns;
    public boolean learned;

    public Skill (int turns, Player player){
     this.turns = turns;
     this.player = player;
     this.learned = false;

    }




    public void skillLearned(){
        learned = true;
    }

}
