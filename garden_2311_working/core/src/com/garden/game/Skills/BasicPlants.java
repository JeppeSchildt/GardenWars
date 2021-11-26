package com.garden.game.Skills;

import com.garden.game.player.Player;
import com.garden.game.tools.Constants;
import jdk.vm.ci.meta.Constant;

public class BasicPlants extends Skill {
    public BasicPlants(int turns, Player player) {
        super(turns, player);
    }

    @Override
    public void skillLearned(){
        super.skillLearned();
        player.makePlantAvailable(Constants.RICE);
    }
}
