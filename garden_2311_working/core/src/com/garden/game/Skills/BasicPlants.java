package com.garden.game.Skills;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.garden.game.player.Player;
import com.garden.game.tools.Constants;
import jdk.vm.ci.meta.Constant;
import com.garden.game.screens.SkillTreeScreen;

import java.util.ArrayList;

public class BasicPlants extends Skill {
    public BasicPlants(int turns, Player player)
    {
        super(turns, player);
        adjacent.add(Constants.FERTILIZER_1);
        incoming = 0;
        name = "Basic Plants";
    }

    @Override
    public void skillLearned(){
        super.skillLearned();
        player.makePlantAvailable(Constants.TOMATO);
        player.makePlantAvailable(Constants.POTATO);
        player.makePlantAvailable(Constants.TURNIP);
        player.makePlantAvailable(Constants.WHEAT);
        player.makePlantAvailable(Constants.CORN);
        player.makePlantAvailable(Constants.CASSAVA);
        player.makePlantAvailable(Constants.EGGPLANT);
        player.makePlantAvailable(Constants.COFFEE);
    }

    @Override
    public void nextTurn() {
        super.nextTurn();
        if(turns == 0) {
            skillLearned();


        }
    }
}
